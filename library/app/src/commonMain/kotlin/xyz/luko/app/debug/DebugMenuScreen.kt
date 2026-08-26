package xyz.luko.app.debug

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import xyz.luko.ui.designsystem.components.page.NestedScaffold
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.utils.DebugAction

@Composable
internal fun DebugMenuScreen() {
    val debugActions = koinInject<List<DebugAction>>()
    val scope = rememberCoroutineScope()

    NestedScaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(debugActions.size) {
                val action = debugActions[it]
                Button(
                    onClick = { scope.launch { action.execute() } },
                    modifier = Modifier.fillMaxSize()
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Theme.materialColors.background,
                        contentColor = Theme.materialColors.error
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Theme.materialColors.error
                    )
                ) {
                    Text(action.label)
                }
            }
        }
    }
}
