package com.nonofce.android.myprescriptions.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nonofce.android.domain.Prescription
import com.nonofce.android.myprescriptions.model.Prescription as ModelPrescription

@Dao
interface PrescriptionDao {

    @Query("SELECT * FROM Prescription ORDER BY date DESC")
    fun selectAllPrescriptions(): List<Prescription>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNewPrescription(prescription: ModelPrescription)

    @Query("DELETE FROM Prescription WHERE id = :prescriptionId")
    fun deletePrescription(prescriptionId: String)
}