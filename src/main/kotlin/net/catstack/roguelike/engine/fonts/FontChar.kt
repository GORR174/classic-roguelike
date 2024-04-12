package net.catstack.roguelike.engine.fonts

import net.catstack.engine.render.Texture
import net.catstack.roguelike.engine.render.Sprite
import org.joml.Vector2f


data class FontChar(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
    val xOffset: Int,
    val yOffset: Int,
    val xAdvance: Int,
    val texture: Texture,
    val textureCoordinates: Array<Vector2f>,
)