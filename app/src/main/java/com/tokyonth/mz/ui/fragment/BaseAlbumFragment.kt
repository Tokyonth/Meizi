package com.tokyonth.mz.ui.fragment

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tokyonth.mz.Constants
import com.tokyonth.mz.adapter.BaseAlbumAdapter
import com.tokyonth.mz.base.BaseFragment
import com.tokyonth.mz.data.AlbumPictureEntity
import com.tokyonth.mz.ui.activity.DetailActivity
import com.tokyonth.mz.utils.ktx.toast
import com.tokyonth.mz.viewmodel.BaseListViewModel

abstract class BaseAlbumFragment : BaseFragment() {

    abstract fun setAlbumModel(): BaseListViewModel<AlbumPictureEntity>

    abstract fun setRefreshView(): SmartRefreshLayout

    abstract fun setRecyclerView(): RecyclerView

    abstract fun setAdapter(): BaseAlbumAdapter<*>

    override fun initData() {

    }

    override fun initView() {
        setRefreshView().run {
            autoRefresh()
            setOnRefreshListener {
                setAlbumModel().refreshPage()
            }
            setOnLoadMoreListener {
                setAlbumModel().nextPage()
            }
        }
        setRecyclerView().apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = setAdapter()
        }
        setAdapter().setItemClick {
            Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(Constants.INTENT_KEY_ALBUM_ID, it.id)
            }.let {
                startActivity(it)
            }
        }
    }

    override fun initObserve() {
        super.initObserve()
        setAlbumModel().successLiveData.observe(viewLifecycleOwner) {
            setAdapter().addData(it)
        }
        setAlbumModel().refreshLiveData.observe(viewLifecycleOwner) {
            if (it) {
                setAdapter().clearData()
            }
            setRefreshView().finishRefresh(it)
        }
        setAlbumModel().loadMoreLiveData.observe(viewLifecycleOwner) {
            setRefreshView().finishLoadMore(it)
        }
        setAlbumModel().errorLiveData.observe(viewLifecycleOwner) {
            if (setAdapter().itemCount == 0) {
                setAdapter().setErrorView(requireContext(), it)
            } else {
                toast(it)
            }
        }
    }

}
