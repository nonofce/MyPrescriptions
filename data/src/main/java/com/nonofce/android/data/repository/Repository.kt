package com.nonofce.android.data.repository

import com.nonofce.android.data.source.LocalDataSource
import com.nonofce.android.domain.Medication
import com.nonofce.android.domain.Prescription

class Repository(private val localDataSource: LocalDataSource) {

    suspend fun loadAllPrescriptions(): List<Prescription> {
        return localDataSource.loadAllPrescriptions()
    }

    suspend fun addPrescription(prescription: Prescription) {
        localDataSource.addPrescription(prescription)
    }

    suspend fun deletePrescription(prescriptionId: String) {
        localDataSource.deletePrescription(prescriptionId)
    }

    suspend fun updatePrescription(prescription: Prescription) {
        localDataSource.updatePrescription(prescription)
    }

    suspend fun getMedicationList(prescriptionId: String) =
        localDataSource.loadAllMedications(prescriptionId)

    suspend fun addNewMedication(medication: Medication){
        localDataSource.addNewMedication(medication)
    }

    suspend fun updateMedication(medication: Medication){
        localDataSource.updateMedication(medication)
    }

    suspend fun deleteMedication(id:String, prescriptionId: String){
        localDataSource.deleteMedication(id, prescriptionId)
    }
}