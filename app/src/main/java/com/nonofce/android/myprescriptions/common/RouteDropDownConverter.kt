package com.nonofce.android.myprescriptions.common

import android.content.Context
import androidx.databinding.InverseMethod
import com.nonofce.android.myprescriptions.R


object RouteDropDownConverter {

    fun fromValueToLabel(context: Context, value: String): String {
        val routes_codes = context.resources.getStringArray(R.array.medication_route_code)
        val route_label = context.resources.getStringArray(R.array.medication_route_name)

        routes_codes.forEachIndexed { index, s ->
            if (s == value) {
                return route_label[index]
            }
        }
        return ""
    }

    @InverseMethod(value = "fromValueToLabel")
    fun fromLabelToValue(context: Context, label: String): String {
        val routes_codes = context.resources.getStringArray(R.array.medication_route_code)
        val route_label = context.resources.getStringArray(R.array.medication_route_name)

        route_label.forEachIndexed { index, s ->
            if (s == label) {
                return routes_codes[index]
            }
        }
        return ""
    }
}