package net.catstack.engine.render

import net.catstack.engine.shaders.ShaderProgram
import net.catstack.engine.shaders.default.DefaultShaderProgram
import net.catstack.roguelike.engine.Engine
import net.catstack.roguelike.engine.render.Sprite
import org.joml.Matrix4f
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30

private const val MAX_BATCH_SIZE = 1000
private var projectionMatrix = Matrix4f().apply {
    identity()
    ortho(0f, 1280f, 0f, 720f, 0f, 100f)
}

class SpriteBatch(val fontSize: Int) {
    private val batches = arrayListOf<SpriteBatchHelper>()

    companion object {
        var drawCalls = 0
            private set
    }

    fun begin() {
        drawCalls = 0
        batches.forEach { it.reset() }
    }

    fun draw(sprite: Sprite) {
        var added = false

        for (batch in batches) {
            if (batch.hasRoom) {
                if (sprite.texture == null || (batch.textures.size < 8 || sprite.texture in batch.textures)) {
                    batch.addSprite(sprite)
                    added = true
                    break
                }
            }
        }

        if (!added) {
            val newBatch = SpriteBatchHelper(MAX_BATCH_SIZE, fontSize)
            batches.add(newBatch)
            newBatch.addSprite(sprite)
        }
    }

    fun end() {
        batches.forEach {
            drawCalls++
            it.render()
        }
    }

    private class SpriteBatchHelper(val maxBatchSize: Int, val fontSize: Int) {
        private val posSize = 2
        private val colorSize = 4
        private val texCoordsSize = 2
        private val texIdSize = 1

        private val posOffset = 0
        private val colorOffset = posOffset + posSize * Float.SIZE_BYTES
        private val texCoordsOffset = colorOffset + colorSize * Float.SIZE_BYTES
        private val texIdOffset = texCoordsOffset + texCoordsSize * Float.SIZE_BYTES
        private val vertexSize = 9
        private val vertexSizeBytes = vertexSize * Float.SIZE_BYTES

        private val sprites: Array<Sprite?> = arrayOfNulls(maxBatchSize)

        private var numSprites = 0
        var hasRoom = true
            private set

        private var vertices: FloatArray = FloatArray(maxBatchSize * 4 * vertexSize)
        private val texSlots = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7)

        val textures = arrayListOf<Texture>()

        private var vaoId: Int = 0
        private var vboId: Int = 0

        private val shader: ShaderProgram = DefaultShaderProgram()

        init {
            vaoId = GL30.glGenVertexArrays()
            GL30.glBindVertexArray(vaoId)

            vboId = GL30.glGenBuffers()
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId)
            GL15.glBufferData(GL30.GL_ARRAY_BUFFER, (vertices.size * Float.SIZE_BYTES).toLong(), GL30.GL_DYNAMIC_DRAW)

            val eboId = GL30.glGenBuffers()
            val indices = generateIndices()
            GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, eboId)
            GL15.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indices, GL30.GL_STATIC_DRAW)

            glVertexAttribPointer(0, posSize, GL30.GL_FLOAT, false, vertexSizeBytes, posOffset.toLong())
            GL30.glEnableVertexAttribArray(0)

            glVertexAttribPointer(1, colorSize, GL30.GL_FLOAT, false, vertexSizeBytes, colorOffset.toLong())
            GL30.glEnableVertexAttribArray(1)

            glVertexAttribPointer(2, texCoordsSize, GL_FLOAT, false, vertexSizeBytes, texCoordsOffset.toLong())
            GL30.glEnableVertexAttribArray(2)

            glVertexAttribPointer(3, texIdSize, GL_FLOAT, false, vertexSizeBytes, texIdOffset.toLong())
            GL30.glEnableVertexAttribArray(3)
        }

        fun reset() {
            repeat(vertices.size) {
                vertices[it] = 0f
            }
            numSprites = 0
            hasRoom = true
            repeat(sprites.size) {
                sprites[it] = null
            }
        }

        fun render() {
            if (numSprites == 0)
                return

            var rebufferData = true

            repeat(numSprites) {
                val sprite = sprites[it]!!
                if (sprite.isDirty) {
                    sprite.isDirty = false
                    rebufferData = true
                }
            }

            if (rebufferData) {
                GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId)
                GL15.glBufferSubData(GL30.GL_ARRAY_BUFFER, 0, vertices)
            }

            val game = Engine.game
            shader.useShaderProgram()
            shader.setMat4f("uProjection", projectionMatrix)
            textures.forEachIndexed { index, texture ->
                glActiveTexture(GL_TEXTURE0 + index)
                texture.bind()
            }
            shader.setIntArray("uTextures", texSlots)

            GL30.glBindVertexArray(vaoId)

            GL11.glDrawElements(GL30.GL_TRIANGLES, numSprites * 6, GL30.GL_UNSIGNED_INT, 0)

            GL30.glBindVertexArray(0)

            textures.forEach { texture ->
                texture.unbind()
            }
        }

        fun addSprite(sprite: Sprite) {
            val index = numSprites
            sprites[index] = sprite
            numSprites++

            if (sprite.texture != null) {
                val texture = sprite.texture!!
                if (texture !in textures) {
                    textures.add(texture)
                }
            }

            loadVertexProperties(index)

            if (numSprites >= maxBatchSize) {
                hasRoom = false
            }
        }

        private fun loadVertexProperties(index: Int) {
            val sprite = sprites[index] ?: throw RuntimeException("Can't find sprite")

            var offset = index * 4 * vertexSize

            val color = sprite.color
            val texCoords = sprite.textureCoords

            var texId = -1
            if (sprite.texture != null) {
                for (i in 0 until textures.size) {
                    if (textures[i] == sprite.texture) {
                        texId = i
                        break
                    }
                }
            }

            var xAdd = 1f
            var yAdd = 1f
            repeat(4) {
                when (it) {
                    1 -> yAdd = 0f
                    2 -> xAdd = 0f
                    3 -> yAdd = 1f
                }

                vertices[offset] = sprite.position.x * (fontSize - 4) + (xAdd * fontSize * 0.7f)// - (transform.scale.x * transform.origin.x)
                vertices[offset + 1] = sprite.position.y * fontSize + (yAdd * fontSize)// - (transform.scale.y * transform.origin.y)

                vertices[offset + 2] = color.x
                vertices[offset + 3] = color.y
                vertices[offset + 4] = color.z
                vertices[offset + 5] = color.w

                vertices[offset + 6] = texCoords[it].x
                vertices[offset + 7] = texCoords[it].y

                vertices[offset + 8] = texId.toFloat()

                offset += vertexSize
            }
        }

        private fun generateIndices(): IntArray {
            val elements = IntArray(6 * maxBatchSize)

            for (i in 0 until maxBatchSize) {
                loadElementIndices(elements, i)
            }

            return elements
        }

        private fun loadElementIndices(elements: IntArray, index: Int) {
            val offsetArrayIndex = 6 * index
            val offset = 4 * index

            elements[offsetArrayIndex] = offset + 3
            elements[offsetArrayIndex + 1] = offset + 2
            elements[offsetArrayIndex + 2] = offset

            elements[offsetArrayIndex + 3] = offset
            elements[offsetArrayIndex + 4] = offset + 2
            elements[offsetArrayIndex + 5] = offset + 1
        }
    }
}