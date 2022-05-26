package com.example.habitsclean.ui.list

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Habit
import com.example.domain.model.HabitType
import com.example.habitsclean.R
import com.example.habitsclean.dagger.getApplicationComponent
import com.example.habitsclean.dagger.subcomp.ListViewModelModule
import com.example.habitsclean.databinding.FragmentHabitsListBinding
import com.example.habitsclean.ui.adapters.ButtonClickListener
import com.example.habitsclean.ui.adapters.ItemHabitAdapter
import com.example.habitsclean.ui.adapters.MySwipeHelper
import com.example.habitsclean.utils.LoadingScreen
import java.util.*
import javax.inject.Inject

class ListFragment : Fragment() {

    companion object {

        const val HABIT = "habit"
        const val HABIT_TYPE = "habit_type"

        fun newInstance(habitType: HabitType): ListFragment {
            val fragment = ListFragment()
            val bundle = Bundle()
            bundle.putSerializable(HABIT_TYPE, habitType)
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var viewModel: ListViewModel
    private var binding: FragmentHabitsListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        getApplicationComponent()?.getListViewModelComponent()?.getModule(
            ListViewModelModule(this, arguments?.get(HABIT_TYPE) as HabitType))?.build().also {
                it?.inject(this)
        }

        binding = FragmentHabitsListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding!!.habitList.apply { adapter = ItemHabitAdapter(context) { habit -> postHabit(habit) } }
        viewModel.habits.observe(viewLifecycleOwner) {
            it?.let { (binding?.habitList?.adapter as ItemHabitAdapter).refreshHabits(it) }
        }

        createSwipeButtons()
        binding?.addHabitButton?.setOnClickListener { goToRedactorFragment() }
        createBottomSheet()
    }

    private fun createBottomSheet() = ListBottomSheetFragment().also {

        childFragmentManager.beginTransaction()
            .replace(R.id.containerBottomSheet, it)
            .commit()
    }

    private fun goToRedactorFragment(habit: Habit? = null) = findNavController()
        .navigate(R.id.action_nav_home_to_redactorFragment, Bundle().also { it.putSerializable(HABIT, habit) })

    private fun postHabit(habit: Habit) {

        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        viewModel.postHabit(habit, currentDay)

        val timesLeft = habit.count - habit.getCountDone(currentDay) - 1
        val text = when (habit.type) {
            HabitType.USEFUL -> when (timesLeft) {
                in 1 .. Int.MAX_VALUE -> resources.getString(R.string.useful_habit_not_enough_message, timesLeft)
                else -> resources.getString(R.string.useful_habit_enough_message)
            }
            HabitType.HARMFUL -> when (timesLeft) {
                in 1 .. Int.MAX_VALUE -> resources.getString(R.string.harmful_habit_not_enough_message, timesLeft)
                else -> resources.getString(R.string.harmful_habit_enough_message)
            }
        }

        val toast = Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 30)
        toast.show()
    }

    private fun createSwipeButtons() = object : MySwipeHelper(requireContext(), binding!!.habitList, 200) {

        override fun instantiateMyButtons(viewHolder: RecyclerView.ViewHolder, buffer: MutableList<MyButton>) {

            buffer.add(
                MyButton(
                    text = getString(R.string.delete_button_text),
                    imageResId = 0,
                    color = Color.RED,
                    textSize = 30,
                    listener = object : ButtonClickListener {
                        override fun onClick(pos: Int) {

                            LoadingScreen.showLoading(context)
                            viewModel.deleteHabit(pos).invokeOnCompletion {

                                LoadingScreen.hideLoading()
                                binding?.habitList?.adapter?.notifyItemRemoved(pos)
                            }
                        }
                    },
                    context = requireContext()
                )
            )

            buffer.add(
                MyButton(
                    text = getString(R.string.update_button_text),
                    imageResId = 0,
                    color = Color.parseColor("#FF9502"),
                    textSize = 30,
                    listener = object : ButtonClickListener {
                        override fun onClick(pos: Int) = goToRedactorFragment(viewModel.habits.value!![pos])
                    },
                    context = requireContext()
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}