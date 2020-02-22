package com.fedorov.itunes.di

import androidx.lifecycle.ViewModel
import com.fedorov.itunes.ui.fragment.AlbumInfoFragment
import com.fedorov.itunes.ui.vm.AlbumsViewModel
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
    @ViewModelKey(AlbumsViewModel::class)
    abstract fun bindViewModel(viewModel: AlbumsViewModel): ViewModel
}
