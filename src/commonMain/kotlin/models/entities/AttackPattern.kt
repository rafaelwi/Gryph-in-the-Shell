package models.entities

import com.soywiz.klock.TimeSpan

class AttackPattern(private var attackDamage: Double,
                    private var timeUntilInitiate: TimeSpan,
                    private var totalCycles: Int,
                    private var timeBetweenCycles: TimeSpan) {

    fun getTimeUntilInitiate(): TimeSpan {
        return timeUntilInitiate
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