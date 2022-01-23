package com.example.goods_expiry_date_tracker.di

import android.content.Context
import androidx.room.Room
import com.example.goods_expiry_date_tracker.data.database.GoodsDao
import com.example.goods_expiry_date_tracker.data.database.GoodsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): GoodsDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            GoodsDatabase::class.java,
            "goods.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideGoodsDao(goodsDatabase: GoodsDatabase): GoodsDao =
        goodsDatabase.getGoodsDao()

}