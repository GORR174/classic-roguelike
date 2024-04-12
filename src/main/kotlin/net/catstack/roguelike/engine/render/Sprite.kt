package net.catstack.roguelike.engine.render

import net.catstack.engine.render.Texture
import org.joml.Vector2f
import org.joml.Vector4f
import java.awt.geom.Rectangle2D

private val rectangleTexCoords = arrayOf(
    Vector2f(1f, 1f),
    Vector2f(1f, 0f),
    Vector2f(0f, 0f),
    Vector2f(0f, 1f),
)

class Sprite(texture: Texture? = null, isFlipped: Boolean = false, color: Vector4f = Vector4f(1f, 1f, 1f, 1f), textureCoords: Array<Vector2f> = rectangleTexCoords) {
    var isDirty = true

    var position: Vector2f = Vector2f()
    var size: Vector2f = Vector2f()

    var color = color
        set(value) {
            field = value
            isDirty = true
        }

    var texture = texture
        set(value) {
            field = value
            isDirty = true
        }

    var isFlipped = isFlipped
        set(value) {
            field = value
            isDirty = true
        }

    var textureCoords: Array<Vector2f> = textureCoords
        get() {
            if (isFlipped) {
                return field.reversedArray()
            }

            return field
        }
        set(value) {
            field = value
            isDirty = true
        }
}