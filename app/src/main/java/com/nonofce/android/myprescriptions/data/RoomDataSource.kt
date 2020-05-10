package com.nonofce.android.myprescriptions.data

import com.nonofce.android.data.source.LocalDataSource
import com.nonofce.android.domain.Medication
import com.nonofce.android.domain.Prescription
import com.nonofce.android.myprescriptions.model.toDomain
import com.nonofce.android.myprescriptions.model.toLocal
import com.nonofce.android.myprescriptions.model.toNewLocal

class RoomDataSource(private val database: LocalDatabase) : LocalDataSource {
    override suspend fun loadAllPrescriptions(): List<Prescription> =
        database.prescriptionDao().selectAllPrescriptions().map {
            it.toDomain()
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

    override suspend fun loadAllMedications(prescriptionId: String): List<Medication> =
        database.medicationDao().getMedicationList(prescriptionId).map { it.toDomain() }

    override suspend fun addNewMedication(medication: Medication) {
        database.medicationDao().addNewMedication(medication.toNewLocal())
    }

    override suspend fun updateMedication(medication: Medication) {
        database.medicationDao().updateMedication(medication.toLocal())
    }

    override suspend fun deleteMedication(id: String, prescriptionId: String) {
        database.medicationDao().deleteMedication(id, prescriptionId)
    }
}