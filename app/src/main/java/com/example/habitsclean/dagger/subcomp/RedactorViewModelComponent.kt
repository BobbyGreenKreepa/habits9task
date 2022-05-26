package com.example.habitsclean.dagger.subcomp

import com.example.habitsclean.ui.redactor.RedactorFragment
import dagger.Subcomponent

@Subcomponent(modules = [RedactorViewModelModule::class])
interface RedactorViewModelComponent {

    fun inject(fragment: RedactorFragment)

    @Subcomponent.Builder
    interface Builder{
        fun getModule(module: RedactorViewModelModule?): Builder?
        fun build(): RedactorViewModelComponent?
    }
}