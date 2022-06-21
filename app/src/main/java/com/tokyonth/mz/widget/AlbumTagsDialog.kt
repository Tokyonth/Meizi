package com.tokyonth.mz.widget

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip

import com.tokyonth.mz.R
import com.tokyonth.mz.data.DetailAlbumEntity
import com.tokyonth.mz.databinding.LayoutDialogTagsBinding
import com.tokyonth.mz.utils.ktx.dp2px
import st235.com.github.flow_layout.FlowLayoutParams

class AlbumTagsDialog(context: Context) :
    BottomSheetDialog(context, R.style.BottomSheetDialog) {

    private lateinit var binding: LayoutDialogTagsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutDialogTagsBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
    }

    private fun createChip(string: String): Chip {
        val params = FlowLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(
                4.dp2px().toInt(), 0, 4.dp2px().toInt(), 0
            )
        }
        return Chip(context).apply {
            text = string
            layoutParams = params
        }
    }

    fun setData(
        data: List<DetailAlbumEntity.TagsItem>,
        itemClick: (DetailAlbumEntity.TagsItem) -> Unit
    ) {
        this.show()
        data.forEach { tags ->
            val chip = createChip(tags.name).apply {
                setOnClickListener {
                    itemClick.invoke(tags)
                }
            }
            binding.flowTags.addView(chip)
        }
    }

}
