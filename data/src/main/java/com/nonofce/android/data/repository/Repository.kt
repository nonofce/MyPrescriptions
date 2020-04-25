package com.nonofce.android.data.repository

import com.nonofce.android.data.source.LocalDataSource
import com.nonofce.android.domain.Prescription

class Repository(private val localDataSource: LocalDataSource) {

    suspend fun addPrescription(prescription: Prescription){
        localDataSource.addPrescription(prescription)
    }
}