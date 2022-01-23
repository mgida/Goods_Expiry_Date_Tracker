package com.example.goods_expiry_date_tracker.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "goods")
data class ItemModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    val type: String,
    val date: String,
    val daysUntilExpired: String,
    var isExpired: Boolean = false

) : Parcelable