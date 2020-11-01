package com.e.golgirview.di.main

import com.e.golgirview.ui.main.LauncherFragment
import com.e.golgirview.ui.main.LoginFragment
import com.e.golgirview.ui.main.QuestionFragment
import com.e.golgirview.ui.main.SummaryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeMainFragment(): LauncherFragment

    @ContributesAndroidInjector()
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector()
    abstract fun contributeQuestionFragment(): QuestionFragment

    @ContributesAndroidInjector()
    abstract fun contributeSummaryFragment(): SummaryFragment

}