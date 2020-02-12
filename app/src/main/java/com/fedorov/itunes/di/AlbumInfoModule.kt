package com.fedorov.itunes.di

import androidx.lifecycle.ViewModel
import com.fedorov.itunes.ui.albumInfo.AlbumInfoFragment
import com.fedorov.itunes.ui.albumInfo.AlbumInfoViewModel
import com.fedorov.itunes.ui.searchAlbums.SearchAlbumsFragment
import com.fedorov.itunes.ui.searchAlbums.SearchAlbumsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Dagger module for the AlbumInfoFragment.
 */
@Module
abstract class AlbumInfoModule {

    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
        ])
    internal abstract fun albumInfoFragment(): AlbumInfoFragment

    @Binds
    @IntoMap
    @ViewModelKey(AlbumInfoViewModel::class)
    abstract fun bindViewModel(viewmodel: AlbumInfoViewModel): ViewModel
}
