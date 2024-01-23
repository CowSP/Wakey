package com.example.wakey.ui.pattern

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wakey.ui.AppViewModelProvider
import com.example.wakey.ui.navigation.NavigationDestination

object PatternDetailScreenDestination : NavigationDestination {
    override val route = "pattern_edit"
    const val patternIdArg = "patternId"
    val routeWithArgs = "$route/{$patternIdArg}"
}

@Composable
fun PatternDetailScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PatternDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.patternDetailUiState.collectAsState().value

    Column(modifier = modifier) {
        PatternDetailForm(uiState = uiState, onValueChange = {}, modifier = Modifier.weight(1f))
        PatternDetailButtons(onCancelPressed = navigateUp, onSavePressed = { /*TODO*/ })
    }
}

@Composable
fun PatternDetailForm(
    uiState: PatternDetailUiState,
    onValueChange: (PatternDetailUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = uiState.toString())
    }
}

@Composable
fun PatternDetailButtons(
    onCancelPressed: () -> Unit, onSavePressed: () -> Unit, modifier: Modifier = Modifier
) {
    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = modifier.fillMaxWidth()) {
        OutlinedButton(onClick = onCancelPressed) {
            Text(text = "Cancel")
        }
        Button(onClick = onSavePressed) {
            Text(text = "Save")
        }
    }
}