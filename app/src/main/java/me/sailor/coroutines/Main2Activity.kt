package me.sailor.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.*
import me.sailor.coroutines.MainActivity.Companion.END
import me.sailor.coroutines.MainActivity.Companion.RUN
import me.sailor.coroutines.MainActivity.Companion.START

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        GlobalScope.launch {
            //耗时操作
            val async = async {
                delay(3000)
                return@async "666"
            }
            //result 就是异步回调的值
            var result = async.await()
        }


        AsyncMerge.setOnClickListener {
            Util.log(START)
            /**
             * 场景一
             * 需要合并两个异步运算后的结果。
             * 如组合提取多个网络请求的返回值。
             */
            GlobalScope.launch {
                val async1 = async {
                    delay(3000)
                    return@async 666
                }
                val async2 = async {
                    delay(2000)
                    return@async "Async Return:"
                }
                val result = async2.await() + async1.await()
                //await()，就是表示异步回调到主线程了，直接使用该值进行UI更新
                TextView.text = result
                Log.d("MainActivity", "RUN----> Time:${Util.getNow()}, Result:$result")
            }
            Util.log(END)
        }


        AsyncTask.setOnClickListener {
            Util.log(START)
            /**
             * 场景二
             * 根据第一个异步操作的值，来进行接下来操作。
             * 如 注册成功后直接进行登录。
             */
            GlobalScope.launch {
                val result = async {
                    val async1 = job1()
                    if (async1 == "Async1-") {
                        val async2 = job2()
                        return@async async1 + async2
                    }
                    return@async async1
                }
                //await()，就是表示异步的回调，直接使用该值进行UI更新
                TextView.text = result.await()
                Log.d("MainActivity", "RUN----> Time:${Util.getNow()}, Result:${result.await()}")
            }
            Util.log(END)
        }

        var task: Deferred<Unit>? = null
        var heartBeat = 0
        AsyncCancel.setOnClickListener {
            Util.log(START)
            /**
             * 场景三
             * 主動断开请求
             * 如 断开某个心跳线程。
             */

            GlobalScope.launch {
                task = async {
                    while (true) {
                        heartBeat++
                        Log.d("MainActivity", "RUN----> Time:${Util.getNow()},发送心跳包。")
                        delay(1000)
                    }
                }
            }
            Util.log(END)
        }

        Cancel.setOnClickListener {
            task?.cancel()
            Log.d("Test", "MainActivity: 取消心跳包发送 一直进行$heartBeat 次")
        }

    }

    private suspend fun job1(): String {
        delay(2000)
        return "Async1-"
    }

    private suspend fun job2(): String {
        delay(3000)
        return "Async2"
    }
}
