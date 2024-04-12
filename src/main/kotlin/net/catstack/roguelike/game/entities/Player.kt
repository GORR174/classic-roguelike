package net.catstack.roguelike.game.entities

import net.catstack.roguelike.engine.Engine
import net.catstack.roguelike.engine.fonts.Font
import net.catstack.roguelike.engine.input.KeyListener
import net.catstack.roguelike.engine.render.Sprite
import org.joml.Vector4f
import org.lwjgl.glfw.GLFW

class Player : GameObject(null) {

    override fun create() {
        val fontChar = Engine.assets.getFont("roboto").fontChars['@'.code]!!
        sprite.texture = fontChar.texture
        sprite.textureCoords = fontChar.textureCoordinates
        sprite.color = Vector4f(1f, 0f, 1f, 1f)
    }

    override fun update() {
        if (KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_RIGHT)) {
            sprite.position.x += 1
        }
        if (KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_LEFT)) {
            sprite.position.x -= 1
        }
        if (KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_UP)) {
            sprite.position.y += 1
        }
        if (KeyListener.isKeyJustPressed(GLFW.GLFW_KEY_DOWN)) {
            sprite.position.y -= 1
        }
    }
//    override fun getTexture(): Texture {
//        return
//    }
}