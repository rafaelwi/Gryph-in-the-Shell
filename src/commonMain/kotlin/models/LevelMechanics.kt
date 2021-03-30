package models

import com.soywiz.korge.input.mouse
import com.soywiz.korge.particle.ParticleEmitter
import com.soywiz.korge.particle.readParticleEmitter
import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.hitTest
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Point
import models.entities.Enemy
import models.components.SwipeComponent

class LevelMechanics(private var levelData: LevelData,
                     private var enemySprite: Sprite,
                     private var currentEnemy: Enemy) {

    suspend fun init() {
        initSwipeMechanics()
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

}