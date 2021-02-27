package com.kardia.membership.data.cache.serializer

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SerializerList @Inject internal constructor() {
    private val gson = Gson()

    /**
     * Serialize an object to Json.
     *
     * @param object to serialize.
     */
    fun serialize(`object`: Any?, clazz: Class<*>?): String {
        return gson.toJson(`object`, clazz)
    }

    /**
     * Deserialize a json representation of an object.
     *
     * @param string A json string to deserialize.
     */
    fun <T> deserialize(
        jsonArray: String?,
        clazz: Class<T>?
    ): MutableList<T?>? {
        val typeOfT: Type = TypeToken.getParameterized(
            MutableList::class.java,
            clazz
        ).type
        return Gson().fromJson(jsonArray, typeOfT)
    }
}