package defdev.labdroid.activity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import defdev.labdroid.activity.flows.FlowId
import defdev.labdroid.activity.ui.LoginScreen

@Composable
fun ActivityApp(container: AppContainer, onAuthenticated: (FlowId) -> Unit) {
    var selectedId by rememberSaveable { mutableStateOf(FlowId.CHALLENGE.value) }
    val selected = FlowId.entries.first { it.value == selectedId }
    val model: LoginViewModel = viewModel(key = selected.value, factory = container.viewModelFactory)
    LoginScreen(selected, { selectedId = it.value }, model) {
        model.contentOpened()
        onAuthenticated(selected)
    }
}
