package net.catstack.roguelike.game.entities

import net.catstack.roguelike.engine.Engine

class Brick(x: Int, y: Int) : GameObject(null, x, y) {

    override fun create() {
        val fontChar = Engine.assets.getFont("roboto").fontChars['#'.code]!!
        sprite.texture = fontChar.texture
        sprite.textureCoords = fontChar.textureCoordinates
    }
}