package com.example.roomwordssample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomwordssample.data.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordViewModel(app: Application) : AndroidViewModel(app) {
    val wordRepository = WordRepository(app)

    val words = wordRepository.getAllWords()

    fun insertWord(word: Word) {
        viewModelScope.launch {
            wordRepository.insertWord(word)
        }
    }

    fun deleteAllWords() {
        viewModelScope.launch {
            wordRepository.deleteAllWords()
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            wordRepository.deleteWord(word)
        }
    }

    fun updateWord(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            wordRepository.updateWord(word)
        }
    }

}