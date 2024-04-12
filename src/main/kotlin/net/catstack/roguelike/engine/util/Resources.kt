package net.catstack.roguelike.engine.util

import java.nio.file.Paths
import java.util.*

object Resources {
    fun getResourceAsStream(filePath: String) = Thread.currentThread().contextClassLoader.getResourceAsStream(filePath)

    fun getResourceFullPath(filePath: String) : String {
        val uri = Thread.currentThread().contextClassLoader.getResource(filePath)?.toURI()
        return Paths.get(uri!!).toString()
    }

    fun getTextFromResource(filePath: String): String {
        val inputStream = getResourceAsStream(filePath)
        val scanner = Scanner(inputStream).useDelimiter("\\A")
        val result = if (scanner.hasNext()) scanner.next() else ""
        scanner.close()
        inputStream.close()

        return result
    }
}