package com.tuan.rickandmorty.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.character_detail.*
import com.tuan.rickandmorty.R
import com.tuan.rickandmorty.di.GlideApp
import com.tuan.rickandmorty.viewmodel.CharacterDetailViewModel


@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private val model: CharacterDetailViewModel by viewModels()
    private val args: CharacterDetailFragmentArgs by navArgs()
    private val nav by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.character_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setupWithNavController(nav, AppBarConfiguration(nav.graph))

        model.character.observe(viewLifecycleOwner) {

            GlideApp.with(requireContext())
                .load(it.image)
                .centerCrop()
                .circleCrop()
                .transition(withCrossFade())
                .into(character_image)

            character_name.text = it.name
            character_species.text = if (it.type.isNotEmpty()) it.type else it.species
            character_gender.setText(it.gender.translation)
            character_origin.text = it.origin.name
            character_location.text = it.location.name
            character_status_badge.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), it.status.color))
            character_status.setText(it.status.translation)
        }
    }

    override fun onStart() {
        super.onStart()

        model.initialize(args.character)
    }
}