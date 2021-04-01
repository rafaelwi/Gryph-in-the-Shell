package models.entities

import com.soywiz.klock.Time
import com.soywiz.klock.TimeSpan
import com.soywiz.klock.milliseconds
import com.soywiz.klogger.Console
import models.LevelManager

class AttackPatternPlayer(private var attackPattern: AttackPattern,
                          private var levelManager: LevelManager?,
                          private var currentPlayer: Player?) {

    private var currentCycles: Int
    private var nextCycleTimestamp: TimeSpan

    init {
        currentCycles = attackPattern.getTotalCycles()
        nextCycleTimestamp = 0.milliseconds
    }

    fun complete(): Boolean {
        return currentCycles == 0
    }

    fun getCurrentCycles(): Int {
        return currentCycles
    }

    fun getNextAttackTimestamp(currTime: TimeSpan): TimeSpan {
        return currTime + attackPattern.getTimeBetweenCycles()
    }

    fun decrementCycle() {
        currentCycles -= 1
        Console.log(currentCycles)
    }

    fun applyAttack() {
        levelManager!!.triggerIsHit()
        currentPlayer?.reduceHealth(attackPattern.getDamage())
        this.decrementCycle()
    }

    fun playPattern(currTime: TimeSpan, patternTimestamp: TimeSpan) {
        if (currTime >= patternTimestamp) {
            if (this.getCurrentCycles() > 0 && currTime >= nextCycleTimestamp) {
                this.applyAttack()
                nextCycleTimestamp = this.getNextAttackTimestamp(currTime)
            }
        }
    }
}