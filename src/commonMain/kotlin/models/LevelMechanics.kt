package models

import com.soywiz.klock.TimeSpan
import com.soywiz.klock.milliseconds
import com.soywiz.klock.seconds
import com.soywiz.klogger.Console
import com.soywiz.korge.input.mouse
import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.hitTest
import com.soywiz.korio.async.delay
import com.soywiz.korio.async.launch
import com.soywiz.korma.geom.Point
import kotlinx.coroutines.CoroutineScope
import models.entities.Enemy
import models.components.SwipeComponent
import models.entities.Player
import kotlin.coroutines.coroutineContext

class LevelMechanics(private var levelData: LevelData,
                     private var levelManager: LevelManager?,
                     private var enemySprite: Sprite,
                     private var currentEnemy: Enemy,
                     private var currentPlayer: Player?) {

    private var nextCycleTimestamp = 0.milliseconds

    suspend fun init() {
        initSwipeMechanics()
    }

    private suspend fun initAttackPattern() {

    }

    private suspend fun initSwipeMechanics() {
        var swipeHit = false
        var inDrag = false
        var dragPoint: Point? = null
        var swipeGraphic = SwipeComponent(levelData.mouse)
        var totalDamage = 0.0

        swipeGraphic.init()
        levelData.addChild(swipeGraphic)

        levelData.mouse {
            onDown {
                swipeGraphic.setSwipe(true)
                swipeHit = false
                dragPoint = it.currentPosStage
                if (enemySprite.hitTest(dragPoint!!) != null) currentEnemy.reduceHealth(1.0)
            }
            onMoveAnywhere {
                if (dragPoint != null) {
                    inDrag = true
                    if (enemySprite.hitTest(dragPoint!!) != null) swipeHit = true
                }
            }
            onUpAnywhere {
                if (inDrag && swipeHit) {
                    //Drag distance / Screen size -> Multiplier
                    //totalDamage = dragPoint?.distanceTo(it.downPosStage) ?: 0.0
                    //Console.log(totalDamage)
                    //Console.log(swipeHit)
                    //Reduce enemy health by scaled damage
                    currentEnemy.reduceHealth(10.0)
                }
                totalDamage = 0.0
                dragPoint = null
                swipeHit = false
                inDrag = false
                swipeGraphic.setSwipe(false)
                swipeGraphic.resetSwipe()
            }
        }
    }

    fun checkInitiateAttack(dt: TimeSpan?) {
        var currTime = levelManager?.getCurrTime()

        if (currTime != null && levelManager?.getIsOngoing() == true) {
            if (currTime >= currentEnemy.getAttackPattern().getTimeUntilInitiate()) {
                if (currentEnemy.getAttackPattern().getCurrentCycles() >= 0 && currTime >= nextCycleTimestamp) {
                    Console.log("attacking on cycle ", currentEnemy.getAttackPattern().getCurrentCycles())
                    currentPlayer?.reduceHealth(currentEnemy.getAttackPattern().getDamage())
                    currentEnemy.getAttackPattern().decrementCycle()
                    nextCycleTimestamp = currTime + currentEnemy.getAttackPattern().getTimeBetweenCycles()
                }
            }
        }
    }

}