package com.example.mypet.di

import com.example.mypet.data.FoodDetailAlarmRepositoryImpl
import com.example.mypet.data.PetDetailRepositoryImpl
import com.example.mypet.domain.FoodDetailAlarmRepository
import com.example.mypet.domain.PetDetailRepository
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
    fun providePetDetailRepository(petDetailRepositoryImpl: PetDetailRepositoryImpl): PetDetailRepository =
        petDetailRepositoryImpl

    @Provides
    @Singleton
    fun provideFoodDetailAlarmRepository(foodDetailAlarmRepositoryImpl: FoodDetailAlarmRepositoryImpl): FoodDetailAlarmRepository =
        foodDetailAlarmRepositoryImpl
}