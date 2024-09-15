package com.example.firebaseapp.di

import com.example.firebaseapp.feature.firebase_auth.repository.AuthRepository
import com.example.firebaseapp.feature.firebase_auth.repository.AuthRepositoryImpl
import com.example.firebaseapp.feature.firestore.repository.FirestoreDbRepositoryImpl
import com.example.firebaseapp.feature.firestore.repository.FirestoreRepository
import com.example.firebaseapp.feature.firebaserealtimedb.repository.RealtimeDbRepository
import com.example.firebaseapp.feature.firebaserealtimedb.repository.RealtimeRepository
import com.google.firebase.auth.FirebaseAuth
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

    @Binds
    abstract fun provideFirebaseAuthRepository(
        repo: AuthRepositoryImpl
    ): AuthRepository
}