package models

import com.soywiz.klock.TimeSpan
import com.soywiz.klock.milliseconds
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
                     private var enemySprite: Sprite,
                     private var currentEnemy: Enemy,
                     private var currentPlayer: Player?) {

    private var attackCountdown = currentEnemy.getAttackPattern().getTimeUntilInitiate()
    private var cycleCountdown = 0.milliseconds

    suspend fun init() {
        initSwipeMechanics()
        initAttackPattern()
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

    suspend fun checkInitiateAttack(dt: TimeSpan?) {


        /**
         *         if (attackCountdown > 0.milliseconds) {
        attackCountdown.minus(1.milliseconds)
        Console.log(attackCountdown)
        }
        else {
        Console.log("initiating attack")
        if (currentEnemy.getAttackPattern().getCurrentCycles() > 0 && cycleCountdown <= 0.milliseconds) {
        Console.log("attacking")
        currentPlayer?.reduceHealth(currentEnemy.getAttackPattern().getDamage())
        currentEnemy.getAttackPattern().decrementCycle()
        cycleCountdown = currentEnemy.getAttackPattern().getTimeBetweenCycles()
        } else {
        Console.log("countingdown until next cycle")
        cycleCountdown.minus(1.milliseconds)
        }
        }
         */
        /**
         *
        CoroutineScope(coroutineContext).launch {
        delay(currentEnemy.getAttackPattern().getTimeUntilInitiate())
        while (currentEnemy.getAttackPattern().getCurrentCycles() > 0) {
        currentPlayer?.reduceHealth(currentEnemy.getAttackPattern().getDamage())
        currentEnemy.getAttackPattern().decrementCycle()
        delay(currentEnemy.getAttackPattern().getTimeBetweenCycles())
        }
        }
         */
    }

}