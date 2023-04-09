package ru.wearemad.mad_core_data.datastore.impl.object_store.serializer

inline fun <reified T : Any> createKotlinxSerializer(): ObjectPreferencesSerializer<T> =
    ObjectPreferencesKotlinxSerializer(
        dataType = T::class
    )