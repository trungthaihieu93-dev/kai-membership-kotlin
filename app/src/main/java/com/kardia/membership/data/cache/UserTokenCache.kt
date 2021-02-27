package com.kardia.membership.data.cache

import android.content.Context
import com.kardia.membership.data.cache.serializer.Serializer
import com.kardia.membership.data.entities.UserToken
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserTokenCache
@Inject constructor(
    val context: Context,
    private val serializer: Serializer,
    private val fileManager: FileManager
) {
    private var userToken: UserToken? = null
    private val fileDir = "user"
    private val filename: String = "$fileDir${java.io.File.separator}UserToken"

    fun get(): UserToken? {
        val file = File("${context.cacheDir}${File.separator}$filename")
        if (userToken == null) userToken =
            serializer.deserialize(fileManager.readFileContent(file), UserToken::class.java)
        if (userToken == null) userToken = UserToken()
        return userToken
    }

    fun put(userToken: UserToken) {
        this.userToken = userToken
        val fileDir = File("${context.cacheDir}${File.separator}$fileDir")
        if (!fileDir.exists()) fileDir.mkdir()
        val file = File("${context.cacheDir}${File.separator}$filename")
        fileManager.writeToFile(file, serializer.serialize(userToken, UserToken::class.java))
    }

    fun clear() {
        this.userToken = null
        val file = File("${context.cacheDir}${File.separator}$fileDir")
        fileManager.clearDirectory(file)
    }
}