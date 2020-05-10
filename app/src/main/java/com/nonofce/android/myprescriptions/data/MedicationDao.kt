package com.nonofce.android.myprescriptions.data

import androidx.room.*
import com.nonofce.android.myprescriptions.model.Medication as ModelMedication

@Dao
interface MedicationDao {

    @Query("SELECT * FROM Medication where prescriptionId = :prescriptionId")
    suspend fun getMedicationList(prescriptionId: String): List<ModelMedication>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewMedication(medication: ModelMedication)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateMedication(medication: ModelMedication)

    @Query("DELETE FROM Medication WHERE id = :id AND prescriptionId = :prescriptionId")
    suspend fun deleteMedication(id: String, prescriptionId: String)
}