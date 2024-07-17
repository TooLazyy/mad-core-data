package ru.wearemad.mad_core_data.blockstore

import com.google.android.gms.auth.blockstore.BlockstoreClient
import com.google.android.gms.auth.blockstore.RetrieveBytesRequest
import com.google.android.gms.auth.blockstore.StoreBytesData
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class DefaultBlockStoreStorage(
    private val key: String,
    private val blockstore: BlockstoreClient
) : BlockStoreStorage<ByteArray> {

    override suspend fun set(data: ByteArray, syncData: Boolean) {
        setInternal(data, syncData = syncData)
    }

    override suspend fun get(): ByteArray? = getInternal()

    private suspend fun setInternal(
        data: ByteArray,
        syncData: Boolean
    ) = suspendCancellableCoroutine { cont ->
        val request = StoreBytesData.Builder()
            .setBytes(data)
            .setKey(key)
            .setShouldBackupToCloud(syncData)
            .build()
        blockstore.storeBytes(request)
            .addOnSuccessListener {
                cont.resume(Unit)
            }
            .addOnFailureListener {
                cont.resume(Unit)
            }
    }

    private suspend fun getInternal(): ByteArray? = suspendCancellableCoroutine { cont ->
        val request = RetrieveBytesRequest.Builder()
            .setKeys(listOf(key))
            .build()

        blockstore.retrieveBytes(request)
            .addOnSuccessListener {
                val data = it.blockstoreDataMap[key]?.bytes
                cont.resume(data)
            }
            .addOnFailureListener {
                cont.resume(null)
            }
    }
}