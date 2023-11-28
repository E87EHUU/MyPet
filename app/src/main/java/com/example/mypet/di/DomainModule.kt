package com.example.mypet.di

import com.example.mypet.data.AlarmRepositoryImpl
import com.example.mypet.data.AlarmServiceRepositoryImpl
import com.example.mypet.data.BootCompleteRepositoryImpl
import com.example.mypet.data.CareRepositoryImpl
import com.example.mypet.data.PetCreationRepositoryImpl
import com.example.mypet.data.PetRepositoryImpl
import com.example.mypet.domain.AlarmRepository
import com.example.mypet.domain.AlarmServiceRepository
import com.example.mypet.domain.BootCompleteRepository
import com.example.mypet.domain.CareRepository
import com.example.mypet.domain.PetCreationRepository
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
    fun provideCareRepository(careRepositoryImpl: CareRepositoryImpl): CareRepository =
        careRepositoryImpl

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

    @Provides
    fun providePetCreationRepository(petCreationRepositoryImpl: PetCreationRepositoryImpl): PetCreationRepository =
        petCreationRepositoryImpl
}