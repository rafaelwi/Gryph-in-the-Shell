package models.components

import com.soywiz.klock.timesPerSecond
import com.soywiz.korge.input.MouseEvents
import com.soywiz.korge.particle.*
import com.soywiz.korge.view.*
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Point
import models.LevelManager

/**
 * Note: Possible that there's some inconsistencies in swipes after thorough testing. Will investigate further later.
 */
class SwipeComponent(private val levelManager: LevelManager?, private val mouse: MouseEvents): Container() {

    private lateinit var particleEmitterView: ParticleEmitterView
    private lateinit var particleEmitter: ParticleEmitter
    private lateinit var particleEmitterPos: Point
    var inSwipe = false


    suspend fun init() {
        particleEmitterPos = Point(-100.0, -100.0)
        particleEmitter = resourcesVfs["particles/fire.pex"].readParticleEmitter()
        particleEmitterView = particleEmitter(particleEmitter, particleEmitterPos)
        addFixedUpdater(60.timesPerSecond) {
            if (inSwipe && levelManager?.getIsOngoing() == true) updateSwipe(mouse.currentPosStage)
        }
    }

    fun setSwipe(isDrag: Boolean) {
        inSwipe = isDrag;
    }

    fun updateSwipe(currentPos: Point) {
        particleEmitterPos.setTo(currentPos.x, currentPos.y)
    }

    fun resetSwipe() {
        particleEmitterPos.setTo(-100.0, -100.0)
    }

}