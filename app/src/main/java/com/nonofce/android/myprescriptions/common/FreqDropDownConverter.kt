package com.nonofce.android.myprescriptions.common

import android.content.Context
import androidx.databinding.InverseMethod
import com.nonofce.android.myprescriptions.R


object FreqDropDownConverter {

    fun fromValueToLabel(context: Context, value: String): String {
        val freq_codes = context.resources.getStringArray(R.array.medication_freq_code)
        val freq_label = context.resources.getStringArray(R.array.medication_freq_name)

        freq_codes.forEachIndexed { index, s ->
            if (s == value) {
                return freq_label[index]
            }
        }
        return ""
    }

    @InverseMethod(value = "fromValueToLabel")
    fun fromLabelToValue(context: Context, label: String): String {
        val freq_codes = context.resources.getStringArray(R.array.medication_freq_code)
        val freq_label = context.resources.getStringArray(R.array.medication_freq_name)

        freq_label.forEachIndexed { index, s ->
            if (s == label) {
                return freq_codes[index]
            }
        }
        return ""
    }
}