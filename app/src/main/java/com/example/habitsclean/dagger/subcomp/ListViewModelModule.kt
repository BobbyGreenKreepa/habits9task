package com.example.habitsclean.dagger.subcomp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.model.HabitType
import com.example.domain.use_cases.DeleteHabitCase
import com.example.domain.use_cases.GetHabitsCase
import com.example.domain.use_cases.LoadRemoteDataCase
import com.example.domain.use_cases.PostHabitCase
import com.example.habitsclean.ui.list.ListFragment
import com.example.habitsclean.ui.list.ListViewModel
import dagger.Module
import dagger.Provides

@Module
class ListViewModelModule(

    private val listFragment: ListFragment,
    private val habitType: HabitType

) {

    @Provides
    fun provideListViewModel(

        getHabitsCase: GetHabitsCase,
        deleteHabitCase: DeleteHabitCase,
        postHabitCase: PostHabitCase,
        loadRemoteDataCase: LoadRemoteDataCase

    ): ListViewModel {

        return ViewModelProvider(listFragment, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ListViewModel(
                    getHabitsCase, deleteHabitCase,
                    loadRemoteDataCase, postHabitCase, habitType
                ) as T
            }
        }).get(ListViewModel::class.java)
    }
}