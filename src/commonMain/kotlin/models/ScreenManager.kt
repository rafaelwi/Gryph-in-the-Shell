package models

import com.soywiz.klock.seconds
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import kotlin.random.Random

class ScreenManager(private val sceneView: Container, private val levelManager: LevelManager?) {

    private var screenShakeTimer = 0.seconds
    private var screenShakeDuration = 2.seconds

    init {
        screenShakeTimer = 0.seconds
        screenShakeDuration = .6.seconds
    }

    fun check() {
        checkScreenStatus()
        checkShakeScreen()
    }

    private fun checkScreenStatus() {

    }

    private fun checkShakeScreen() {
        if (levelManager!!.getIsHit()) {
            if (screenShakeTimer <= screenShakeDuration) {
                sceneView.position(Random.nextDouble(-10.0, 10.0), Random.nextDouble(-5.0, 5.0))
                screenShakeTimer += levelManager.getCurrTime()
            } else {
                screenShakeTimer = 0.seconds
                levelManager!!.triggerIsHit()
                sceneView.position(0, 0)
            }
        }
    }

}