package com.nonofce.android.data.source

import com.nonofce.android.domain.Prescription

interface LocalDataSource {

    suspend fun loadAllPrescriptions(): List<Prescription>
    suspend fun addPrescription(prescription: Prescription)
    suspend fun deletePrescription(prescriptionId: String)
}