package com.nonofce.android.myprescriptions.data

import com.nonofce.android.data.source.LocalDataSource
import com.nonofce.android.domain.Prescription
import com.nonofce.android.myprescriptions.model.toDomain
import com.nonofce.android.myprescriptions.model.toLocal
import com.nonofce.android.myprescriptions.model.toNewLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(private val database: LocalDatabase) : LocalDataSource {
    override suspend fun loadAllPrescriptions(): List<Prescription> =
        database.prescriptionDao().selectAllPrescriptions().map { it.toDomain()
    }

    override suspend fun addPrescription(prescription: Prescription) {
        database.prescriptionDao().addNewPrescription(prescription.toNewLocal())
    }

    override suspend fun deletePrescription(prescriptionId: String) {
        database.prescriptionDao().deletePrescription(prescriptionId)
    }

    override suspend fun updatePrescription(prescription: Prescription) {
        database.prescriptionDao().updatePrescription(prescription.toLocal())
    }
}