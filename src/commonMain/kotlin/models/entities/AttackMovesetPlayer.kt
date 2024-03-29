package models.entities

import com.soywiz.klock.TimeSpan
import com.soywiz.klock.milliseconds
import models.LevelManager
import kotlin.random.Random

/** The moveset of the player (attacking + blocking) **/
class AttackMovesetPlayer(private var attackMoveset: AttackMoveset,
                          private var levelManager: LevelManager?,
                          private var currentPlayer: Player?) {
    private var index: Int
    private var playing: Boolean
    private lateinit var randomAttackPattern: AttackPattern
    private lateinit var randomAttackPatternPlayer: AttackPatternPlayer
    private var lastPatternTimestamp: TimeSpan
    private var initPatternDamage: Double

    init {
        playing = false
        lastPatternTimestamp = 0.milliseconds
        index = initRandIndex()
        initPattern(index)
        initPatternDamage = randomAttackPattern.getDamage()
        initPatternPlayer()
    }

    /** Block damage **/
    fun nullify() {
        randomAttackPattern.setDamage(0.0)
    }

    /** Set a random attack pattern **/
    fun reactivate() {
        randomAttackPattern.setDamage(initPatternDamage)
    }

    /** Pick a random index for pattern **/
    private fun initRandIndex(): Int {
        return Random.nextInt(0, attackMoveset.getNumberOfPatterns())
    }

    /** Gets the pattern to use **/
    private fun initPattern(index: Int) {
        randomAttackPattern = attackMoveset.getAttackPatterns()[index]
        initPatternDamage = randomAttackPattern.getDamage()
    }

    /** Sets the player's attack pattern **/
    private fun initPatternPlayer() {
        randomAttackPatternPlayer = AttackPatternPlayer(randomAttackPattern, levelManager, currentPlayer)
    }

    /** Actualize the attack pattern selected **/
    fun playMoveset() {
        val currTime = levelManager?.getCurrTime()

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
            this.reactivate()
            initPattern(index)
            initPatternPlayer()
            playing = true
            if (levelManager!!.getIsDefending()) this.nullify()
        }
    }
}
