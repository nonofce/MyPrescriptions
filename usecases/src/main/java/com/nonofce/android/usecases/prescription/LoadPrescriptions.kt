package com.nonofce.android.usecases.prescription

import com.nonofce.android.data.repository.Repository
import com.nonofce.android.domain.Prescription

class LoadPrescriptions(private val repository: Repository) {

    suspend fun execute(): List<Prescription> {
        return repository.loadAllPrescriptions()
    }
}