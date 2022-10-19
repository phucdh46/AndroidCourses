package com.example.roomwordsample

import android.app.Application
import com.example.roomwordsample.data.WordRoomDatabase

class BaseApplication : Application() {

    val database: WordRoomDatabase by lazy { WordRoomDatabase.getInstance(this) }
}