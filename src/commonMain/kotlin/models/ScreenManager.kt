package models

import com.soywiz.klock.TimeSpan
import com.soywiz.klock.seconds
import com.soywiz.klock.timesPerSecond
import com.soywiz.klogger.Console
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addFixedUpdater
import com.soywiz.korge.view.alpha
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
                Console.log("Shaking screen")
                sceneView.position(Random.nextDouble(-10.0, 10.0), Random.nextDouble(-5.0, 5.0))
                screenShakeTimer += levelManager.getCurrTime()
            } else {
                Console.log("Complete shake")
                screenShakeTimer = 0.seconds
                levelManager!!.triggerIsHit()
                sceneView.position(0, 0)
            }
        }
    }

}