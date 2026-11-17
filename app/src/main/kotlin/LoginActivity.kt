package defdev.labdroid.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import defdev.labdroid.activity.content.LOGIN_FLOW_EXTRA
import defdev.labdroid.activity.flows.solution.ContentAuthorization
import defdev.labdroid.activity.theme.ActivityLabTheme
import defdev.labdroid.activity.flows.FlowId

class LoginActivity : ComponentActivity() {
    private val container by lazy { AppContainer() }
    private var requestedFlow by mutableStateOf<FlowId?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) requestedFlow = requestedFlow(intent)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
        )
        setContent {
            ActivityLabTheme {
                ActivityApp(container, requestedFlow, { requestedFlow = null }) { selected ->
                    val target = when (selected) {
                        FlowId.CHALLENGE -> Intent(this, defdev.labdroid.activity.flows.challenge.SecretActivity::class.java)
                        FlowId.SOLUTION -> Intent(this, defdev.labdroid.activity.flows.solution.SecretActivity::class.java)
                            .putExtra(ContentAuthorization.EXTRA_GRANT, ContentAuthorization.grant())
                    }
                    startActivity(target)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        requestedFlow = requestedFlow(intent)
    }

    private fun requestedFlow(intent: Intent): FlowId? =
        FlowId.entries.firstOrNull { it.value == intent.getStringExtra(LOGIN_FLOW_EXTRA) }
}
