package com.nonofce.android.myprescriptions.data

import androidx.room.*
import com.nonofce.android.myprescriptions.model.Prescription as ModelPrescription

@Dao
interface PrescriptionDao {

    @Query("SELECT * FROM Prescription ORDER BY date DESC")
    suspend fun selectAllPrescriptions(): List<ModelPrescription>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewPrescription(prescription: ModelPrescription)

    @Query("DELETE FROM Prescription WHERE id = :prescriptionId")
    suspend fun deletePrescription(prescriptionId: String)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePrescription(prescription: ModelPrescription)

}