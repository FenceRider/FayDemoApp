package com.example.faydemo.di

import com.example.faydemo.network.makeOpenClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class OpenNetwork

@Module
@InstallIn(SingletonComponent::class)
object OpenNetworkModule {

    @OpenNetwork
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return makeOpenClient()
    }
}