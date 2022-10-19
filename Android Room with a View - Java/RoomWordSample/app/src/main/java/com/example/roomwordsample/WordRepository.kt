package com.example.roomwordsample

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.roomwordsample.data.Word
import com.example.roomwordsample.data.WordDao
import com.example.roomwordsample.data.WordRoomDatabase


class WordRepository(application: Application) {
     var mWordDao : WordDao = WordRoomDatabase.getInstance(application.applicationContext).wordDao()


    fun getAllWords(): LiveData<List<Word>> = mWordDao.getAlphabetized()

    suspend fun insert(word: Word){
        //WordRoomDatabase.databaseWriteExecutor.execute { mWordDao.insertWord(word) }
        mWordDao.insertWord(word)
    }
}