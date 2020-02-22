package com.fedorov.itunes.di

import com.fedorov.itunes.ui.fragment.SearchAlbumsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Dagger module for the SearchAlbumFragment.
 */
@Module
abstract class SearchAlbumsModule {

    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
        ])
    internal abstract fun searchAlbumsFragment(): SearchAlbumsFragment
}
