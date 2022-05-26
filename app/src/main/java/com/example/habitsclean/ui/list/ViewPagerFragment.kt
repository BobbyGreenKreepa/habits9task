package com.example.habitsclean.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.domain.model.HabitType
import com.example.habitsclean.R
import com.example.habitsclean.databinding.ViewPagerFragmentBinding
import com.example.habitsclean.ui.adapters.HabitPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {

    private var binding: ViewPagerFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ViewPagerFragmentBinding.inflate(inflater, container, false)

        val fragmentList = arrayListOf<Fragment>(
            ListFragment.newInstance(HabitType.USEFUL),
            ListFragment.newInstance(HabitType.HARMFUL)
        )

        val adapter = HabitPagerAdapter(
            activity as AppCompatActivity,
            fragmentList
        )

        binding!!.viewPager.adapter = adapter

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        TabLayoutMediator(binding!!.tablay, binding!!.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> resources.getString(R.string.good_habits)
                1 -> resources.getString(R.string.bad_habits)
                else -> throw Exception("no more habits")
            }

            binding!!.viewPager.setCurrentItem(tab.position, true)

        }.attach()
    }

}