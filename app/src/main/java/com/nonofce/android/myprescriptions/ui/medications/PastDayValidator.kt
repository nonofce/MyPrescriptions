package com.nonofce.android.myprescriptions.ui.medications

import android.os.Parcel
import android.os.Parcelable
import com.google.android.material.datepicker.CalendarConstraints
import com.nonofce.android.myprescriptions.common.ONE_DAY
import java.util.*

class PastDayValidator() : CalendarConstraints.DateValidator {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
    }

    override fun isValid(date: Long): Boolean {
        val calendar = Calendar.getInstance()
        return calendar.timeInMillis - ONE_DAY < date
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PastDayValidator> {
        override fun createFromParcel(parcel: Parcel): PastDayValidator {
            return PastDayValidator(parcel)
        }

        override fun newArray(size: Int): Array<PastDayValidator?> {
            return arrayOfNulls(size)
        }
    }
}