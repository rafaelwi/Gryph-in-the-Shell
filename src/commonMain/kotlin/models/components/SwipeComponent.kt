package models.components

import com.soywiz.klock.timesPerSecond
import com.soywiz.korge.input.MouseEvents
import com.soywiz.korge.particle.*
import com.soywiz.korge.view.*
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Point
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class SwipeComponent(private val mouse: MouseEvents): Container() {

    private lateinit var particleEmitterView: ParticleEmitterView
    private lateinit var particleEmitter: ParticleEmitter
    private lateinit var particleEmitterPos: Point
    var inSwipe = false

    suspend fun init() {
        particleEmitterPos = Point(-20.0, -20.0)
        particleEmitter = resourcesVfs["particles/fire.pex"].readParticleEmitter()
        particleEmitterView = particleEmitter(particleEmitter, particleEmitterPos)
        addFixedUpdater(60.timesPerSecond) {
            if (inSwipe) updateSwipe(mouse.currentPosStage)
        }
    }

    fun setSwipe(isDrag: Boolean) {
        inSwipe = isDrag;
    }

    fun updateSwipe(currentPos: Point) {
        particleEmitterPos.setTo(currentPos.x, currentPos.y)
    }

    fun resetSwipe() {
        particleEmitterPos.setTo(-20.0, -20.0)
    }

}