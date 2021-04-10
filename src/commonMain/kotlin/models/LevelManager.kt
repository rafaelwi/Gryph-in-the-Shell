package models

import com.soywiz.klock.TimeSpan
import com.soywiz.korge.scene.SceneContainer
import constants.LevelStatus

/** Manages the status of the level **/
class LevelManager(val sceneContainer: SceneContainer?) {
    private var isOngoing: Boolean
    private var battleStatus: LevelStatus
    private var levelTimer: LevelTimer
    private var isHit: Boolean
    private var isDefending: Boolean

    init {
        isOngoing = false
        isHit = false
        isDefending = false
        battleStatus = LevelStatus.NOT_STARTED
        levelTimer = LevelTimer()
    }

    fun start() {
        levelTimer.startTimer()
        isOngoing = true
        battleStatus = LevelStatus.ON
    }

    fun finish() {
        levelTimer.finishTimer()
        isOngoing = false
        battleStatus = LevelStatus.FINISHED
    }

    fun triggerIsHit() {
        isHit = !isHit
    }

    fun setIsDefending(isDefendingBool: Boolean) {
        isDefending = isDefendingBool
    }

    fun getIsOngoing(): Boolean {
        return isOngoing
    }

    fun getCurrTime(): TimeSpan {
        return levelTimer.getTimeElapsed()
    }

    fun getScore(): Double {
        return levelTimer.getTimeCompleted()
    }

    fun getIsHit(): Boolean {
        return isHit
    }

    fun getIsDefending(): Boolean {
        return isDefending
    }

    override fun toString(): String {
        return "LevelManager isOngoing: ${this.isOngoing}\n\tBattleStatus: ${this.battleStatus}\n"
    }
}
