package defdev.labdroid.activity.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.WideNavigationRail
import androidx.compose.material3.WideNavigationRailDefaults
import androidx.compose.material3.WideNavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import defdev.labdroid.activity.LoginViewModel
import defdev.labdroid.activity.R
import defdev.labdroid.activity.flows.FlowId

private data class LoginCase(val flow: FlowId, val model: LoginViewModel)

@Composable
fun LoginScreen(
    selected: FlowId,
    onSelect: (FlowId) -> Unit,
    model: LoginViewModel,
    onAuthenticated: () -> Unit,
) {
    val state by model.uiState.collectAsStateWithLifecycle()
    val motion = MaterialTheme.motionScheme.defaultSpatialSpec<IntOffset>()
    LaunchedEffect(model, state.isAuthenticated) {
        if (state.isAuthenticated) onAuthenticated()
    }
    Scaffold(topBar = { LabHeader() }) { insets ->
        Row(Modifier.fillMaxSize().padding(insets)) {
            WideNavigationRail(
                modifier = Modifier.fillMaxHeight().width(76.8.dp),
                windowInsets = WindowInsets(0, 0, 0, 0),
                colors = WideNavigationRailDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
            ) {
                FlowId.entries.forEachIndexed { index, flow ->
                    val label = stringResource(R.string.case_navigation, index + 1, stringResource(flow.titleRes()))
                    WideNavigationRailItem(
                        selected = selected == flow,
                        onClick = { onSelect(flow) },
                        icon = { Text(stringResource(flow.markerRes()),
                            style = MaterialTheme.typography.titleMediumEmphasized, fontSize = 20.sp) },
                        label = null,
                        railExpanded = false,
                        modifier = Modifier.semantics { contentDescription = label },
                    )
                }
            }
            AnimatedContent(
                targetState = LoginCase(selected, model),
                contentKey = { it.flow },
                modifier = Modifier.weight(1f).fillMaxHeight().clipToBounds(),
                transitionSpec = {
                    val direction = if (targetState.flow.ordinal > initialState.flow.ordinal) {
                        AnimatedContentTransitionScope.SlideDirection.Start
                    } else {
                        AnimatedContentTransitionScope.SlideDirection.End
                    }
                    slideIntoContainer(direction, motion) togetherWith slideOutOfContainer(direction, motion)
                },
                label = "caseTransition",
            ) { page ->
                LoginForm(page.flow, page.model, page.flow == selected)
            }
        }
    }
}

@Composable
private fun LoginForm(flow: FlowId, model: LoginViewModel, active: Boolean) {
    val state by model.uiState.collectAsStateWithLifecycle()
    Column(
        Modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            Modifier.widthIn(max = 720.dp).fillMaxWidth().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(stringResource(flow.titleRes()), style = MaterialTheme.typography.titleLargeEmphasized)
                Text(stringResource(R.string.please_log_in_and_find_batman_s_secret),
                    style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            TextField(
                value = state.username,
                onValueChange = { if (active) model.onUsernameChange(it) },
                readOnly = !active,
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
                onValueChange = { if (active) model.onPasswordChange(it) },
                readOnly = !active,
                label = {
                    Text(stringResource(R.string.password),
                        fontWeight = MaterialTheme.typography.labelLargeEmphasized.fontWeight)
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(onDone = { if (active) model.login() }),
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
            )
            LoginAction(state.canSubmit && !state.isAuthenticated) { if (active) model.login() }
            if (state.error != null) {
                Text(stringResource(R.string.error_credentials),
                    color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

private fun FlowId.titleRes(): Int = when (this) {
    FlowId.CHALLENGE -> R.string.flow_challenge_title
    FlowId.SOLUTION -> R.string.flow_solution_title
}

private fun FlowId.markerRes(): Int = when (this) {
    FlowId.CHALLENGE -> R.string.flow_challenge_marker
    FlowId.SOLUTION -> R.string.flow_solution_marker
}
