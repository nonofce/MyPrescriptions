package com.nonofce.android.myprescriptions.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nonofce.android.myprescriptions.model.Prescription as ModelPrescription

@Dao
interface PrescriptionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNewPrescription(prescription: ModelPrescription)
}