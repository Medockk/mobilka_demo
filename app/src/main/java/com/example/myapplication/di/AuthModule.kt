package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.data.data_source.AuthApi
import com.example.myapplication.data.data_source.Constants
import com.example.myapplication.data.repository.AuthRepositoryImpl
import com.example.myapplication.domain.repository.AuthRepository
import com.example.myapplication.domain.usecase.Auth.RefreshTokenUseCase
import com.example.myapplication.domain.usecase.Auth.SignInUseCase
import com.example.myapplication.domain.usecase.Auth.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<AuthApi>()

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi, @ApplicationContext context: Context): AuthRepository =
        AuthRepositoryImpl(authApi, context)


    @Provides
    @Singleton
    fun provideSignInUseCase(repo: AuthRepository) =
        SignInUseCase(repo)

    @Provides
    @Singleton
    fun provideSignUpUseCase(repo: AuthRepository) =
        SignUpUseCase(repo)

    @Provides
    @Singleton
    fun provideRefreshTokenUseCase(repo: AuthRepository) =
        RefreshTokenUseCase(repo)

}