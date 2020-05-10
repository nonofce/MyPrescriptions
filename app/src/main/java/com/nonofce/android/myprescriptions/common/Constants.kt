package com.nonofce.android.myprescriptions.common

import java.text.SimpleDateFormat
import java.util.*

const val ONE_DAY: Long = 60 * 60 * 24 * 1000

val dbFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
val userFormatter = SimpleDateFormat("dd-MMMM-yyyy", Locale.US)