package com.tokyonth.mz.utils

import java.util.*
import kotlin.collections.HashSet

object RandomUtils {

    fun start(count: Int, max: Int, min: Int): IntArray {
        val set = HashSet<Int>()
        val random = Random()
        do {
            val r = random.nextInt(max) + 1 - min
            set.add(r)
        } while (set.size < count)
        return set.toIntArray()
    }

}
