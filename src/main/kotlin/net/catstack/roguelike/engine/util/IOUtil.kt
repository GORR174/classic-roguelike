package net.catstack.roguelike.engine.util

import org.lwjgl.BufferUtils
import kotlin.Throws
import java.io.IOException
import java.nio.channels.Channels
import org.lwjgl.system.MemoryUtil
import java.io.InputStream
import java.nio.ByteBuffer

object IOUtil {
    private fun resizeBuffer(buffer: ByteBuffer?, newCapacity: Int): ByteBuffer {
        val newBuffer = BufferUtils.createByteBuffer(newCapacity)
        buffer!!.flip()
        newBuffer.put(buffer)
        return newBuffer
    }

    /**
     * Reads the specified resource and returns the raw data as a ByteBuffer.
     *
     * @param resource   the resource to read
     * @param bufferSize the initial buffer size
     *
     * @return the resource data
     *
     * @throws IOException if an IO error occurs
     */
    @Throws(IOException::class)
    fun ioResourceToByteBuffer(io: InputStream, bufferSize: Int): ByteBuffer {
        var buffer: ByteBuffer? = null
            try {
                io.use { source ->
                    Channels.newChannel(source).use { rbc ->
                        buffer = BufferUtils.createByteBuffer(bufferSize)
                        while (true) {
                            val bytes = rbc.read(buffer)
                            if (bytes == -1) {
                                break
                            }
                            if (buffer?.remaining() == 0) {
                                buffer = resizeBuffer(buffer, buffer!!.capacity() * 3 / 2) // 50%
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
        }
        buffer!!.flip()
        return MemoryUtil.memSlice(buffer)
    }
}