package com.nonofce.android.usecases.medication

import com.nonofce.android.data.repository.Repository
import com.nonofce.android.domain.Medication

class AddMedication(private val repository: Repository) {

    suspend fun execute(medication:Medication){
        repository.addNewMedication(medication)
    }
}