package defdev.labdroid.activity

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import java.io.File

class LoginScreenTest {
    @get:Rule val rule = createAndroidComposeRule<LoginActivity>()
    private fun text(id: Int) = InstrumentationRegistry.getInstrumentation().targetContext.getString(id)

    @Test fun initialFormShowsItsIdentityAndFields() {
        rule.onNodeWithText(text(R.string.app_name)).assertIsDisplayed()
        rule.onNodeWithText(text(R.string.username)).assertIsDisplayed()
        rule.onNodeWithText(text(R.string.password)).assertIsDisplayed()
        rule.onNodeWithText(text(R.string.login)).assertIsNotEnabled()
    }

    @Test fun completeInputEnablesTheAction() {
        rule.onNodeWithText(text(R.string.username)).performTextInput("user")
        rule.onNodeWithText(text(R.string.password)).performTextInput("password")
        rule.onNodeWithText(text(R.string.login)).assertIsEnabled()
    }

    @Test fun rejectedInputShowsTheInlineMessage() {
        rule.onNodeWithText(text(R.string.username)).performTextInput("wrong")
        rule.onNodeWithText(text(R.string.password)).performTextInput("wrong")
        rule.onNodeWithText(text(R.string.login)).performClick()
        rule.onNodeWithText(text(R.string.error_credentials)).assertIsDisplayed()
    }

    @Test fun casesKeepTheirFormsSeparate() {
        rule.onNodeWithText(text(R.string.username)).performTextInput("first")
        rule.onNodeWithContentDescription(text(R.string.case_navigation, 2, text(R.string.flow_solution_title))).performClick()
        rule.onNodeWithText(text(R.string.flow_solution_title)).assertIsDisplayed()
        rule.onNodeWithText(text(R.string.username)).assertTextEquals(text(R.string.username), "")
        rule.onNodeWithText(text(R.string.login)).assertIsNotEnabled()
        rule.onNodeWithText(text(R.string.username)).performTextInput("second")
        rule.onNodeWithContentDescription(text(R.string.case_navigation, 1, text(R.string.flow_challenge_title))).performClick()
        rule.onNodeWithText(text(R.string.username)).assertTextEquals(text(R.string.username), "first")
    }

    @Test fun selectedCaseSurvivesRecreation() {
        rule.onNodeWithContentDescription(text(R.string.case_navigation, 2, text(R.string.flow_solution_title))).performClick()
        rule.runOnUiThread {
            ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
                .filterIsInstance<LoginActivity>().single().recreate()
        }
        rule.onNodeWithText(text(R.string.flow_solution_title)).assertIsDisplayed()
    }

    @Test fun caseTransitionsFollowTheirOrder() {
        val challenge = text(R.string.flow_challenge_title)
        val solution = text(R.string.flow_solution_title)
        val identity = rule.onNodeWithText(text(R.string.app_name)).fetchSemanticsNode().positionInRoot
        val start = rule.onNodeWithText(challenge).fetchSemanticsNode().positionInRoot.x
        rule.mainClock.autoAdvance = false
        try {
            rule.onNodeWithContentDescription(text(R.string.case_navigation, 2, solution)).performClick()
            rule.mainClock.advanceTimeByFrame()
            rule.mainClock.advanceTimeBy(80)
            assertTrue(rule.onNodeWithText(challenge).fetchSemanticsNode().positionInRoot.x < start)
            assertTrue(rule.onNodeWithText(solution).fetchSemanticsNode().positionInRoot.x > start)
            assertEquals(identity, rule.onNodeWithText(text(R.string.app_name)).fetchSemanticsNode().positionInRoot)
            captureFrame("activity-forward.png")
            rule.mainClock.advanceTimeBy(1500)
            rule.onNodeWithText(solution).assertIsDisplayed()

            rule.onNodeWithContentDescription(text(R.string.case_navigation, 1, challenge)).performClick()
            rule.mainClock.advanceTimeByFrame()
            rule.mainClock.advanceTimeBy(80)
            assertTrue(rule.onNodeWithText(solution).fetchSemanticsNode().positionInRoot.x > start)
            assertTrue(rule.onNodeWithText(challenge).fetchSemanticsNode().positionInRoot.x < start)
            assertEquals(identity, rule.onNodeWithText(text(R.string.app_name)).fetchSemanticsNode().positionInRoot)
            captureFrame("activity-backward.png")
            rule.mainClock.advanceTimeBy(1500)
            rule.onNodeWithText(challenge).assertIsDisplayed()
        } finally {
            rule.mainClock.autoAdvance = true
        }
    }

    private fun captureFrame(name: String) {
        val bitmap = rule.onRoot().captureToImage().asAndroidBitmap()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        File(context.cacheDir, name).outputStream().use { output ->
            check(bitmap.compress(Bitmap.CompressFormat.PNG, 100, output))
        }
    }

    private fun text(id: Int, vararg arguments: Any) =
        InstrumentationRegistry.getInstrumentation().targetContext.getString(id, *arguments)
}
