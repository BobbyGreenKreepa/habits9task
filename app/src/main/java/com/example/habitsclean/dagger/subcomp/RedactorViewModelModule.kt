package com.example.habitsclean.dagger.subcomp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.use_cases.AddHabitCase
import com.example.domain.use_cases.UpdateHabitCase
import com.example.habitsclean.ui.redactor.RedactorFragment
import com.example.habitsclean.ui.redactor.RedactorViewModel
import dagger.Module
import dagger.Provides

@Module
class RedactorViewModelModule(

    private val redactorFragment: RedactorFragment

) {

    @Provides
    fun providesRedactorViewModel(

        addHabitCase: AddHabitCase,
        updateHabitCase: UpdateHabitCase

    ): RedactorViewModel {

        return ViewModelProvider(redactorFragment, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RedactorViewModel (
                    addHabitCase, updateHabitCase
                ) as T
            }
        }).get(RedactorViewModel::class.java)
    }
}