package scenes

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

/** Screen after completing level **/
class GryphScene : Scene() {
    private lateinit var mgryphGlow : Image
    private lateinit var rage : Image

    private val deviceWidth = MainModule.size.width

    /** Initialize resources **/
    override suspend fun Container.sceneInit() {
        mgryphGlow = image(resourcesVfs["intro\\mgryph_glow.png"].readBitmap()) {
            anchor(Anchor.TOP_LEFT)
            scale = 2.0
            position(50, 400)
        }

        rage = image(resourcesVfs["intro\\rage.png"].readBitmap()) {
            anchor(Anchor.TOP_CENTER)
            position(deviceWidth / 2, 1400)
        }
    }

    /** Animations **/
    override suspend fun sceneAfterInit() {
        mgryphGlow.alpha = 0.0
        rage.alpha = 0.0
        rage.tween(rage::alpha[1.0], time = 2.seconds)

        // Glow gryph eyes
        mgryphGlow.tween(mgryphGlow::alpha[0.3], time = 0.5.seconds)
        mgryphGlow.tween(mgryphGlow::alpha[0.15], time = 0.5.seconds)
        mgryphGlow.tween(mgryphGlow::alpha[0.45], time = 0.5.seconds)
        mgryphGlow.tween(mgryphGlow::alpha[0.3], time = 0.5.seconds)
        mgryphGlow.tween(mgryphGlow::alpha[0.6], time = 0.5.seconds)
        mgryphGlow.tween(mgryphGlow::alpha[0.45], time = 0.5.seconds)
        mgryphGlow.tween(mgryphGlow::alpha[1.0], time = 0.5.seconds)

        // Return to map as normal
        delay(2.5.seconds)
        launchImmediately { rage.tween(rage::alpha[0.0], time = 1.seconds) }
        mgryphGlow.tween(mgryphGlow::alpha[0.0], time = 1.seconds)
        sceneContainer.changeTo(MapScene::class)
    }
}
