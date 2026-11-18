package defdev.labs.droid.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import defdev.labs.droid.activity.theme.ActivityLabTheme
import defdev.labs.droid.activity.ui.LoginScreen

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
                val model: LoginViewModel = viewModel(factory = container.viewModelFactory)
                LoginScreen(model) {
                    startActivity(Intent(this, SecretActivity::class.java))
                    finish()
                }
            }
        }
    }
}
