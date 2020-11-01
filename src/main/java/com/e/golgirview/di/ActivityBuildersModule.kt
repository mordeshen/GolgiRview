package com.e.golgirview.di


import com.e.golgirview.di.main.MainFragmentBuildersModule
import com.e.golgirview.di.main.MainModule
import com.e.golgirview.di.main.MainScope
import com.e.golgirview.di.main.MainViewModelModule
import com.e.golgirview.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {


    @MainScope
    @ContributesAndroidInjector(
        modules = [MainModule::class, MainFragmentBuildersModule::class, MainViewModelModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

}