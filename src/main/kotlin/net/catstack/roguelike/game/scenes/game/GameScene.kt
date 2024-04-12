package net.catstack.roguelike.game.scenes.game

import mu.KotlinLogging
import net.catstack.roguelike.engine.Engine
import net.catstack.roguelike.engine.input.KeyListener
import net.catstack.roguelike.engine.scenes.Scene
import org.lwjgl.glfw.GLFW

private val log = KotlinLogging.logger {  }

class GameScene : Scene() {

    override fun onCreate() {
        layers.add(GameLayer())
    }

    override fun update(delta: Float) {
        if (KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_ESCAPE)) {
            log.info { "close" }
            Engine.close()
        }
    }
}