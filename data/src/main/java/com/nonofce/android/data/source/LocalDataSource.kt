package com.nonofce.android.data.source

import com.nonofce.android.domain.Prescription

interface LocalDataSource {

    suspend fun addPrescription(prescription: Prescription)
}