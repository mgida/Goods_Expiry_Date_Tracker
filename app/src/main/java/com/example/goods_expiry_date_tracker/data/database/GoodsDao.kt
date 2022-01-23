package com.example.goods_expiry_date_tracker.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.goods_expiry_date_tracker.data.model.ItemModel

@Dao
interface GoodsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(itemModel: ItemModel)

    @Update
    suspend fun updateItem(itemModel: ItemModel)

    @Query("SELECT * FROM goods WHERE daysUntilExpired != 0 ORDER BY date DESC ")
    fun getUnExpiredItems(): LiveData<List<ItemModel>>

    @Query("SELECT * FROM goods WHERE daysUntilExpired = 0")
    fun getExpiredItems(): LiveData<List<ItemModel>>

    @Query("select * from goods where ID = :id")
    fun selectItemByID(id: Int): LiveData<ItemModel>

    @Delete
    suspend fun deleteItem(itemModel: ItemModel)

}