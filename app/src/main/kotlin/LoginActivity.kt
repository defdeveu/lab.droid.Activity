package defdev.labdroid.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import defdev.labdroid.activity.theme.ActivityLabTheme
import defdev.labdroid.activity.flows.FlowId

class LoginActivity : ComponentActivity() {
    private val container by lazy { AppContainer() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
        )
        setContent {
            ActivityLabTheme {
                ActivityApp(container) { selected ->
                    val target = when (selected) {
                        FlowId.CHALLENGE -> defdev.labdroid.activity.flows.challenge.SecretActivity::class.java
                        FlowId.SOLUTION -> defdev.labdroid.activity.flows.solution.SecretActivity::class.java
                    }
                    startActivity(Intent(this, target))
                }
            }
        }
    }
}
