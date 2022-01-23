package com.example.goods_expiry_date_tracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.goods_expiry_date_tracker.data.model.ItemModel

@Database(entities = [ItemModel::class], version = 1, exportSchema = false)
abstract class GoodsDatabase : RoomDatabase() {

    abstract fun getGoodsDao(): GoodsDao
}


