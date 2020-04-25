package com.nonofce.android.myprescriptions.data

import com.nonofce.android.data.source.LocalDataSource
import com.nonofce.android.domain.Prescription
import com.nonofce.android.myprescriptions.model.toNewLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(private val database: LocalDatabase) : LocalDataSource {

    override suspend fun addPrescription(prescription: Prescription) {
        withContext(Dispatchers.IO) {
            database.prescriptionDao().addNewPrescription(prescription.toNewLocal())
        }
    }
}