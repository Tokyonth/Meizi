package com.tokyonth.mz.ui.fragment

import androidx.fragment.app.viewModels

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.adapter.MovieListAdapter
import com.tokyonth.mz.data.MovieEntity
import com.tokyonth.mz.databinding.FragmentMovieBinding
import com.tokyonth.mz.viewmodel.MovieViewModel

class MovieFragment : BaseListFragment<MovieEntity>() {

    private val binding: FragmentMovieBinding by lazyBind()

    private val model: MovieViewModel by viewModels()

    private val movieAdapter = MovieListAdapter()

    override fun setVbRoot() = binding

    override fun setAlbumModel() = model

    override fun setRefreshView() = binding.included.refreshList

    override fun setRecyclerView() = binding.included.rvList

    override fun setAdapter() = movieAdapter

}
