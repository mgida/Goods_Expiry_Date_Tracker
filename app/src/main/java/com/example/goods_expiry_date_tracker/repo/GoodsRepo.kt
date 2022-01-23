package com.example.goods_expiry_date_tracker.repo

import androidx.lifecycle.LiveData
import com.example.goods_expiry_date_tracker.data.database.GoodsDao
import com.example.goods_expiry_date_tracker.data.model.ItemModel
import javax.inject.Inject

class GoodsRepo @Inject constructor(private val goodsDao: GoodsDao) {

    suspend fun insertItem(itemModel: ItemModel) = goodsDao.insertItem(itemModel)

    fun getItemsFromDB(): LiveData<List<ItemModel>> = goodsDao.getUnExpiredItems()

    fun getExpiredItems(): LiveData<List<ItemModel>> = goodsDao.getExpiredItems()

//    suspend fun updateItem(itemModel: ItemModel) = goodsDao.updateItem(itemModel)
//
//    fun selectItemByID(id: Int): LiveData<ItemModel> = goodsDao.selectItemByID(id)
//
//    suspend fun deleteItem(item: ItemModel) = goodsDao.deleteItem(item)

}