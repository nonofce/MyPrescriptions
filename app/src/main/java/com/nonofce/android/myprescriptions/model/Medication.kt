package com.nonofce.android.myprescriptions.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*
import com.nonofce.android.domain.Medication as DomainMedication

@Entity
@Parcelize
data class Medication(
    @PrimaryKey(autoGenerate = false) val id: String,
    val prescriptionId: String,
    val name: String,
    val strength: String,
    val amount: String,
    val route: String,
    val frequency: String,
    val startDate: String,
    val startTime: String,
    val howMuch: String
) : Parcelable {
}

fun Medication.toDomain() =
    DomainMedication(
        id,
        prescriptionId,
        name,
        strength,
        amount,
        route,
        frequency,
        startDate,
        startTime,
        howMuch
    )

fun DomainMedication.toNewLocal() = Medication(
    UUID.randomUUID().toString(),
    prescriptionId,
    name,
    strength,
    amount,
    route,
    frequency,
    startDate,
    startTime,
    howMuch
)

fun DomainMedication.toLocal() =
    Medication(
        id,
        prescriptionId,
        name,
        strength,
        amount,
        route,
        frequency,
        startDate,
        startTime,
        howMuch
    )
