package defdev.labdroid.activity.flows.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import defdev.labdroid.activity.content.showSecretImage

class SecretActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showSecretImage()
    }
}
