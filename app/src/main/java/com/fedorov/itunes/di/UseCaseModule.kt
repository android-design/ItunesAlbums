package com.fedorov.itunes.di

import com.fedorov.itunes.data.model.Entity
import com.fedorov.itunes.domain.base.UseCase
import com.fedorov.itunes.domain.interactor.GetAlbumInfoUseCase
import com.fedorov.itunes.domain.interactor.GetAlbumsUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun provideAlbumsUseCase(getAlbumsUseCase: GetAlbumsUseCase): UseCase<String, Entity>

    @Binds
    @Singleton
    abstract fun provideAlbumInfoUseCase(getAlbumInfoUseCase: GetAlbumInfoUseCase): UseCase<Int, Entity>
}