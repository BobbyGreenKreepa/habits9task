package com.example.habitsclean.dagger

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.habitsclean.dagger.subcomp.ListViewModelComponent
import com.example.habitsclean.dagger.subcomp.ListViewModelModule
import com.example.habitsclean.dagger.subcomp.RedactorViewModelComponent
import com.example.habitsclean.dagger.subcomp.RedactorViewModelModule
import com.example.habitsclean.ui.list.ListBottomSheetFragment
import com.example.habitsclean.ui.list.ListFragment
import com.example.habitsclean.ui.redactor.ColorDialogFragment
import com.example.habitsclean.ui.redactor.RedactorFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MainModule::class,
        HabitModule::class,
        ContextModule::class
    ]
)
interface ApplicationComponent {

    fun getRedactorViewModelComponent(): RedactorViewModelComponent.Builder
    fun getListViewModelComponent(): ListViewModelComponent.Builder

}

fun Fragment.getApplicationComponent() = (requireActivity().application as? DaggerApp)?.applicationComponent