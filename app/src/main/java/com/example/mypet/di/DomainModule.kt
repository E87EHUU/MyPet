package com.example.mypet.di

import com.example.mypet.data.PetCreationRepositoryImpl
import com.example.mypet.data.PetDetailRepositoryImpl
import com.example.mypet.domain.PetCreationRepository
import com.example.mypet.domain.PetDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {
    @Provides
    fun providePetDetailRepository(petDetailRepositoryImpl: PetDetailRepositoryImpl): PetDetailRepository =
        petDetailRepositoryImpl

    @Provides
    fun providePetCreationRepository(petCreationRepositoryImpl: PetCreationRepositoryImpl): PetCreationRepository =
        petCreationRepositoryImpl
}