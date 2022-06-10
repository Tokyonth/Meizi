package com.tokyonth.mz.ui.fragment.search

import com.tokyonth.mz.R
import com.tokyonth.mz.utils.ktx.string

enum class SearchType(private val id: Int, private val msg: String) {

    CATEGORY(0, string(R.string.label_category)),
    TEAM(1, string(R.string.label_team)),
    MOTEL(2, string(R.string.label_motel));

    fun getValue(): String {
        return msg
    }

    fun getKey(): Int {
        return id
    }

}
