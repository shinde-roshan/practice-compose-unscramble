package com.example.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.POINTS_PER_WORD
import com.example.unscramble.data.WordsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var usedWords = mutableSetOf<String>()
    private lateinit var currentWord: String

    var userGuess by mutableStateOf("")
        private set

    init {
        resetGame()
    }

    private fun pickRandomWordAndShuffle(): String {
        currentWord = WordsData.random()
        return if (usedWords.contains(currentWord)) {
            pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            shuffleWord(currentWord)
        }
    }

    private fun shuffleWord(word: String): String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while (String(tempWord) == word) tempWord.shuffle()
        return String(tempWord)
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    fun skipCurrentWord() {
        updateGameStateToNextWord(updatedScore = _uiState.value.score)
    }

    fun checkUserGuess() {
        if (userGuess.equals(currentWord, true)) {
            updateGameStateToNextWord(
                updatedScore = _uiState.value.score.plus(POINTS_PER_WORD)
            )
        } else {
            _uiState.update {
                it.copy(isGuessedWordWrong = true)
            }
        }
    }

    private fun updateGameStateToNextWord(updatedScore: Int) {
        _uiState.update {
            it.copy(
                currentScrambledWord = pickRandomWordAndShuffle(),
                isGuessedWordWrong = false,
                score = updatedScore,
                currentWordCount = it.currentWordCount.inc()
            )
        }
        updateUserGuess("")
    }

}