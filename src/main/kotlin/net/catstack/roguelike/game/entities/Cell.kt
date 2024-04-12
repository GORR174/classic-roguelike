package net.catstack.roguelike.game.entities

import net.catstack.roguelike.engine.Engine
import net.catstack.roguelike.engine.fonts.Font
import net.catstack.roguelike.engine.input.KeyListener
import net.catstack.roguelike.engine.render.Sprite
import org.joml.Vector4f
import org.lwjgl.glfw.GLFW

class Cell : GameObject(null) {

    override fun create() {
        val fontChar = Engine.assets.getFont("roboto").fontChars['.'.code]!!
        sprite.texture = fontChar.texture
        sprite.textureCoords = fontChar.textureCoordinates
    }

//    override fun getTexture(): Texture {
//        return
//    }
}