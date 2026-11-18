package defdev.labs.droid.activity.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import defdev.labs.droid.activity.LoginViewModel
import defdev.labs.droid.activity.R

@Composable
fun LoginScreen(model: LoginViewModel, onAuthenticated: () -> Unit) {
    val state by model.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) onAuthenticated()
    }
    Scaffold(topBar = { LabHeader() }) { insets ->
        Column(
            Modifier.fillMaxSize().padding(insets).imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                Modifier.widthIn(max = 720.dp).fillMaxWidth().padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Text(
                    stringResource(R.string.please_log_in_and_find_batman_s_secret),
                    style = MaterialTheme.typography.titleLargeEmphasized,
                )
                TextField(
                    value = state.username,
                    onValueChange = model::onUsernameChange,
                    label = {
                        Text(stringResource(R.string.username),
                            fontWeight = MaterialTheme.typography.labelLargeEmphasized.fontWeight)
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.fillMaxWidth(),
                )
                TextField(
                    value = state.password,
                    onValueChange = model::onPasswordChange,
                    label = {
                        Text(stringResource(R.string.password),
                            fontWeight = MaterialTheme.typography.labelLargeEmphasized.fontWeight)
                    },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(onDone = { model.login() }),
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.fillMaxWidth(),
                )
                LoginAction(state.canSubmit && !state.isAuthenticated, model::login)
                if (state.error != null) {
                    Text(
                        stringResource(R.string.error_credentials),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}
