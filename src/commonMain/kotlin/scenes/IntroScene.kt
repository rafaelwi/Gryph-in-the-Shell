package scenes

import MainModule
import com.soywiz.klock.seconds
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.scene.delay
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Anchor
import com.soywiz.korma.interpolation.Easing
import constants.DebugStatus

class IntroScene : Scene() {
    private var PLAY_ANIMATIONS = DebugStatus.ON

    private lateinit var skyline : Image
    private lateinit var mgryphPlain : Image
    private lateinit var mgryphGlow : Image

    private lateinit var title : Image
    private lateinit var takeover : Image
    private lateinit var army : Image
    private lateinit var saveGuelph : Image

    private val deviceWidth = MainModule.size.width

    // Declare all images and shapes used here
    override suspend fun Container.sceneInit() {
        skyline = image(resourcesVfs["intro\\neoguelph_skyline.png"].readBitmap()) {
            anchor(Anchor.TOP_LEFT)
            position(0, 0)
        }

        mgryphPlain = image(resourcesVfs["intro\\mgryph.png"].readBitmap()) {
            anchor(Anchor.TOP_LEFT)
            scale = 2.0
            position(50, 400)
        }

        mgryphGlow = image(resourcesVfs["intro\\mgryph_glow.png"].readBitmap()) {
            anchor(Anchor.TOP_LEFT)
            scale = 2.0
            position(50, 400)
        }

        title = image(resourcesVfs["intro\\neoguelph_2120.png"].readBitmap()) {
            anchor(Anchor.TOP_CENTER)
            position(deviceWidth / 2, 1400)
        }

        takeover = image(resourcesVfs["intro\\robot_takeover.png"].readBitmap()) {
            anchor(Anchor.TOP_CENTER)
            position(deviceWidth / 2, 1400)
        }

        army = image(resourcesVfs["intro\\army_commands.png"].readBitmap()) {
            anchor(Anchor.TOP_CENTER)
            position(deviceWidth / 2, 1400)
        }

        saveGuelph = image(resourcesVfs["intro\\save_neoguelph.png"].readBitmap()) {
            anchor(Anchor.TOP_CENTER)
            position(deviceWidth / 2, 1400)
        }
    }

    // Do animations here
    override suspend fun sceneAfterInit() {
        if (PLAY_ANIMATIONS == DebugStatus.ON) {
            skyline.alpha = 0.0
            mgryphPlain.alpha = 0.0
            mgryphGlow.alpha = 0.0
            title.alpha = 0.0
            takeover.alpha = 0.0
            army.alpha = 0.0
            saveGuelph.alpha = 0.0

            // Fade in skyline and title, start skyline scrolling
            skyline.tween(skyline::alpha[1.0], time = 1.seconds)
            title.tween(title::alpha[1.0], time = 2.seconds)
            launchImmediately { skyline.tween(skyline::x[-1320], time = 10.seconds, easing = Easing.LINEAR) }

            // After 5 seconds, move to takeover text
            delay(5.seconds)
            launchImmediately { title.tween(title::alpha[0.0], time = 1.seconds) }
            takeover.tween(takeover::alpha[1.0], time = 1.2.seconds)

            // After 5 seconds, move on to army text
            delay(5.seconds)
            launchImmediately { skyline.tween(skyline::alpha[0.0], time = 1.5.seconds) }
            takeover.tween(takeover::alpha[0.0], time = 1.5.seconds)

            // Fade in gryphon and army text
            mgryphPlain.tween(mgryphPlain::alpha[1.0], time = 1.seconds)
            army.tween(army::alpha[1.0], time = 2.seconds)

            // After 5 seconds, fade out both, bring hero text, and glow eyes!
            delay(5.seconds)
            launchImmediately { mgryphPlain.tween(mgryphPlain::alpha[0.01], time = 1.seconds) }
            army.tween(army::alpha[0.0], time = 1.seconds)
            saveGuelph.tween(saveGuelph::alpha[1.0], time = 1.seconds)

            // Glow gryph eyes
            mgryphGlow.tween(mgryphGlow::alpha[0.3], time = 0.5.seconds)
            mgryphGlow.tween(mgryphGlow::alpha[0.15], time = 0.5.seconds)
            mgryphGlow.tween(mgryphGlow::alpha[0.45], time = 0.5.seconds)
            mgryphGlow.tween(mgryphGlow::alpha[0.3], time = 0.5.seconds)
            mgryphGlow.tween(mgryphGlow::alpha[0.6], time = 0.5.seconds)
            mgryphGlow.tween(mgryphGlow::alpha[0.45], time = 0.5.seconds)
            mgryphGlow.tween(mgryphGlow::alpha[1.0], time = 0.5.seconds)

            // Fade out everything and transition to map
            delay(2.5.seconds)
            saveGuelph.tween(saveGuelph::alpha[0.0], time = 1.5.seconds)
            delay(0.5.seconds)
            mgryphGlow.tween(mgryphGlow::alpha[0.0], time = 0.5.seconds)
            sceneContainer.changeTo(MapScene::class)
        }
    }
}
