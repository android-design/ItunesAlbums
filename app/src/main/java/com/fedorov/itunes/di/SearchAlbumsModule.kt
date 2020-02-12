package com.fedorov.itunes.di

import androidx.lifecycle.ViewModel
import com.fedorov.itunes.ui.searchAlbums.SearchAlbumsFragment
import com.fedorov.itunes.ui.searchAlbums.SearchAlbumsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Dagger module for the SearchAlbumFragment.
 */
@Module
abstract class SearchAlbumsModule {

    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
        ])
    internal abstract fun searchAlbumsFragment(): SearchAlbumsFragment

    @Binds
    @IntoMap
    @ViewModelKey(SearchAlbumsViewModel::class)
    abstract fun bindViewModel(viewmodel: SearchAlbumsViewModel): ViewModel
}
