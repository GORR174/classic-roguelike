package net.catstack.roguelike.engine.scenes

import mu.KotlinLogging
import net.catstack.roguelike.engine.Engine
import net.catstack.roguelike.engine.input.KeyListener
import org.lwjgl.glfw.GLFW

private val log = KotlinLogging.logger {}

abstract class Game(scene: Scene = Scene.emptyScene) {

    var scene: Scene = scene
        set(value) {
            scene.dispose()
            field = value
            log.info { "Set scene: ${value::class.simpleName}" }
        }

    open fun onCreate() {
        log.info { "Game is starting" }
    }

    open fun update(delta: Float) {
        if (!scene.hasBeenCreated)
            scene.create()

        scene.update(delta)
        scene.layers.forEach { it.update(delta) }

        if (KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_F5)) {
            Engine.game.scene = scene::class.java.getConstructor().newInstance()
            log.info { "Scene was recreated!" }
        }
    }

    fun dispose() {
        scene.dispose()
        log.info { "Game is disposing" }
    }

    open fun onResize(width: Int, height: Int) {

    }
}