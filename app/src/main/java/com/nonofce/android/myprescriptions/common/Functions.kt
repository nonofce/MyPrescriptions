package com.nonofce.android.myprescriptions.common

import com.google.android.material.textfield.TextInputLayout
import java.util.*

fun updateTextControlWithTimeFromPicker(time: Long, control: TextInputLayout) {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time + ONE_DAY
    control.editText?.setText(userFormatter.format(calendar.time))
}

fun transformDateFormat(control: TextInputLayout): String {
    val rawDate = control.editText?.text.toString()
    val formattedDate = if (!rawDate.isBlank()) dbFormatter.format(
        userFormatter.parse(control.editText?.text.toString())
    ) else ""
    return formattedDate
}