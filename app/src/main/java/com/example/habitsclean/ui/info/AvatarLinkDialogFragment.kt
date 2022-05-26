package com.example.habitsclean.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.habitsclean.R
import com.example.habitsclean.databinding.AvatarLinkDialogFragmentBinding
import com.example.habitsclean.main_activity.MainActivity
import com.google.android.material.navigation.NavigationView

class AvatarLinkDialogFragment: DialogFragment() {

    private var binding: AvatarLinkDialogFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = AvatarLinkDialogFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navView: NavigationView? = activity?.findViewById(R.id.nav_view)
        val userImage = navView!!.getHeaderView(0).findViewById<ImageView>(R.id.avatar_image)
        userImage.setBackgroundColor(0)

        binding?.downloadLinkButton?.setOnClickListener {
            val str = binding?.editLink?.text.toString()
            Glide
                .with(context as MainActivity)
                .load(str)
                .circleCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .dontAnimate()
                .into(userImage)
            dismiss()
        }
    }
}