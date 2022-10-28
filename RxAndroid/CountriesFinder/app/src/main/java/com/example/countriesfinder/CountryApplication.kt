package com.example.countriesfinder

import android.app.Application
import com.example.countriesfinder.data.CountryDatabase

class CountryApplication : Application() {
    val database: CountryDatabase by lazy { CountryDatabase.getDatabase(this) }
}