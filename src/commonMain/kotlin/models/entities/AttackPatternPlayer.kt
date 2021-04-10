package models.entities

import com.soywiz.klock.TimeSpan
import com.soywiz.klock.milliseconds
import com.soywiz.klogger.Console
import models.LevelManager

/** Handles playing the attack pattern **/
class AttackPatternPlayer(private var attackPattern: AttackPattern,
                          private var levelManager: LevelManager?,
                          private var currentPlayer: Player?) {
    private var currentCycles: Int = attackPattern.getTotalCycles()
    private var nextCycleTimestamp: TimeSpan

    init {
        nextCycleTimestamp = 0.milliseconds
    }

    fun complete(): Boolean {
        return currentCycles == 0
    }

    private fun getCurrentCycles(): Int {
        return currentCycles
    }

    private fun getNextAttackTimestamp(currTime: TimeSpan): TimeSpan {
        return currTime + attackPattern.getTimeBetweenCycles()
    }

    fun decrementCycle() {
        currentCycles -= 1
    }

    /** Select pattern **/
    private fun applyAttack() {
        levelManager!!.triggerIsHit()
        currentPlayer?.reduceHealth(attackPattern.getDamage())
        Console.log("Attack ${currentCycles} of ${attackPattern.getDamage()}")
        this.decrementCycle()
    }

    /** Use pattern **/
    fun playPattern(currTime: TimeSpan, patternTimestamp: TimeSpan) {
        if (currTime >= patternTimestamp) {
            if (this.getCurrentCycles() > 0 && currTime >= nextCycleTimestamp) {
                this.applyAttack()
                nextCycleTimestamp = this.getNextAttackTimestamp(currTime)
            }
        }
    }
}
