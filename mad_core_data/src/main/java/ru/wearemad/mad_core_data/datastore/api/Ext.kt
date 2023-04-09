package ru.wearemad.mad_core_data.datastore.api

import kotlinx.coroutines.flow.firstOrNull

suspend fun <T : Any?> DataPreferences<T>.getValueOrNull(): T? = getAsFlow().firstOrNull()?.data