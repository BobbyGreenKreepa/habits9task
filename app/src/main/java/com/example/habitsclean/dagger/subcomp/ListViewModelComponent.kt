package com.example.habitsclean.dagger.subcomp

import com.example.habitsclean.ui.list.ListFragment
import com.example.habitsclean.ui.redactor.RedactorFragment
import dagger.Subcomponent

@Subcomponent(modules = [ListViewModelModule::class])
interface ListViewModelComponent {

    @Subcomponent.Builder
    interface Builder {
        fun getModule(module: ListViewModelModule?): Builder?
        fun build(): ListViewModelComponent?
    }

    fun inject(fragment: ListFragment)
}