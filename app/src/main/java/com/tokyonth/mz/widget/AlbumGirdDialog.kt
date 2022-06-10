package com.tokyonth.mz.widget

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog

import com.tokyonth.mz.R
import com.tokyonth.mz.adapter.GridAlbumAdapter
import com.tokyonth.mz.databinding.LayoutDialogGridBinding

class AlbumGirdDialog(context: Context) :
    BottomSheetDialog(context, R.style.BottomSheetDialog) {

    private lateinit var binding: LayoutDialogGridBinding

    private var itemClick: ((Int) -> Unit)? = null

    private val gridAlbumAdapter = GridAlbumAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutDialogGridBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.rvAlbumGrid.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = gridAlbumAdapter
        }
        gridAlbumAdapter.setItemClick {
            itemClick?.invoke(it)
            dismiss()
        }
    }

    fun setData(data: MutableList<String>, itemClick: (Int) -> Unit): AlbumGirdDialog {
        this.itemClick = itemClick
        gridAlbumAdapter.addData(data)
        return this
    }

}
