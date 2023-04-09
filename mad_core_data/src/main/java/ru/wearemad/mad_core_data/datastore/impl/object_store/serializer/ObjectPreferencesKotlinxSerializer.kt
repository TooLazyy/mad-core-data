package ru.wearemad.mad_core_data.datastore.impl.object_store.serializer

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

/**
 * Сериализует даныне через kotlinx
 * Объект сериализации должен быть с аннотацией @Serializable
 */
class ObjectPreferencesKotlinxSerializer<T : Any>(
    private val dataType: KClass<T>
) : ObjectPreferencesSerializer<T> {

    @OptIn(InternalSerializationApi::class)
    override fun serialize(data: T): String = Json.encodeToString(
        dataType.serializer(),
        data
    )

    @OptIn(InternalSerializationApi::class)
    override fun deserialize(serialized: String): T = Json.decodeFromString(
        dataType.serializer(),
        serialized
    )
}