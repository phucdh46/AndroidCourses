package com.example.roomwordsample.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWord(word: Word)

    @Query("DELETE FROM word_table")
    fun deleteAll()

    @Query("SELECT * FROM word_table ORDER BY word ASC")
     fun getAlphabetized(): LiveData<List<Word>>
}