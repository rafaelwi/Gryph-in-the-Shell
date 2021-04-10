package models

import com.soywiz.klock.TimeSpan
import com.soywiz.korge.input.mouse
import com.soywiz.korge.input.onUpAnywhere
import com.soywiz.korge.input.scaleRecognizer
import com.soywiz.korge.input.touch
import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.hitTest
import com.soywiz.korma.geom.Point
import models.components.SwipeComponent
import models.entities.AttackMovesetPlayer
import models.entities.Enemy
import models.entities.Player

/** Holds technical objects related to level (player object, etc.) **/
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

    /** Block mechanics **/
    private fun initHoldMechanic() {
        levelData.touch {
            scaleRecognizer {
                if (levelManager?.getIsOngoing() == true) {
                    enemyMovesetPlayer.nullify()
                    levelManager!!.setIsDefending(true)
                }
            }
        }

        levelData.addUpdater {
            onUpAnywhere {
                if (levelManager?.getIsOngoing() == true) {
                    enemyMovesetPlayer.reactivate()
                    levelManager!!.setIsDefending(false)
                }
            }
        }
    }

    /** Attack mechanics **/
    private suspend fun initSwipeMechanics() {
        var swipeHit = false
        var inDrag = false
        var dragPoint: Point? = null
        val swipeGraphic = SwipeComponent(levelManager, levelData.mouse)

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
                    //Reduce enemy health by scaled damage
                    currentEnemy.reduceHealth(10.0)
                }
                dragPoint = null
                swipeHit = false
                inDrag = false
                swipeGraphic.setSwipe(false)
                swipeGraphic.resetSwipe()
            }
        }
    }

    fun initiateAttack() {
        enemyMovesetPlayer.playMoveset()
    }
}
