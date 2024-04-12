package net.catstack.roguelike.game.entities

import net.catstack.engine.render.SpriteBatch
import net.catstack.engine.render.Texture
import net.catstack.roguelike.engine.render.Sprite

abstract class GameObject(texture: Texture?) {
    var sprite = Sprite(texture)

    open fun create() {

    }

    open fun update() {

    }

    fun draw(spriteBatch: SpriteBatch) {
        spriteBatch.draw(sprite)
    }

//    abstract fun getTexture(): Texture
}