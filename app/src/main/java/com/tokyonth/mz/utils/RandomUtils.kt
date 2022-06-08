package com.tokyonth.mz.utils

import java.util.*

object RandomUtils {

    fun start(count: Int, max: Int): HashSet<Int> {
        val ran = Random()
        val hs = HashSet<Int>()
        while (true) {
            val tmp = ran.nextInt(max) + 1
            hs.add(tmp)
            if (hs.size == count) break
        }
        return hs
    }

}
