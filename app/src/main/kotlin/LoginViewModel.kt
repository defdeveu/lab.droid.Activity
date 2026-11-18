package defdev.labs.droid.activity

import androidx.lifecycle.ViewModel
import defdev.labs.droid.activity.error.LabError
import defdev.labs.droid.activity.service.LoginService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val error: LabError? = null,
    val isAuthenticated: Boolean = false,
) {
    val canSubmit: Boolean get() = username.isNotEmpty() && password.isNotEmpty()
}

class LoginViewModel(private val service: LoginService) : ViewModel() {
    private val state = MutableStateFlow(LoginUiState())
    val uiState = state.asStateFlow()

    fun onUsernameChange(value: String) {
        state.update { it.copy(username = value, error = null) }
    }

    fun onPasswordChange(value: String) {
        state.update { it.copy(password = value, error = null) }
    }

    fun login() {
        val current = state.value
        if (!current.canSubmit || current.isAuthenticated) return
        val accepted = service.authenticate(current.username, current.password)
        state.update {
            it.copy(
                isAuthenticated = accepted,
                error = if (accepted) null else LabError.INVALID_CREDENTIALS,
            )
        }
    }
}
