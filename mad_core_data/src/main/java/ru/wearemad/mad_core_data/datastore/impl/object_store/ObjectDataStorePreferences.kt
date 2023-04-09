package ru.wearemad.mad_core_data.datastore.impl.object_store

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.wearemad.mad_core_data.datastore.api.DataPreferences
import ru.wearemad.mad_core_data.datastore.api.PreferencesData
import ru.wearemad.mad_core_data.datastore.impl.defaul_store.PreferencesKeyName
import ru.wearemad.mad_core_data.datastore.impl.defaul_store.PreferencesName
import ru.wearemad.mad_core_data.datastore.impl.object_store.serializer.ObjectPreferencesSerializer
import ru.wearemad.mad_core_data.datastore.impl.string_store.StringDataStorePreferences

/**
 * Префы, которые могут хранить любые сериализуемые объекты.
 * Работают через сериализацию в строку, которая затем пишется в StringDataStorePreferences
 */
class ObjectDataStorePreferences<T : Any>(
    prefsName: PreferencesName,
    prefsKeyName: PreferencesKeyName,
    context: Context,
    private val defaultValue: T,
    private val serializer: ObjectPreferencesSerializer<T>
) : DataPreferences<T> {

    private val stringPrefsDelegate = StringDataStorePreferences(
        prefsName,
        prefsKeyName,
        context,
        serializer.serialize(defaultValue)
    )

    override suspend fun set(newValue: T) {
        stringPrefsDelegate.set(
            serializer.serialize(newValue)
        )
    }

    override suspend fun update(updateAction: suspend (T) -> T) {
        stringPrefsDelegate.update {
            val currentValue = it.toObjectOrDefault()
            val newValue = updateAction(currentValue)
            serializer.serialize(newValue)
        }
    }

    override suspend fun clear() {
        stringPrefsDelegate.clear()
    }

    override suspend fun isSet(): Boolean = stringPrefsDelegate.isSet()

    override fun getAsFlow(): Flow<PreferencesData<T>> =
        stringPrefsDelegate.getAsFlow()
            .map {
                PreferencesData(
                    it.data.toObjectOrDefault(),
                    it.isDefaultValueUsed
                )
            }

    private fun String.toObjectOrDefault(): T = try {
        serializer.deserialize(this)
    } catch (_: Exception) {
        defaultValue
    }
}