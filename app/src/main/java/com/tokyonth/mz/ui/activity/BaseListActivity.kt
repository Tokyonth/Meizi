package com.tokyonth.mz.ui.activity

import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout

import com.tokyonth.mz.adapter.BaseListAdapter
import com.tokyonth.mz.base.BaseActivity
import com.tokyonth.mz.data.AlbumPictureEntity
import com.tokyonth.mz.utils.ktx.toast
import com.tokyonth.mz.viewmodel.BaseListViewModel

abstract class BaseListActivity<T> : BaseActivity() {

    abstract fun setAlbumModel(): BaseListViewModel<T>

    abstract fun setRefreshView(): SmartRefreshLayout

    abstract fun setRecyclerView(): RecyclerView

    abstract fun setAdapter(): BaseListAdapter<T, *>

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
    }

    override fun initObserve() {
        super.initObserve()
        setAlbumModel().successLiveData.observe(this) {
            setAdapter().addData(it)
        }
        setAlbumModel().refreshLiveData.observe(this) {
            if (it) {
                setAdapter().clearData()
            }
            setRefreshView().finishRefresh(it)
        }
        setAlbumModel().loadMoreLiveData.observe(this) {
            setRefreshView().finishLoadMore(it)
        }
        setAlbumModel().errorLiveData.observe(this) {
            if (setAdapter().itemCount == 0) {
                setAdapter().setErrorView(this, it)
            } else {
                toast(it)
            }
        }
    }

}
