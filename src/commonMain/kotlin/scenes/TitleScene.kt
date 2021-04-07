package scenes

import MainModule
import com.soywiz.klock.seconds
import com.soywiz.korge.animate.animate
import com.soywiz.korge.input.mouse
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.scene.delay
import com.soywiz.korge.sound.DEFAULT_FADE_EASING
import com.soywiz.korge.sound.DEFAULT_FADE_TIME
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.tween.tweenAsync
import com.soywiz.korge.view.*
import com.soywiz.korge.view.tween.hide
import com.soywiz.korge.view.tween.show
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Anchor
import com.soywiz.korma.interpolation.Easing
import constants.DebugStatus
import kotlinx.coroutines.Job
import map.GameMapFactory

class TitleScene : Scene() {
    private var PLAY_ANIMATIONS = DebugStatus.ON // Turn off if animations are annoying during other testing
    private lateinit var title : Image
    private lateinit var bg : Image
    private lateinit var start : Image


    // Entrypoint
    override suspend fun Container.sceneInit() {

        bg = image(resourcesVfs["title\\title_bg.png"].readBitmap()) {
            setSize(this.width, MainModule.size.height / 1.0)
        }

        title = image(resourcesVfs["title\\gits_compact.png"].readBitmap()) {
            anchor(Anchor.MIDDLE_CENTER)
            scale = 0.6
            position(MainModule.size.width / 2.0, MainModule.size.height / 3.0)
        }

        start = image(resourcesVfs["title\\start.png"].readBitmap()) {
            anchor(Anchor.MIDDLE_CENTER)
            position(MainModule.size.width / 2.0, (MainModule.size.height / 3.0) * 2)
            scale = 1.5

            mouse {
                onClick {
                    startOnClickAnimation()
                    sceneContainer.changeTo(MapScene::class)
                }
            }
        }
    }

    // Comment this function out if the fades are getting annoying while testing
    override suspend fun sceneAfterInit() {
        if (PLAY_ANIMATIONS == DebugStatus.ON) {
            title.alpha = 0.0
            bg.alpha = 0.0
            start.alpha = 0.0

            bg.tween(bg::alpha[1.0], time = 1.seconds)
            delay(1.seconds)
            title.tween(title::alpha[1.0], time = 2.seconds)
            delay(0.5.seconds)
            start.tween(start::alpha[1.0], time = 1.seconds)
        }
    }

    private suspend fun startOnClickAnimation() {
        if (PLAY_ANIMATIONS == DebugStatus.ON) {
            start.tween(start::alpha[0.0], time = 1.seconds)
            title.tween(title::y[MainModule.size.height / 2.0], time = 0.5.seconds)
            title.tween(title::scale[10], time = 1.seconds, easing = Easing.EASE_IN_QUAD)
        }
    }
}
