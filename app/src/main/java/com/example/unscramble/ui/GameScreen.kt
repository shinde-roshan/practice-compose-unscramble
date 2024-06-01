package com.example.unscramble.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.unscramble.R
import com.example.unscramble.ui.theme.UnscrambleTheme

@Composable
fun GameScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineSmall
        )
        GameLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        )
    }
}

@Composable
fun GameLayout(modifier: Modifier = Modifier) {
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val paddingSmall = dimensionResource(id = R.dimen.padding_small)

    Card(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(paddingMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMedium)
        ) {
            Text(
                text = stringResource(id = R.string.word_count_v_v, 0, 10),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(paddingSmall)
                    .align(Alignment.End)
            )
            Text(
                text = "Scrambled",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = stringResource(id = R.string.word_instruction),
                style = MaterialTheme.typography.titleMedium
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = stringResource(id = R.string.text_field_instruction)) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
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