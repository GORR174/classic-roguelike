package net.catstack.roguelike.game.entities

import net.catstack.roguelike.engine.Engine

class Shop(x: Int, y: Int) : GameObject(null, x, y) {

    override fun create() {
        val fontChar = Engine.assets.getFont("roboto").fontChars['1'.code]!!
        sprite.texture = fontChar.texture
        sprite.textureCoords = fontChar.textureCoordinates
    }
}