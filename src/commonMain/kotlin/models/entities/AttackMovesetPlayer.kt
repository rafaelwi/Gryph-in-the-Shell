package models.entities

import com.soywiz.klock.TimeSpan
import com.soywiz.klock.milliseconds
import com.soywiz.klogger.Console
import models.LevelManager
import kotlin.random.Random

class AttackMovesetPlayer(private var attackMoveset: AttackMoveset,
                          private var levelManager: LevelManager?,
                          private var currentPlayer: Player?) {

    private var index: Int
    private var playing: Boolean
    private lateinit var randomAttackPattern: AttackPattern
    private lateinit var randomAttackPatternPlayer: AttackPatternPlayer
    private var lastPatternTimestamp: TimeSpan

    init {
        playing = false
        lastPatternTimestamp = 0.milliseconds
        index = initRandIndex()
        initPattern(index)
        initPatternPlayer(index)
    }

    fun initRandIndex(): Int {
        return Random.nextInt(0, attackMoveset.getNumberOfPatterns())
    }

    fun initPattern(index: Int) {
        randomAttackPattern = attackMoveset.getAttackPatterns()[index]
    }

    fun initPatternPlayer(index: Int) {
        randomAttackPatternPlayer = AttackPatternPlayer(randomAttackPattern, levelManager, currentPlayer)
    }

    fun playMoveset() {
        var currTime = levelManager?.getCurrTime()
        //While game still ongoing, rotate and play
        if (currTime != null && levelManager?.getIsOngoing() == true && playing) {
            //Get random pattern
            randomAttackPatternPlayer.playPattern(currTime, lastPatternTimestamp + randomAttackPattern.getTimeUntilInitiate())
            if (randomAttackPatternPlayer.complete()) {
                playing = false
                lastPatternTimestamp = currTime
            }
        } else {
            index = initRandIndex()
            initPattern(index)
            initPatternPlayer(index)
            playing = true
        }
    }

}