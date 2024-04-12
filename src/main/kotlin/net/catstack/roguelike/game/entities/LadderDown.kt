package net.catstack.roguelike.game.entities

import net.catstack.roguelike.engine.Engine
import net.catstack.roguelike.engine.fonts.Font
import net.catstack.roguelike.engine.input.KeyListener
import net.catstack.roguelike.engine.render.Sprite
import org.joml.Vector4f
import org.lwjgl.glfw.GLFW

class LadderDown(x: Int, y: Int) : GameObject(null, x, y) {

    override fun create() {
        val fontChar = Engine.assets.getFont("roboto").fontChars['>'.code]!!
        sprite.texture = fontChar.texture
        sprite.textureCoords = fontChar.textureCoordinates
//        sprite.color = Vector4f(1f, 1f, 1f, 7f)
    }
}