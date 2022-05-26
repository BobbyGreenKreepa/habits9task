package com.example.habitsclean.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.habitsclean.dagger.getApplicationComponent
import com.example.habitsclean.databinding.BottomSheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class ListBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var viewModel: ListViewModel
    private var binding: BottomSheetFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        viewModel = ViewModelProvider(requireParentFragment()).get(ListViewModel::class.java)
        binding = BottomSheetFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSort()
        setupSearch()
    }

    private fun setupSearch() {

        binding?.habitSearch?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?) = false.also {

                viewModel.filter.filter(newText)
            }
        })
    }

    private fun setupSort() {

        binding?.sortSpinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    l: Long
                ) {
                    viewModel.sortList(position)
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
    }
}