package com.example.countriesfinder.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Update

@Dao
interface CountryDao {

    @Insert(onConflict = IGNORE)
    fun insert(country: Country)

    @Insert(onConflict = IGNORE)
    fun insertAll(countries: List<Country>): List<Long>

    @Update
    fun update(country: Country)

    @Query("SELECT * FROM tb_countries")
    fun getAll(): LiveData<List<Country>>

    @Query("DELETE FROM tb_countries")
    fun deleteAll()

    @Query("SELECT * FROM tb_countries WHERE name LIKE :query")
    fun search(query: String): List<Country>

}