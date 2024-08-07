package ru.wearemad.mad_core_data.blockstore

interface BlockStoreStorage<T : Any?> {

    suspend fun set(data: T, syncData: Boolean)

    suspend fun get(): T?
}