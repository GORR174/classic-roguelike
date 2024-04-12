package net.catstack.roguelike.game.entities

import net.catstack.roguelike.engine.Engine
import net.catstack.roguelike.engine.fonts.Font
import net.catstack.roguelike.engine.input.KeyListener
import net.catstack.roguelike.engine.render.Sprite
import org.joml.Vector4f
import org.lwjgl.glfw.GLFW

class Player(x: Int, y: Int) : GameObject(null, x, y) {

    override fun create() {
        val fontChar = Engine.assets.getFont("roboto").fontChars['@'.code]!!
        sprite.texture = fontChar.texture
        sprite.textureCoords = fontChar.textureCoordinates
//        sprite.color = Vector4f(1f, 0f, 1f, 1f)
    }

    override fun update() {
        if (KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_RIGHT)
            || KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_KP_6)
            || KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_L)) {
            sprite.position.x += 1
        }
        if (KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_LEFT)
            || KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_KP_4)
            || KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_H)) {
            sprite.position.x -= 1
        }
        if (KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_UP)
            || KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_KP_8)
            || KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_K)) {
            sprite.position.y += 1
        }
        if (KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_DOWN)
            || KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_KP_2)
            || KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_J)) {
            sprite.position.y -= 1
        }
        if (KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_KP_7)
            || KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_Y)) {
            sprite.position.x -= 1
            sprite.position.y += 1
        }
        if (KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_KP_9)
            || KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_U)) {
            sprite.position.x += 1
            sprite.position.y += 1
        }
        if (KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_KP_1)
            || KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_B)) {
            sprite.position.x -= 1
            sprite.position.y -= 1
        }
        if (KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_KP_3)
            || KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_N)) {
            sprite.position.x += 1
            sprite.position.y -= 1
        }
    }
}