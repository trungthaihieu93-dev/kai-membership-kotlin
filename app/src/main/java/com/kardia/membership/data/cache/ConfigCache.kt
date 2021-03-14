package com.kardia.membership.data.cache

import android.content.Context
import com.kardia.membership.data.cache.serializer.Serializer
import com.kardia.membership.domain.entities.config.ConfigEntity
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigCache
@Inject constructor(
    val context: Context,
    private val serializer: Serializer,
    private val fileManager: FileManager
) {
    private var configEntity: ConfigEntity? = null
    private val fileDir = "user"
    private val filename: String = "$fileDir${java.io.File.separator}ConfigEntity"

    fun get(): ConfigEntity? {
        val file = File("${context.cacheDir}${File.separator}$filename")
        if (configEntity == null) configEntity =
            serializer.deserialize(fileManager.readFileContent(file), ConfigEntity::class.java)
        if (configEntity == null) configEntity = ConfigEntity(ArrayList())
        return configEntity
    }

    fun put(configEntity: ConfigEntity) {
        this.configEntity = configEntity
        val fileDir = File("${context.cacheDir}${File.separator}$fileDir")
        if (!fileDir.exists()) fileDir.mkdir()
        val file = File("${context.cacheDir}${File.separator}$filename")
        fileManager.writeToFile(file, serializer.serialize(configEntity, ConfigEntity::class.java))
    }

    fun clear() {
        this.configEntity = null
        val file = File("${context.cacheDir}${File.separator}$fileDir")
        fileManager.clearDirectory(file)
    }
}