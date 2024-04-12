package net.catstack.roguelike.engine.fonts

import net.catstack.roguelike.engine.Engine
import net.catstack.roguelike.engine.render.Sprite
import net.catstack.roguelike.engine.util.Resources
import org.joml.Vector2f
import java.util.*
import kotlin.collections.HashMap

class Font(font: String) {
    val fontTexture = Engine.assets.getTextureFullPath("$font")
    private val fontFileText = Resources.getTextFromResource("$font.fnt")

    val fontChars = HashMap<Int, FontChar>()

    init {
        fontFileText.lines()
            .filter { it.startsWith("char ") }
            .forEach {
                val scanner = Scanner(it).useDelimiter(" ".toString())

                var charId = 0
                var x = 0
                var y = 0
                var width = 0
                var height = 0
                var xOffset = 0
                var yOffset = 0
                var xAdvance = 0

                while (scanner.hasNext()) {
                    val input = scanner.next()
                    when {
                        input.startsWith("id=") -> {
                            charId = input.substringAfter("id=").toInt()
                        }
                        input.startsWith("x=") -> {
                            x = input.substringAfter("x=").toInt()
                        }
                        input.startsWith("y=") -> {
                            y = input.substringAfter("y=").toInt()
                        }
                        input.startsWith("width=") -> {
                            width = input.substringAfter("width=").toInt()
                        }
                        input.startsWith("height=") -> {
                            height = input.substringAfter("height=").toInt()
                        }
                        input.startsWith("xoffset=") -> {
                            xOffset = input.substringAfter("xoffset=").toInt()
                        }
                        input.startsWith("yoffset=") -> {
                            yOffset = input.substringAfter("yoffset=").toInt()
                        }
                        input.startsWith("xadvance=") -> {
                            xAdvance = input.substringAfter("xadvance=").toInt()
                        }
                    }
                }
                scanner.close()

                val texCoords = arrayOf(
                    Vector2f((x + width).toFloat() / fontTexture.width, 1 - y.toFloat() / fontTexture.height),
                    Vector2f((x + width).toFloat() / fontTexture.width, 1 - (y + height).toFloat() / fontTexture.height),
                    Vector2f(x.toFloat() / fontTexture.width, 1 - (y + height).toFloat() / fontTexture.height),
                    Vector2f(x.toFloat() / fontTexture.width, 1 - y.toFloat() / fontTexture.height),
                )

//                val charSpriteComponent = Sprite(texture = fontTexture, textureCoords = texCoords)

                fontChars[charId] = FontChar(x, y, width, height, xOffset, yOffset, xAdvance, fontTexture, texCoords)
            }
    }
}