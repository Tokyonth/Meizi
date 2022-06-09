package com.tokyonth.mz.ui.fragment.search

enum class SearchType(private val id: Int, private val msg: String) {

    CATEGORY(0, "分类"),
    TEAM(1, "机构"),
    MOTEL(2, "模特");

    fun getValue(): String {
        return msg
    }

    fun getKey(): Int {
        return id
    }

}
