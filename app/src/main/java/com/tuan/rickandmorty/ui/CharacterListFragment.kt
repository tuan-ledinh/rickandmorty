package com.tuan.rickandmorty.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.character_list.*
import com.tuan.rickandmorty.R
import com.tuan.rickandmorty.adapter.CharacterListAdapter
import com.tuan.rickandmorty.viewmodel.CharacterListViewModel

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private val model: CharacterListViewModel by viewModels()
    private val nav by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.character_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setupWithNavController(nav, AppBarConfiguration(nav.graph))

        val recyclerAdapter = CharacterListAdapter()
        val recyclerLayoutManager = GridLayoutManager(character_list.context, 2)
        recyclerLayoutManager.spanSizeLookup = recyclerAdapter.getSpanSizeLookup()

        character_list.apply {
            adapter = recyclerAdapter
            layoutManager = recyclerLayoutManager

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager

                    val visibleCount = recyclerView.layoutManager!!.childCount
                    val totalCount = recyclerView.layoutManager!!.itemCount
                    val firstVisible = layoutManager.findFirstVisibleItemPosition()

                    if (firstVisible + visibleCount >= totalCount) {
                        model.loadNextPage()
                    }
                }
            })
        }

        recyclerAdapter.setOnItemClickListener {
            nav.navigate(
                CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                    it
                )
            )
        }

        model.characters.observe(viewLifecycleOwner) {
            recyclerAdapter.setDataset(it)
        }

        model.loading.observe(viewLifecycleOwner) {
            recyclerAdapter.setLoading(it)
        }

        model.error.observe(viewLifecycleOwner) {
            recyclerAdapter.setError(it)
        }
    }

    override fun onStart() {
        super.onStart()

        model.initialize()
    }
}