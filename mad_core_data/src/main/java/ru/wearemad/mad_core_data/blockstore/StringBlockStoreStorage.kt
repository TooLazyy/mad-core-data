package ru.wearemad.mad_core_data.blockstore

import android.content.Context
import com.google.android.gms.auth.blockstore.Blockstore

class StringBlockStoreStorage(
    context: Context,
    key: String,
) : BlockStoreStorage<String> {

    private val client: BlockStoreStorage<ByteArray> = DefaultBlockStoreStorage(
        key,
        Blockstore.getClient(context)
    )

    override suspend fun set(data: String, syncData: Boolean) {
        client.set(data.toByteArray(Charsets.UTF_8), syncData = syncData)
    }

    override suspend fun get(): String? {
        val bytes = client.get() ?: return null
        return String(bytes, Charsets.UTF_8)
    }
}