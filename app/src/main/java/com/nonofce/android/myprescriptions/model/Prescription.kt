package com.nonofce.android.myprescriptions.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*
import com.nonofce.android.domain.Prescription as DomainPrescription

@Entity
@Parcelize
data class Prescription(
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString(),
    val who: String = "",
    val where: String = "",
    val date: String = ""
) : Parcelable

fun Prescription.toDomain(): DomainPrescription = DomainPrescription(id, who, where, date)

fun DomainPrescription.toNewLocal(): Prescription =
    Prescription(UUID.randomUUID().toString(), who, where, date)

fun DomainPrescription.toLocal(): Prescription =
    Prescription(id, who, where, date)