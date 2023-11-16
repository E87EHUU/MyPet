package com.example.mypet.di

import com.example.mypet.data.BootCompleteRepositoryImpl
import com.example.mypet.data.FoodAlarmRepositoryImpl
import com.example.mypet.data.FoodAlarmServiceRepositoryImpl
import com.example.mypet.data.FoodRepositoryImpl
import com.example.mypet.data.PetRepositoryImpl
import com.example.mypet.domain.BootCompleteRepository
import com.example.mypet.domain.FoodAlarmRepository
import com.example.mypet.domain.FoodAlarmServiceRepository
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
    fun provideFoodAlarmRepository(foodAlarmRepositoryImpl: FoodAlarmRepositoryImpl): FoodAlarmRepository =
        foodAlarmRepositoryImpl

    @Provides
    @Singleton
    fun provideFoodAlarmServiceRepository(foodAlarmServiceRepositoryImpl: FoodAlarmServiceRepositoryImpl): FoodAlarmServiceRepository =
        foodAlarmServiceRepositoryImpl

    @Provides
    @Singleton
    fun provideBootCompleteRepository(bootCompleteRepositoryImpl: BootCompleteRepositoryImpl): BootCompleteRepository =
        bootCompleteRepositoryImpl
}