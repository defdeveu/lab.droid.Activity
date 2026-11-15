package defdev.labdroid.activity.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import defdev.labdroid.activity.R
import defdev.labdroid.activity.theme.LabTitle

@Composable
fun LabHeader() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleMediumEmphasized.copy(
                    fontFamily = LabTitle,
                    fontSize = 19.sp,
                    lineHeight = 26.sp,
                    fontWeight = FontWeight(875),
                ),
            )
        },
        navigationIcon = {
            Box(Modifier.padding(start = 16.dp, end = 8.dp)) {
                Icon(
                    painterResource(R.drawable.brand_stamp),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(40.dp),
                )
            }
        },
    )
}

@Composable
fun LoginAction(enabled: Boolean, onClick: () -> Unit) {
    val height = 56.dp
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth().heightIn(min = height),
        shapes = ButtonDefaults.shapes(
            shape = MaterialTheme.shapes.large,
            pressedShape = ButtonDefaults.mediumPressedShape,
        ),
        contentPadding = ButtonDefaults.contentPaddingFor(height),
    ) {
        Text(stringResource(R.string.login), style = ButtonDefaults.textStyleFor(height))
    }
}
