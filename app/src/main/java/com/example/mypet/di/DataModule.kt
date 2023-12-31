package com.example.mypet.di

import android.content.Context
import androidx.room.Room
import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.alarm.IAlarmDao
import com.example.mypet.data.local.room.LocalDatabase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
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
            //.createFromAsset(DB_FILE_NAME)
            //.fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideLocalPetDao(db: LocalDatabase) =
        db.localPetDao()

    @Singleton
    @Provides
    fun provideLocalCareDao(db: LocalDatabase) =
        db.localCareDao()

    @Singleton
    @Provides
    fun provideLocalAlarmDao(db: LocalDatabase) =
        db.localAlarmDao()

    @Singleton
    @Provides
    fun provideLocalAlarmReceiverDao(db: LocalDatabase) =
        db.localAlarmReceiverDao()

    @Singleton
    @Provides
    fun provideLocalAlarmServiceDao(db: LocalDatabase) =
        db.localAlarmServiceDao()

    @Singleton
    @Provides
    fun provideLocalBootCompleteDao(db: LocalDatabase) =
        db.localBootCompleteDao()

    @Singleton
    @Provides
    fun provideAlarmDao(alarmDao: AlarmDao): IAlarmDao =
        alarmDao

    @Singleton
    @Provides
    fun provideLocalPetCreationDao(db: LocalDatabase) =
        db.localPetCreationDao()

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth =
        Firebase.auth
}