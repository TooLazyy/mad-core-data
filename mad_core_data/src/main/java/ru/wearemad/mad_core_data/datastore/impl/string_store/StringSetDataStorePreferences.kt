package ru.wearemad.mad_core_data.datastore.impl.string_store

import android.content.Context
import androidx.datastore.preferences.core.stringSetPreferencesKey
import ru.wearemad.mad_core_data.datastore.api.DataPreferences
import ru.wearemad.mad_core_data.datastore.impl.defaul_store.DataStorePreferences
import ru.wearemad.mad_core_data.datastore.impl.defaul_store.PreferencesKeyName
import ru.wearemad.mad_core_data.datastore.impl.defaul_store.PreferencesName

/**
 * Реализация префов для хранения сета строк
 */
class StringSetDataStorePreferences(
    prefsName: PreferencesName,
    prefsKeyName: PreferencesKeyName,
    context: Context,
    defaultValue: Set<String> = emptySet()
) : DataPreferences<Set<String>> by DataStorePreferences(
    prefsName,
    defaultValue,
    stringSetPreferencesKey(prefsKeyName.name),
    context
)