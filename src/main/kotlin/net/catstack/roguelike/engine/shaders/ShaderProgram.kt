package net.catstack.engine.shaders

import mu.KotlinLogging
import net.catstack.roguelike.engine.util.Resources
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL20.*
import kotlin.system.exitProcess

private val log = KotlinLogging.logger {}

abstract class ShaderProgram(vertexFilePath: String, fragmentFilePath: String) {
    val programID: Int
    private val vertexShaderID: Int
    private val fragmentShaderId: Int

    init {
        val prefix = "assets/shaders/"
        vertexShaderID = loadShader(prefix + vertexFilePath, GL20.GL_VERTEX_SHADER)
        fragmentShaderId = loadShader(prefix + fragmentFilePath, GL20.GL_FRAGMENT_SHADER)
        programID = GL20.glCreateProgram()
        GL20.glAttachShader(programID, vertexShaderID)
        GL20.glAttachShader(programID, fragmentShaderId)
        GL20.glLinkProgram(programID)
        GL20.glValidateProgram(programID)
        useShaderProgram()
        setDefaultUniforms()
        GL20.glDeleteShader(vertexShaderID)
        GL20.glDeleteShader(fragmentShaderId)
    }

    abstract fun setDefaultUniforms()

    fun bindUniform(uniformName: String, bindAction: (Int) -> Unit) {
        val vertexColorLocation = GL20.glGetUniformLocation(programID, uniformName)
        bindAction(vertexColorLocation)
    }

    fun setBool(uniformName: String, value: Boolean) {
        glUniform1i(glGetUniformLocation(programID, uniformName), if (value) 1 else 0)
    }

    fun setInt(uniformName: String, value: Int) {
        glUniform1i(glGetUniformLocation(programID, uniformName), value)
    }

    fun setFloat(uniformName: String, value: Float) {
        glUniform1f(glGetUniformLocation(programID, uniformName), value)
    }

    fun setVector3f(uniformName: String, value: Vector3f) {
        glUniform3f(glGetUniformLocation(programID, uniformName), value.x, value.y, value.z)
    }

    fun setMat4f(uniformName: String, value: Matrix4f) {
        val varLocation = glGetUniformLocation(programID, uniformName)
        val matBuffer = BufferUtils.createFloatBuffer(16)
        value.get(matBuffer)
        glUniformMatrix4fv(varLocation, false, matBuffer)
    }

    fun setTexture(uniformName: String, slot: Int) {
        val varLocation = glGetUniformLocation(programID, uniformName)

        glUniform1i(varLocation, slot)
    }

    fun setIntArray(uniformName: String, array: IntArray) {
        val varLocation = glGetUniformLocation(programID, uniformName)

        glUniform1iv(varLocation, array)
    }

    fun setVec2Array(uniformName: String, array: Array<Vector2f?>) {
        val varLocation = glGetUniformLocation(programID, uniformName)

        glUniform2fv(varLocation, array.map { if (it != null) listOf(it.x, it.y) else listOf(0f, 0f) }.flatten().toFloatArray())
    }

    fun useShaderProgram() {
        glUseProgram(programID)
        setDefaultUniforms()
    }

    fun dispose() {
        GL20.glUseProgram(0)
        GL20.glDetachShader(programID, vertexShaderID)
        GL20.glDetachShader(programID, fragmentShaderId)
        GL20.glDeleteProgram(programID)
    }

    private fun loadShader(filePath: String, shaderType: Int): Int {
        val shaderSource = Resources.getTextFromResource(filePath)

        val shaderID = GL20.glCreateShader(shaderType)
        GL20.glShaderSource(shaderID, shaderSource)
        GL20.glCompileShader(shaderID)

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            log.info { GL20.glGetShaderInfoLog(shaderID, 500) }
            log.error { "Could not compile shader!" }
            System.err.println("Could not compile shader!")
            exitProcess(-1)
        }

        return shaderID
    }
}