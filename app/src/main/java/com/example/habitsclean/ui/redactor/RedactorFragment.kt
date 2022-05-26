package com.example.habitsclean.ui.redactor

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.domain.model.Habit
import com.example.domain.model.HabitPriority
import com.example.domain.model.HabitType
import com.example.habitsclean.R
import com.example.habitsclean.closeKeyBoard
import com.example.habitsclean.dagger.getApplicationComponent
import com.example.habitsclean.dagger.subcomp.ListViewModelModule
import com.example.habitsclean.dagger.subcomp.RedactorViewModelModule
import com.example.habitsclean.databinding.FragmentRedactorHabitBinding
import com.example.habitsclean.ui.list.ListFragment
import com.example.habitsclean.utils.LoadingScreen
import javax.inject.Inject

class RedactorFragment : Fragment() {

    @Inject
    lateinit var viewModel: RedactorViewModel
    private var binding: FragmentRedactorHabitBinding? = null
    private var oldHabit: Habit? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        getApplicationComponent()?.getRedactorViewModelComponent()?.getModule(
            RedactorViewModelModule(this))?.build().also { it?.inject(this) }

        binding = FragmentRedactorHabitBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.color.observe(viewLifecycleOwner) {
            binding?.colorButton?.backgroundTintList = ColorStateList.valueOf(it)
        }

        arguments?.getSerializable(ListFragment.HABIT)?.let {
            updateUiForRedacting(it as Habit)
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.change_habit)
        }
        binding?.colorButton?.setOnClickListener { ColorDialogFragment().show(childFragmentManager, null) }
        binding?.habitSaveButton?.setOnClickListener { saveHabit() }
    }

    private fun validateFields(): Boolean {
        var result = true

        if (binding?.typeRadioGroup?.checkedRadioButtonId == -1) {
            result = false
            binding?.habitCurrentType?.setTextColor(Color.RED)
        }

        if (binding?.editDays?.text.isNullOrEmpty() || binding?.editTimes?.text.isNullOrEmpty()) {
            result = false
            binding?.habitFrequency?.setTextColor(Color.RED)
        }

        if (binding?.editHabitName?.text.isNullOrEmpty()) {
            result = false
            binding?.habitNameText?.setHintTextColor(Color.RED)
        }

        if (binding?.editDescription?.text.isNullOrEmpty()) {
            result = false
            binding?.habitDescriptionText?.setHintTextColor(Color.RED)
        }

        closeKeyBoard()
        return result
    }

    private fun collectHabit() = Habit(

        title = binding!!.editHabitName.text.toString(),
        description = binding!!.editDescription.text.toString(),
        type = HabitType.fromInt(binding!!.typeRadioGroup
                .indexOfChild(requireView().findViewById(binding!!.typeRadioGroup.checkedRadioButtonId))),
        priority = HabitPriority.fromInt(binding!!.prioritySpinner.selectedItemPosition),
        count = Integer.valueOf(binding!!.editTimes.text.toString()),
        frequency = Integer.valueOf(binding!!.editDays.text.toString()),
        color = viewModel.color.value!!,
        date = 0,
        uid = oldHabit?.uid ?: ""
    )

    private fun saveHabit() {

        if (validateFields()) {

            LoadingScreen.showLoading(context)

            val job = if (oldHabit == null) viewModel.addHabit(collectHabit()) else viewModel.updateHabit(collectHabit())
            job.invokeOnCompletion { backToHabitList() }
        }
    }

    private fun backToHabitList() {

        LoadingScreen.hideLoading()
        val navController = activity?.findNavController(R.id.nav_host_fragment_content_main)
        navController!!.navigate(R.id.action_redactor_to_list)
    }

    private fun updateUiForRedacting(habit: Habit) = binding?.apply {

        oldHabit = habit
        viewModel.color.value = habit.color
        editHabitName.setText(habit.title)
        editDescription.setText(habit.description)
        prioritySpinner.setSelection(habit.priority.value)
        editDays.setText(habit.frequency.toString())
        editTimes.setText(habit.count.toString())

        when (habit.type) {
            HabitType.USEFUL -> typeRadioGroup.check(R.id.first_radio)
            HabitType.HARMFUL -> typeRadioGroup.check(R.id.second_radio)
        }
    }

    override fun onDestroyView() {

        super.onDestroyView()
        binding = null
    }
}