package com.example.roomwordsample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.roomwordsample.data.Word
import com.example.roomwordsample.data.WordDao
import com.example.roomwordsample.data.WordRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordViewModel(val dao: WordDao): ViewModel() {
    //var mRepository: WordRepository= WordRepository(app)
    //val dao = WordRoomDatabase.getInstance(app.applicationContext).wordDao()


    fun getAllWords() = dao.getAlphabetized()

  /*  fun insert(word: Word){
        viewModelScope.launch(Dispatchers.IO) { mRepository.insert(word) }

    }*/
}
class WordViewModelFatory(private val dao: WordDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)){
            return WordViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
