package com.nonofce.android.domain

data class Medication(
    val id: String,
    val prescriptionId: String,
    val name: String,
    val strength: String,
    val amount: String,
    val route: String,
    val frequency: String,
    val startDate: String,
    val startTime: String,
    val howMuch: String
) {
}