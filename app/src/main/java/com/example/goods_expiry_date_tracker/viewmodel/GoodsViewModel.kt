package com.example.goods_expiry_date_tracker.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goods_expiry_date_tracker.data.model.ItemModel
import com.example.goods_expiry_date_tracker.repo.GoodsRepo
import com.example.goods_expiry_date_tracker.utils.Constant.Companion.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoodsViewModel @Inject constructor
    (private val repository: GoodsRepo) : ViewModel() {

    var id = "---"
    var name = "---"
    var type = "---"
    var date = "---"
//    private var _daysUntilExpired: MutableLiveData<String> =
//        MutableLiveData<String>()
//    val daysUntilExpired: LiveData<String> get() = _daysUntilExpired

    val unExpiredItems: LiveData<List<ItemModel>> = repository.getItemsFromDB()

    val expiredItems: LiveData<List<ItemModel>> = repository.getExpiredItems()

    fun insertItem(itemModel: ItemModel) {
        viewModelScope.launch {
            try {
                repository.insertItem(itemModel)
            } catch (e: Exception) {
                Log.d(TAG, "error occurred ${e.printStackTrace()}")
            }
        }
    }

    fun attachValues(id: String, name: String, type: String, date: String) {
        this.id = id
        this.name = name
        this.type = type
        this.date = date
    }

//    fun updateItem(itemModel: ItemModel) {
//        viewModelScope.launch {
//            try {
//                repository.updateItem(itemModel)
//            } catch (e: Exception) {
//                Log.d(TAG, "error occurred ${e.printStackTrace()}")
//            }
//        }
//    }
//
//    fun deleteItem(itemModel: ItemModel) {
//        viewModelScope.launch {
//            try {
//                repository.deleteItem(itemModel)
//            } catch (e: Exception) {
//                Log.d(TAG, "error occurred ${e.printStackTrace()}")
//            }
//        }
//    }
//
//    fun selectItemById(id: Int) = repository.selectItemByID(id)

//    fun attachDays(days: String) {
//        _daysUntilExpired.postValue(days)
//    }

}

