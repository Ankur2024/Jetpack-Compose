package com.example.firebaseapp.di

import com.example.firebaseapp.firestore.repository.FirestoreDbRepositoryImpl
import com.example.firebaseapp.firestore.repository.FirestoreRepository
import com.example.firebaseapp.repository.RealtimeDbRepository
import com.example.firebaseapp.repository.RealtimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesRealtimeRepository(
        repo: RealtimeDbRepository
    ): RealtimeRepository

    @Binds
    abstract fun provideFirestoreRepository(
        repo: FirestoreDbRepositoryImpl
    ): FirestoreRepository
}