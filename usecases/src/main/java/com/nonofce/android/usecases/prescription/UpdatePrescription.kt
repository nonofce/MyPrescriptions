package com.nonofce.android.usecases.prescription

import com.nonofce.android.data.repository.Repository
import com.nonofce.android.domain.Prescription

class UpdatePrescription(private val repository: Repository) {

    suspend fun execute(prescription: Prescription) {
        repository.updatePrescription(prescription)
    }
}