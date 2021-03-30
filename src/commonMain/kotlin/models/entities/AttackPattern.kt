package models.entities

import com.soywiz.klock.TimeSpan
import com.soywiz.klogger.Console

class AttackPattern(private var attackDamage: Double,
                    private var timeUntilInitiate: TimeSpan,
                    private var totalCycles: Int,
                    private var timeBetweenCycles: TimeSpan) {

    private var currentCycles: Int

    init {
        currentCycles = totalCycles
    }

    fun decrementCycle() {
        currentCycles -= 1
        Console.log(currentCycles)
    }

    fun getTimeUntilInitiate(): TimeSpan {
        return timeUntilInitiate
    }

    fun getCurrentCycles(): Int {
        return currentCycles
    }

    fun getDamage(): Double {
        return attackDamage
    }

    fun getTotalCycles(): Int {
        return totalCycles
    }

    fun getTimeBetweenCycles(): TimeSpan {
        return timeBetweenCycles
    }
}