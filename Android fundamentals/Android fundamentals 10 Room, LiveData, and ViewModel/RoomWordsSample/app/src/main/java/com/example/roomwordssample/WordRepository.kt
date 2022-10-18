package com.example.roomwordssample

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.roomwordssample.data.Word
import com.example.roomwordssample.data.WordRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordRepository(app: Application) {


    val db = WordRoomDatabase.getDatabase(app.applicationContext)
    val mWordDao = db.wordDao()

    fun getAllWords(): LiveData<List<Word>> {
        return mWordDao.getAllWords()
    }

    fun getAnyWord() = mWordDao.getAnyWord()


    suspend fun insertWord(word: Word) {
        withContext(Dispatchers.IO) {
            mWordDao.insert(word)
        }
    }

    suspend fun deleteAllWords() {
        withContext(Dispatchers.IO) {
            mWordDao.deleteAll()
        }
    }

    suspend fun deleteWord(word: Word) {
        withContext(Dispatchers.IO) {
            mWordDao.deleteWord(word)
        }
    }

    fun updateWord(word: Word) {
        mWordDao.update(word)
    }
}