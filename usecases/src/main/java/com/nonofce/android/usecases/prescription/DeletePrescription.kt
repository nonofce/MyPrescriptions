package com.nonofce.android.usecases.prescription

import com.nonofce.android.data.repository.Repository

class DeletePrescription(private val repository: Repository) {

    suspend fun execute(prescriptionId: String){
        repository.deletePrescription(prescriptionId)
    }
}