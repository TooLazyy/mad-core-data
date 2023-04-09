package ru.wearemad.mad_core_data.datastore.impl.object_store.serializer

/**
 * Абстрагирует логику сериализации данных для ObjectDataStorePreferences
 */
interface ObjectPreferencesSerializer<T : Any> {

    @Throws(RuntimeException::class)
    fun serialize(data: T): String

    @Throws(RuntimeException::class)
    fun deserialize(serialized: String): T
}