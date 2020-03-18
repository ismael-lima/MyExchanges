package com.master.myexchanges.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.master.myexchanges.data.model.SearchData


@Dao
interface SearchDao{
    @Query("SELECT * FROM SearchData WHERE userId = :userId order by date desc")
    fun getByUserId(userId: String): List<SearchData>

    @Insert
    fun inserir(searchData: SearchData)
}