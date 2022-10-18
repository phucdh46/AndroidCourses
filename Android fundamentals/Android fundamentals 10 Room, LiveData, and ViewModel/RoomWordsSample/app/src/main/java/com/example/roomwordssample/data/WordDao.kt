package com.example.roomwordssample.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: Word)

    @Update
    fun update(word: Word)

    @Query("DELETE FROM word_table ")
    fun deleteAll()

    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAllWords(): LiveData<List<Word>>

    @Query("SELECT * from word_table LIMIT 1")
    fun getAnyWord(): Word

    @Delete
    fun deleteWord(word: Word)
}