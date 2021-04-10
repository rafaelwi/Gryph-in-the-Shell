package models

import com.soywiz.klock.PerformanceCounter
import com.soywiz.klock.TimeSpan
import com.soywiz.klock.milliseconds

/** Handles on-screen timer **/
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

    fun getTimeElapsed(): TimeSpan {
        return (PerformanceCounter.milliseconds - timerStart).milliseconds
    }

    fun finishTimer() {
        timerEnd = PerformanceCounter.milliseconds
    }

    /** Returns TimeSpan in double form as seconds **/
    fun getTimeCompleted(): Double {
        return (timerEnd - timerStart).milliseconds.seconds
    }
}
