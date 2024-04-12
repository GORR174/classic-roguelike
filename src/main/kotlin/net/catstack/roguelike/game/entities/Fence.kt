package net.catstack.roguelike.game.entities

import net.catstack.roguelike.engine.Engine
import net.catstack.roguelike.engine.fonts.Font
import net.catstack.roguelike.engine.input.KeyListener
import net.catstack.roguelike.engine.render.Sprite
import org.joml.Vector4f
import org.lwjgl.glfw.GLFW

class Fence(val symbol: Char, x: Int, y: Int) : GameObject(null, x, y) {

    override fun create() {
        val fontChar = Engine.assets.getFont("roboto").fontChars[symbol.code]!!
        sprite.texture = fontChar.texture
        sprite.textureCoords = fontChar.textureCoordinates
        sprite.color = Vector4f(0.5f, 0.2f, 0f, 1f)
    }
}