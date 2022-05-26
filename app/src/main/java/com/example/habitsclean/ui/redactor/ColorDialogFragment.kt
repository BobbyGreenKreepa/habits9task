package com.example.habitsclean.ui.redactor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.habitsclean.dagger.DaggerApp
import com.example.habitsclean.dagger.getApplicationComponent
import com.example.habitsclean.databinding.FragmentColorDialogBinding
import com.example.habitsclean.ui.list.ListViewModel
import javax.inject.Inject

class ColorDialogFragment : DialogFragment() {

    lateinit var  viewModel: RedactorViewModel
    private var binding: FragmentColorDialogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(requireParentFragment()).get(RedactorViewModel::class.java)
        binding = FragmentColorDialogBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.colorButtons?.touchables?.forEach {
            it.setOnClickListener { returnColorCode(it as Button) }
        }
    }

    private fun returnColorCode(colorButton: Button) {
        val background = colorButton.currentHintTextColor
        viewModel.color.value = background
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}