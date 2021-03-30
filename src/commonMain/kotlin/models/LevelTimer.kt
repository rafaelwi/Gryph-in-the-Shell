package models

import com.soywiz.klock.PerformanceCounter
import com.soywiz.klock.milliseconds

class LevelTimer {

    private var timerStart: Double
    private var timerEnd: Double

    init {
        timerStart = 0.0
        timerEnd = 0.0
    }

    fun startTimer() {
        timerStart = PerformanceCounter.milliseconds
    }

    fun finishTimer() {
        timerEnd = PerformanceCounter.milliseconds
    }

    //Returns TimeSpan in double form as seconds
    fun getTimeElapsed(): Double {
        return (timerEnd - timerStart).milliseconds.seconds
    }

}