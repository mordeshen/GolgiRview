package com.e.golgirview.di.main

import androidx.lifecycle.ViewModel
import com.e.golgirview.di.ViewModelKey
import com.e.golgirview.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

}