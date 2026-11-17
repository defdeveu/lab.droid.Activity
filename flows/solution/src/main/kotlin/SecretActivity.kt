package defdev.labdroid.activity.flows.solution

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import defdev.labdroid.activity.content.LOGIN_FLOW_EXTRA
import defdev.labdroid.activity.content.showSecretImage

class SecretActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authorization = ViewModelProvider.create(this)[ContentAuthorizationModel::class.java]
        if (!authorization.authorize(intent.getStringExtra(ContentAuthorization.EXTRA_GRANT))) {
            packageManager.getLaunchIntentForPackage(packageName)?.let { login ->
                startActivity(login
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .putExtra(LOGIN_FLOW_EXTRA, "solution"))
            }
            finish()
            return
        }
        showSecretImage()
    }
}
