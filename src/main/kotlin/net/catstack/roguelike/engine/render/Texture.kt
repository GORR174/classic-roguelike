package net.catstack.engine.render

import net.catstack.roguelike.engine.util.Resources
import net.catstack.roguelike.engine.util.IOUtil
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.stb.STBImage.*

class Texture(val filePath: String) {
    private var texID = 0;

    val width: Int
    val height: Int

    init {
        texID = glGenTextures()
        glBindTexture(GL_TEXTURE_2D, texID)

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)

        val imageBuffer = IOUtil.ioResourceToByteBuffer(Resources.getResourceAsStream(filePath), 8 * 1024)

        val width = BufferUtils.createIntBuffer(1)
        val height = BufferUtils.createIntBuffer(1)
        val channels = BufferUtils.createIntBuffer(1)

        stbi_is_hdr_from_memory(imageBuffer)
        stbi_set_flip_vertically_on_load(true)
        val image = stbi_load_from_memory(imageBuffer, width, height, channels, 0)

        if (image != null) {
            if (channels[0] == 3)
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width[0], height[0], 0, GL_RGB, GL_UNSIGNED_BYTE, image)
            else if (channels[0] == 4)
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width[0], height[0], 0, GL_RGBA, GL_UNSIGNED_BYTE, image)
            else
                throw throw Exception("Could not load image: $filePath. Wrong channels count")
        } else {
            throw Exception("Could not load image: $filePath")
        }

        this.width = width[0]
        this.height = height[0]

        stbi_image_free(image)
    }

    fun bind() {
        glBindTexture(GL_TEXTURE_2D, texID)
    }

    fun unbind() {
        glBindTexture(GL_TEXTURE_2D, 0)
    }
}