package com.example.habitsclean.ui.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Habit
import com.example.habitsclean.R
import com.example.habitsclean.databinding.HabitListItemBinding
import kotlinx.android.extensions.LayoutContainer

class ItemHabitAdapter(private val context: Context?, private val onItemClick: (Habit) -> Unit)
    : RecyclerView.Adapter<ItemHabitAdapter.HabitViewHolder>() {

    private var habits: List<Habit> = ArrayList()
    private var binding: HabitListItemBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {

        binding = HabitListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding!!.root)
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    fun refreshHabits(habitList: List<Habit>) {
        this.habits = habitList
        notifyDataSetChanged()
    }

    inner class HabitViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            itemView.setOnClickListener { onItemClick.invoke(habits[adapterPosition]) }
        }

        fun bind(habit: Habit) {
            val times = context?.resources?.getQuantityString(R.plurals.count, habit.count, habit.count)
            val days = context?.resources?.getQuantityString(R.plurals.times, habit.frequency, habit.frequency)

            binding?.habitName?.text = habit.title
            "${context?.getString(R.string.repeat)} $times ${context?.getString(R.string.`in`)} $days".also { binding?.habitDescription?.text = it }
            binding?.colorCard?.backgroundTintList = ColorStateList.valueOf(habit.color)
        }
    }
}