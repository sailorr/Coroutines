package me.sailor.coroutines;

/**
 * -description:
 * -author: created by tang on 2020/7/23 9:38
 */
public class MyThread implements Runnable {

     interface Callback{
        <T> void backResult(T t);
    }

    private  Callback callback;


    public MyThread(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        //耗时操作
//        callback(...);
    }
}
