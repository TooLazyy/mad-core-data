package ru.wearemad.mad_core_data.datastore.impl.defaul_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import ru.wearemad.mad_core_data.datastore.api.DataPreferences
import ru.wearemad.mad_core_data.datastore.api.PreferencesData

@JvmInline
value class PreferencesName(val name: String)

@JvmInline
value class PreferencesKeyName(val name: String)

/**
 * Реализация кор префов через дата стор
 * Хранить может ТОЛЬКО примитивы. См. intPreferencesKey и другие ключи
 */
open class DataStorePreferences<T : Any>(
    prefsName: PreferencesName,
    protected val defaultValue: T,
    private val prefsKey: Preferences.Key<T>,
    protected val context: Context,
) : DataPreferences<T> {

    /**
     * Из-за реализации дата стора, префы нужно держать синглтоном!
     */
    protected val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = prefsName.name)

    override suspend fun set(newValue: T) {
        context.dataStore.edit {
            it[prefsKey] = newValue
        }
    }

    override suspend fun update(updateAction: suspend (T) -> T) {
        context.dataStore.edit {
            it[prefsKey] = updateAction(it[prefsKey] ?: defaultValue)
        }
    }

    override suspend fun clear() {
        context.dataStore.edit {
            it.clear()
        }
    }

    override suspend fun isSet(): Boolean =
        context.dataStore
            .data
            .firstOrNull()
            ?.contains(prefsKey) ?: false

    override fun getAsFlow(): Flow<PreferencesData<T>> =
        context.dataStore.data
            .map {
                val prefsData = it[prefsKey]
                PreferencesData(
                    prefsData ?: defaultValue,
                    prefsData == null
                )
            }
}