package ru.wearemad.mad_core_data.datastore.api

import kotlinx.coroutines.flow.Flow

/**
 * Базовый интерфейс префов. Предполагает хранение одной пары ключ+значение.
 * Можно рассматривать как одну таблицу с данными в room
 * Инстанс релаизации через дата стор (DataStorePreferences) должен быть синглтоном!!!
 */
interface DataPreferences<T : Any?> {

    /**
     * Сетинг нового значения
     */
    suspend fun set(newValue: T)

    /**
     * Апдейт значения
     */
    suspend fun update(updateAction: suspend (T) -> T)

    /**
     * Чистка префов
     */
    suspend fun clear()

    /**
     * Проверка наличия значения
     */
    suspend fun isSet(): Boolean

    /**
     * Обзерв изменений через флоу
     */
    fun getAsFlow(): Flow<PreferencesData<T>>
}