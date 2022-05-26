package com.example.habitsclean.ui.list

import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Habit
import com.example.domain.model.HabitType
import com.example.domain.use_cases.DeleteHabitCase
import com.example.domain.use_cases.GetHabitsCase
import com.example.domain.use_cases.LoadRemoteDataCase
import com.example.domain.use_cases.PostHabitCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListViewModel @Inject constructor(

    private val getHabitsCase: GetHabitsCase,
    private val deleteHabitCase: DeleteHabitCase,
    private val loadRemoteDataCase: LoadRemoteDataCase,
    private val postHabitCase: PostHabitCase,
    private val habitType: HabitType

) : ViewModel(), Filterable {

    private val mutableHabits = MutableLiveData<List<Habit>>()
    val habits: LiveData<List<Habit>> = mutableHabits
    private var habitsNotFilteredList = mutableHabits.value

    private var observer: Observer<List<Habit>?> = Observer {

        mutableHabits.value = it?.filter { el -> el.type == habitType }
        habitsNotFilteredList = mutableHabits.value
    }

    init {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadRemoteData()
            }
            getHabitsCase.execute().asLiveData().observeForever(observer)
        }
    }

    override fun onCleared() {
        habits.removeObserver(observer)
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val searchString = constraint.toString()
                val searchResult = FilterResults()

                searchResult.values = when (searchString.isEmpty()) {

                    true -> habitsNotFilteredList
                    false -> habits.value?.filter { it.title.contains(searchString) }
                }

                return searchResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mutableHabits.value = results?.values as List<Habit>?
            }
        }
    }

    private fun loadRemoteData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            loadRemoteDataCase.execute()
        }
    }

    fun postHabit(habit: Habit, date: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            postHabitCase.execute(habit, date)
        }
    }

    fun deleteHabit(position: Int) = viewModelScope.launch {

        withContext(Dispatchers.IO) {
            deleteHabitCase.execute(mutableHabits.value!![position])
        }
    }

    fun sortList(position: Int) = viewModelScope.launch {
        when (position) {
            0 -> mutableHabits.postValue(mutableHabits.value?.sortedBy { el -> el.uid })
            1 -> mutableHabits.postValue(mutableHabits.value?.sortedBy { el -> el.frequency / el.count })
            2 -> mutableHabits.postValue(mutableHabits.value?.sortedBy { el -> el.priority.value })
        }
    }
}