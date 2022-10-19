package com.example.roomwordcoroutine

class WordRepository(private  val wordDao: WordDao) {

    val allWords = wordDao.getAlphabetizedWords()

    suspend fun insert(word: Word){
        wordDao.insert(word)
    }
}