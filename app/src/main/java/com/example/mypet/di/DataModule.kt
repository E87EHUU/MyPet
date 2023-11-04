package com.example.mypet.di

import android.content.Context
import androidx.room.Room
import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.alarm.impl.AlarmDaoImpl
import com.example.mypet.data.local.room.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private const val DB_NAME = "my_pet"
    private const val DB_FILE_NAME = "$DB_NAME.db"

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, LocalDatabase::class.java, DB_NAME)
            .createFromAsset(DB_FILE_NAME)
            //.fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideLocalPetDetailDao(db: LocalDatabase) =
        db.localPetDetailDao()

    @Singleton
    @Provides
    fun provideLocalAlarmDao(db: LocalDatabase) =
        db.localFoodDetailAlarmDao()

    @Singleton
    @Provides
    fun provideAlarmDao(alarmDaoImpl: AlarmDaoImpl): AlarmDao =
        alarmDaoImpl
}