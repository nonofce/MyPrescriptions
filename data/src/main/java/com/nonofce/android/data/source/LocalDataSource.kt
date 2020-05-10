package com.nonofce.android.data.source

import com.nonofce.android.domain.Medication
import com.nonofce.android.domain.Prescription

interface LocalDataSource {

    suspend fun loadAllPrescriptions(): List<Prescription>
    suspend fun addPrescription(prescription: Prescription)
    suspend fun deletePrescription(prescriptionId: String)
    suspend fun updatePrescription(prescription: Prescription)

    suspend fun loadAllMedications(prescriptionId: String): List<Medication>
    suspend fun addNewMedication(medication: Medication)
    suspend fun updateMedication(medication: Medication)
    suspend fun deleteMedication(id: String, prescriptionId: String)
}