package com.nonofce.android.data.repository

import com.nonofce.android.data.source.LocalDataSource
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
}