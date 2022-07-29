package com.example.stopwatch

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

class StopWatchService : Service() {
    private val timer= Timer()
    override fun onBind(p0: Intent?): IBinder? = null


    //Let’s add 0.0 as the default value.Then,timer.scheduleAtFixedRate
    //pass the time to inner class’s constructor. Delay is 0. And the period is 1 second,
    //we need to provide it in milliseconds. So, 1000 milliseconds.
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val time=intent.getDoubleExtra(CURRENT_TIME,0.0)
        timer.scheduleAtFixedRate(StopWatchTimerTask(time),0,1000)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    companion object {
        const val CURRENT_TIME="current_time"
        const val UPDATED_TIME="updated_time"
    }

    private inner class StopWatchTimerTask(private var time:Double):TimerTask(){
        override fun run() {
            val intent=Intent(UPDATED_TIME)
            time++
            intent.putExtra(CURRENT_TIME,time)
            sendBroadcast(intent)
        }

    }
}