package models

import com.soywiz.klock.TimeSpan
import com.soywiz.klogger.Console
import com.soywiz.korge.input.*
import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.hitTest
import com.soywiz.korge.view.*
import com.soywiz.korma.geom.Point
import models.entities.Enemy
import models.components.SwipeComponent
import models.entities.AttackMovesetPlayer
import models.entities.Player

class LevelMechanics(private var levelData: LevelData,
                     private var levelManager: LevelManager?,
                     private var enemySprite: Sprite,
                     private var currentEnemy: Enemy,
                     private var currentPlayer: Player?) {

    private lateinit var enemyMovesetPlayer: AttackMovesetPlayer

    suspend fun init() {
        initSwipeMechanics()
        initAttackMoveset()
        initHoldMechanic()
    }

    private fun initAttackMoveset() {
        enemyMovesetPlayer = AttackMovesetPlayer(currentEnemy.getAttackMoveset(), levelManager, currentPlayer)
    }

    private fun initHoldMechanic() {
        levelData.touch {
            scaleRecognizer {
                enemyMovesetPlayer.nullify()
            }
        }

        levelData.addUpdater {
            onUpAnywhere {
                enemyMovesetPlayer.reactivate()
            }
        }
    }

    private suspend fun initSwipeMechanics() {
        var swipeHit = false
        var inDrag = false
        var dragPoint: Point? = null
        var swipeGraphic = SwipeComponent(levelManager, levelData.mouse)
        var totalDamage = 0.0

        swipeGraphic.init()
        levelData.addChild(swipeGraphic)

        levelData.mouse {
            onDown {
                swipeGraphic.setSwipe(true)
                swipeHit = false
                dragPoint = it.currentPosStage
                if (enemySprite.hitTest(dragPoint!!) != null && levelManager?.getIsOngoing() == true) currentEnemy.reduceHealth(1.0)
            }
            onMoveAnywhere {
                if (dragPoint != null) {
                    inDrag = true
                    if (enemySprite.hitTest(dragPoint!!) != null) swipeHit = true
                }
            }
            onUpAnywhere {
                if (inDrag && swipeHit && levelManager?.getIsOngoing() == true) {
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

    fun initiateAttack(dt: TimeSpan?) {
        enemyMovesetPlayer.playMoveset()
    }

}