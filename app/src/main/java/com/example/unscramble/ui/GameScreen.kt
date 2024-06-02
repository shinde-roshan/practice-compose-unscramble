package com.example.unscramble.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscramble.R
import com.example.unscramble.ui.theme.UnscrambleTheme

@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel()
) {
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val gameUiState by gameViewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .statusBarsPadding()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(paddingMedium)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineSmall
        )
        GameLayout(
            scrambledWord = gameUiState.currentScrambledWord,
            wordCount = gameUiState.currentWordCount,
            userGuess = gameViewModel.userGuess,
            isUserGuessWrong = gameUiState.isGuessedWordWrong,
            onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(paddingMedium)
        )
        Button(
            onClick = { gameViewModel.checkUserGuess() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMedium)
        ) {
            Text(
                text = stringResource(id = R.string.submit),
                fontSize = 16.sp
            )
        }
        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = paddingMedium)
        ) {
            Text(
                text = stringResource(id = R.string.skip),
                fontSize = 16.sp
            )
        }
        GameScore(
            score = gameUiState.score,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
        )
    }
}

@Composable
fun GameLayout(
    scrambledWord: String,
    wordCount: Int,
    userGuess: String,
    isUserGuessWrong: Boolean,
    onUserGuessChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val paddingSmall = dimensionResource(id = R.dimen.padding_small)

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(paddingMedium),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(paddingMedium)
            ) {
                Text(
                    text = stringResource(id = R.string.word_count_v_v, wordCount, 10),
                    style = MaterialTheme.typography.titleMedium,
                    color = colorScheme.onPrimary,
                    modifier = Modifier
                        .clip(shapes.medium)
                        .background(colorScheme.surfaceTint)
                        .padding(paddingSmall)
                        .align(Alignment.End)
                )
                Text(
                    text = scrambledWord,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = stringResource(id = R.string.word_instruction),
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = shapes.large,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,
                        disabledContainerColor = colorScheme.surface
                    ),
                    value = userGuess,
                    onValueChange = onUserGuessChanged,
                    label = {
                        if (isUserGuessWrong) {
                            Text(text = stringResource(id = R.string.wrong_guess))
                        } else {
                            Text(text = stringResource(id = R.string.text_field_instruction))
                        }
                    },
                    isError = isUserGuessWrong
                )
            }
        }
}

@Composable
fun GameScore(score: Int, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.score_v, score),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
        )
    }
}

@Composable
fun GreetingDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
){
    val activity = (LocalContext.current as Activity)
    
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {},
        title = { Text(text = stringResource(id = R.string.congratulations))},
        text = { Text(text = stringResource(id = R.string.you_scored_v, score))},
        dismissButton = { 
            TextButton(onClick = { activity.finish() }) {
                Text(text = stringResource(id = R.string.exit))
            }
        },
        confirmButton = {
            TextButton(onClick = { onPlayAgain() }) {
                Text(text = stringResource(id = R.string.play_again))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    UnscrambleTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            GameScreen()
        }
    }
}