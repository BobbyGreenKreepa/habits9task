package com.example.habitsclean.ui.redactor

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Habit
import com.example.domain.use_cases.AddHabitCase
import com.example.domain.use_cases.DeleteHabitCase
import com.example.domain.use_cases.UpdateHabitCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RedactorViewModel @Inject constructor(

    private val addHabitCase: AddHabitCase,
    private val updateHabitCase: UpdateHabitCase

) : ViewModel() {

    var color: MutableLiveData<Int> = MutableLiveData(Color.BLUE)

    fun updateHabit(habit: Habit) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            updateHabitCase.execute(habit)
        }
    }

    fun addHabit(habit: Habit) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            addHabitCase.execute(habit)
        }
    }
}