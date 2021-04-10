package scenes

import MainModule
import com.soywiz.klock.seconds
import com.soywiz.korge.input.mouse
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.scene.delay
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*
import com.soywiz.korim.format.readBitmap
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Anchor
import com.soywiz.korma.interpolation.Easing
import constants.DebugStatus

class TitleScene : Scene() {
    private var PLAY_ANIMATIONS = DebugStatus.ON // Turn off if animations are annoying during other testing
    private lateinit var title : Image
    private lateinit var bg : Image
    private lateinit var start : Image
    private lateinit var byline : Text

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
                    sceneContainer.changeTo(IntroScene::class)
                }
            }
        }

        byline = text("(c) 2021 Eric Min Park & Rafael Wiska-Ilnicki", textSize = 50.0, alignment = TextAlignment.BOTTOM_LEFT) {
            position(24.0, (MainModule.size.height / 1.0) - 24)
        }
    }

    override suspend fun sceneAfterInit() {
        if (PLAY_ANIMATIONS == DebugStatus.ON) {
            title.alpha = 0.0
            bg.alpha = 0.0
            start.alpha = 0.0
            byline.alpha = 0.0

            // Show background
            bg.tween(bg::alpha[1.0], time = 1.seconds)
            delay(1.seconds)

            // Show title and byline
            launchImmediately { title.tween(title::alpha[1.0], time = 2.seconds) }
            byline.tween(byline::alpha[1.0], time = 2.seconds)

            // Show start button
            start.tween(start::alpha[1.0], time = 1.seconds)
        }
    }

    private suspend fun startOnClickAnimation() {
        if (PLAY_ANIMATIONS == DebugStatus.ON) {
            launchImmediately { byline.tween(byline::alpha[0.0], time = 1.seconds) }
            start.tween(start::alpha[0.0], time = 1.seconds)
            title.tween(title::y[MainModule.size.height / 2.0], time = 0.5.seconds)
            title.tween(title::scale[10], time = 1.seconds, easing = Easing.EASE_IN_QUAD)
        }
    }
}
