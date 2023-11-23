package com.example.mypet.di

import com.example.mypet.data.AlarmRepositoryImpl
import com.example.mypet.data.AlarmServiceRepositoryImpl
import com.example.mypet.data.BootCompleteRepositoryImpl
import com.example.mypet.data.FoodDetailRepositoryImpl
import com.example.mypet.data.FoodRepositoryImpl
import com.example.mypet.data.PetRepositoryImpl
import com.example.mypet.domain.AlarmRepository
import com.example.mypet.domain.AlarmServiceRepository
import com.example.mypet.domain.BootCompleteRepository
import com.example.mypet.domain.FoodDetailRepository
import com.example.mypet.domain.FoodRepository
import com.example.mypet.domain.PetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    @Singleton
    fun providePetRepository(petRepositoryImpl: PetRepositoryImpl): PetRepository =
        petRepositoryImpl

    @Provides
    @Singleton
    fun provideFoodRepository(foodRepositoryImpl: FoodRepositoryImpl): FoodRepository =
        foodRepositoryImpl

    @Provides
    @Singleton
    fun provideFoodDetailRepository(foodDetailRepositoryImpl: FoodDetailRepositoryImpl): FoodDetailRepository =
        foodDetailRepositoryImpl

    @Provides
    @Singleton
    fun provideAlarmRepository(alarmRepositoryImpl: AlarmRepositoryImpl): AlarmRepository =
        alarmRepositoryImpl

    @Provides
    @Singleton
    fun provideAlarmServiceRepository(alarmServiceRepositoryImpl: AlarmServiceRepositoryImpl): AlarmServiceRepository =
        alarmServiceRepositoryImpl

    @Provides
    @Singleton
    fun provideBootCompleteRepository(bootCompleteRepositoryImpl: BootCompleteRepositoryImpl): BootCompleteRepository =
        bootCompleteRepositoryImpl
}