package com.example.mypet.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import com.example.mypet.data.PetDetailRepositoryImpl
import com.example.mypet.domain.PetDetailRepository

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {
    @Provides
    fun providePetDetailRepository(petDetailRepositoryImpl: PetDetailRepositoryImpl): PetDetailRepository =
        petDetailRepositoryImpl
}