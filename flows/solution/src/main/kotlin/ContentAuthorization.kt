package defdev.labdroid.activity.flows.solution

import java.util.UUID

object ContentAuthorization {
    const val EXTRA_GRANT = "defdev.labdroid.activity.CONTENT_GRANT"
    private var pending: String? = null

    fun grant(): String = UUID.randomUUID().toString().also { pending = it }

    internal fun consume(token: String?): Boolean {
        if (token == null || token != pending) return false
        pending = null
        return true
    }
}
