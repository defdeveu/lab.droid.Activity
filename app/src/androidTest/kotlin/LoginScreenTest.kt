package defdev.labs.droid.activity

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test

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
}
