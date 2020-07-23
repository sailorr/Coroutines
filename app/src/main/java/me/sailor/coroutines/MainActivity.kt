package me.sailor.coroutines

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import me.sailor.coroutines.Util.getNow
import java.lang.Runnable

class MainActivity : AppCompatActivity() {


    companion object{
        val START:String = "START"
        val RUN:String = "RUNING"
        val END:String = "END"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NewThread.setOnClickListener {
            Thread(Runnable {
                Util.log(START)
                Thread.sleep(3000)
                Util.log(END)
            }).start()
        }
        RunBlocking.setOnClickListener {
            Util.log(START)
            runBlocking {
                launch {
                    repeat(3) {
                        delay(3000)
                        Util.log(RUN)
                    }
                }
            }
            Util.log(END)
        }

        LaunchJob.setOnClickListener {
            Util.log(START)
            GlobalScope.launch {
                delay(3000)
                Util.log(RUN)
            }
            Util.log(END)
        }

        Suspend.setOnClickListener {
            Util.log(START)
            GlobalScope.launch {
                val sum1 = job1()
                val sum2  =job2()
                val sum = sum1+sum2
                Log.d(
                    "MainActivity",
                    "Runing---> Time: ${getNow()}, 线程:${Thread.currentThread().name},Result: $sum"
                )
            }
            Util.log(END)
        }


        Async.setOnClickListener {
            Util.log(START)
            GlobalScope.launch {
                val sum1 = GlobalScope.async { job1() }
                val sum2 = GlobalScope.async { job2() }
                val sum = sum1.await()+sum2.await()
                Log.d(
                    "MainActivity",
                    "Runing---> Time: ${getNow()}, 线程:${Thread.currentThread().name},Result: $sum"
                )
            }
            Util.log(END)
        }


    }

    private suspend fun job1():Int{
        delay(2000)
        return 1+1
    }
    private suspend fun job2():Int{
        delay(3000)
        return 2+2
    }




}
