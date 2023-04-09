package ru.wearemad.mad_core_data.datastore.impl.string_store

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import ru.wearemad.mad_core_data.datastore.api.DataPreferences
import ru.wearemad.mad_core_data.datastore.impl.defaul_store.DataStorePreferences
import ru.wearemad.mad_core_data.datastore.impl.defaul_store.PreferencesKeyName
import ru.wearemad.mad_core_data.datastore.impl.defaul_store.PreferencesName

/**
 * Реализация префов для хранения строк
 */
class StringDataStorePreferences(
    prefsName: PreferencesName,
    prefsKeyName: PreferencesKeyName,
    context: Context,
    defaultValue: String = ""
) : DataPreferences<String> by DataStorePreferences(
    prefsName,
    defaultValue,
    stringPreferencesKey(prefsKeyName.name),
    context
)