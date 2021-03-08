package com.kardia.membership.data.cache

import android.content.Context
import com.kardia.membership.data.cache.serializer.Serializer
import com.kardia.membership.data.entities.UserInfo
import com.kardia.membership.data.entities.UserToken
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserInfoCache
@Inject constructor(
    val context: Context,
    private val serializer: Serializer,
    private val fileManager: FileManager
) {
    private var userInfo: UserInfo? = null
    private val fileDir = "user"
    private val filename: String = "$fileDir${java.io.File.separator}UserInfo"

    fun get(): UserInfo? {
        val file = File("${context.cacheDir}${File.separator}$filename")
        if (userInfo == null) userInfo =
            serializer.deserialize(fileManager.readFileContent(file), UserInfo::class.java)
        if (userInfo == null) userInfo = UserInfo(null,null)
        return userInfo
    }

    fun put(userInfo: UserInfo) {
        this.userInfo = userInfo
        val fileDir = File("${context.cacheDir}${File.separator}$fileDir")
        if (!fileDir.exists()) fileDir.mkdir()
        val file = File("${context.cacheDir}${File.separator}$filename")
        fileManager.writeToFile(file, serializer.serialize(userInfo, UserInfo::class.java))
    }

    fun clear() {
        this.userInfo = null
        val file = File("${context.cacheDir}${File.separator}$fileDir")
        fileManager.clearDirectory(file)
    }
}