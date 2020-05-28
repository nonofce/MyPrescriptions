package com.nonofce.android.usecases.medication

import com.nonofce.android.data.repository.Repository
import com.nonofce.android.domain.Medication

class LoadMedication(private val repository: Repository) {

    suspend fun execute(prescriptionId: String): List<Medication> {
        return repository.getMedicationList(prescriptionId)
    }
}