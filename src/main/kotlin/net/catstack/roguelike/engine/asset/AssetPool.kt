package net.catstack.roguelike.engine.asset

import net.catstack.engine.render.Texture
import net.catstack.engine.shaders.ShaderProgram
import net.catstack.roguelike.engine.fonts.Font

class AssetPool {
    private val shaders: HashMap<String, ShaderProgram> = hashMapOf()
    private val textures: HashMap<String, Texture> = hashMapOf()
    private val fonts: HashMap<String, Font> = hashMapOf()
    private val texturesFullPath: HashMap<String, Texture> = hashMapOf()

    fun getShader(resourceName: String): ShaderProgram {
        return shaders[resourceName] ?: throw RuntimeException("Can't find shader with resource name \"$resourceName\"")
    }

    fun addShader(resourceName: String, shaderProgram: ShaderProgram) {
        if (shaders[resourceName] == null)
            shaders[resourceName] = shaderProgram
    }

    fun getTexture(resourceName: String): Texture {
        return textures[resourceName] ?: run {
            val texture = Texture("assets/textures/${resourceName}.png")

            textures[resourceName] = texture

            return texture
        }
    }

    fun getTextureFullPath(resourceName: String): Texture {
        return texturesFullPath[resourceName] ?: run {
            val texture = Texture("${resourceName}.png")

            texturesFullPath[resourceName] = texture

            return texture
        }
    }

    fun addTexture(resourceName: String, texture: Texture) {
        if (textures[resourceName] == null)
            textures[resourceName] = texture
    }

    fun getFont(resourceName: String): Font {
        return fonts[resourceName] ?: run {
            val font = Font("assets/fonts/$resourceName")

            fonts[resourceName] = font

            return font
        }
    }
}