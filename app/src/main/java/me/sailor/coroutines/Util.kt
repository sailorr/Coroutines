package me.sailor.coroutines

import android.util.Log
import java.util.*

/**
 *  -description:
 *  -author: created by tang on 2020/7/23 9:44
 */
object Util {


     fun getNow(): String {
        val tms = Calendar.getInstance()
        return tms.get(Calendar.HOUR_OF_DAY).toString() + ":" + tms.get(Calendar.MINUTE).toString() + ":" + tms.get(
            Calendar.SECOND
        ).toString() + "." + tms.get(Calendar.MILLISECOND).toString()
    }

     fun log(step:String?) {
        Log.d(
            "MainActivity",
            "$step---> Time: ${getNow()}, 线程:${Thread.currentThread().name}"
        )
    }
}