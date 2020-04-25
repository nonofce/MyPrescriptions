package com.nonofce.android.myprescriptions.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nonofce.android.myprescriptions.model.Prescription

@Database(entities = [Prescription::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun prescriptionDao(): PrescriptionDao
}