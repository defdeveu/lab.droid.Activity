package defdev.labdroid.activity

import defdev.labdroid.activity.error.LabError
import defdev.labdroid.activity.service.LoginService
import org.junit.Assert.*
import org.junit.Test

class LoginViewModelTest {
    private class Service(private val accepted: Boolean) : LoginService {
        var received: Pair<String, String>? = null
        override fun authenticate(username: String, password: String): Boolean {
            received = username to password
            return accepted
        }
    }

    @Test fun emptyFieldsDoNotSubmit() {
        val service = Service(true)
        val model = LoginViewModel(service)
        model.login()
        assertFalse(model.uiState.value.canSubmit)
        assertNull(service.received)
    }

    @Test fun bothFieldsControlAvailability() {
        val model = LoginViewModel(Service(true))
        model.onPasswordChange("password")
        assertFalse(model.uiState.value.canSubmit)
        model.onUsernameChange("username")
        assertTrue(model.uiState.value.canSubmit)
        model.onUsernameChange("")
        assertFalse(model.uiState.value.canSubmit)
    }

    @Test fun acceptedInputProducesCompletionWithoutChangingItsContents() {
        val service = Service(true)
        val model = LoginViewModel(service)
        model.onUsernameChange(" User ")
        model.onPasswordChange(" Password ")
        model.login()
        assertEquals(" User " to " Password ", service.received)
        assertTrue(model.uiState.value.isAuthenticated)
        assertNull(model.uiState.value.error)
    }

    @Test fun rejectionRetainsInputAndReportsFailure() {
        val model = LoginViewModel(Service(false))
        model.onUsernameChange("user")
        model.onPasswordChange("password")
        model.login()
        assertFalse(model.uiState.value.isAuthenticated)
        assertEquals(LabError.INVALID_CREDENTIALS, model.uiState.value.error)
        assertEquals("password", model.uiState.value.password)
    }

    @Test fun editingAfterFailureClearsItsMessage() {
        val model = LoginViewModel(Service(false))
        model.onUsernameChange("user")
        model.onPasswordChange("password")
        model.login()
        model.onPasswordChange("another")
        assertNull(model.uiState.value.error)
    }
}
