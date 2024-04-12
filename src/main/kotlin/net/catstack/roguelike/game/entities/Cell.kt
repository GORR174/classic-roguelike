package net.catstack.roguelike.game.entities

import net.catstack.roguelike.engine.Engine
import org.joml.Vector4f

class Cell(x: Int, y: Int) : GameObject(null, x, y) {

    override fun create() {
//        val fontChar = Engine.assets.getFont("roboto").fontChars['•'.code]!!
        val fontChar = Engine.assets.getFont("roboto").fontChars['·'.code]!!
        sprite.texture = fontChar.texture
        sprite.textureCoords = fontChar.textureCoordinates
        sprite.color = Vector4f(.25f, .5f, .25f, 1f)
    }
}