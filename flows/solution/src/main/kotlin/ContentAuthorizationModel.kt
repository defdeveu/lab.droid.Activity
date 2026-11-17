package defdev.labdroid.activity.flows.solution

import androidx.lifecycle.ViewModel

internal class ContentAuthorizationModel : ViewModel() {
    private var authorized = false

    fun authorize(token: String?): Boolean {
        if (!authorized) authorized = ContentAuthorization.consume(token)
        return authorized
    }
}
