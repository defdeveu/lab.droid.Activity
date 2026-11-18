package defdev.labs.droid.activity.service

interface LoginService {
    fun authenticate(username: String, password: String): Boolean
}

class DefaultLoginService : LoginService {
    override fun authenticate(username: String, password: String): Boolean =
        username == "demo" && password == "demo"
}
