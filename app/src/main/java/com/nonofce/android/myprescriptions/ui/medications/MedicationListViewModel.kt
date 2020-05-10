package com.nonofce.android.myprescriptions.ui.medications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nonofce.android.domain.Medication
import com.nonofce.android.myprescriptions.common.Event
import com.nonofce.android.myprescriptions.common.Operations
import com.nonofce.android.myprescriptions.common.Operations.ADD_MEDICATION

class MedicationListViewModel : ViewModel() {

    private var _navigation = MutableLiveData<Event<Pair<Medication, Operations>>>()
    val navigation: LiveData<Event<Pair<Medication, Operations>>>
        get() = _navigation


    fun addNewMedication(prescriptionId: String) {
        _navigation.value =
            Event(
                Medication(
                    "", prescriptionId, "", "", "",
                    "PO", "Q4H", "", "", ""
                ) to ADD_MEDICATION
            )

    }
}