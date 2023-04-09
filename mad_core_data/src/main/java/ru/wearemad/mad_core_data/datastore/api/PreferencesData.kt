package ru.wearemad.mad_core_data.datastore.api

data class PreferencesData<T : Any?>(
    val data: T,
    val isDefaultValueUsed: Boolean
)