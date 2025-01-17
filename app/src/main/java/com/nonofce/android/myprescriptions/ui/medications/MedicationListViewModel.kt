package com.nonofce.android.myprescriptions.ui.medications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nonofce.android.domain.Medication
import com.nonofce.android.myprescriptions.common.Event
import com.nonofce.android.myprescriptions.common.Operations
import com.nonofce.android.myprescriptions.common.Operations.ADD_MEDICATION
import com.nonofce.android.myprescriptions.common.Operations.UPDATE_MEDICATION
import com.nonofce.android.usecases.medication.DeleteMedication
import com.nonofce.android.usecases.medication.LoadMedication
import kotlinx.coroutines.launch

class MedicationListViewModel(
    private val loadMedication: LoadMedication,
    private val deleteMedication: DeleteMedication
) : ViewModel() {

    private var _navigation = MutableLiveData<Event<Pair<Medication, Operations>>>()
    val navigation: LiveData<Event<Pair<Medication, Operations>>>
        get() = _navigation


    sealed class UiModel {
        class MedicationLoaded(val medications: List<Medication>) : UiModel()
        class DeleteMedication(val medicationEvent: Event<Medication>) : UiModel()
    }

    private var _uiModel = MutableLiveData<UiModel>()
    val uiModel: LiveData<UiModel>
        get() = _uiModel

    fun loadmedications(prescriptionId: String) {
        viewModelScope.launch {
            val result: List<Medication> = loadMedication.execute(prescriptionId)
            _uiModel.value = UiModel.MedicationLoaded(result)
        }
    }

    fun addNewMedication(prescriptionId: String) {
        _navigation.value =
            Event(
                Medication(
                    "", prescriptionId, "", "", "",
                    "PO", "Q4H", "", "", ""
                ) to ADD_MEDICATION
            )
    }

    fun onSelectMedication(medication: Medication) {
        println("onSelectMedication ${medication.id}")
    }

    fun onDeleteMedication(medication: Medication) {
        _uiModel.value = UiModel.DeleteMedication(Event(medication))
    }

    fun deleteMedication(medication: Medication) {
        viewModelScope.launch {
            deleteMedication.execute(medication)
            val result = loadMedication.execute(medication.prescriptionId)
            _uiModel.value = UiModel.MedicationLoaded(result)
        }
    }

    fun onEditMedication(medication: Medication) {
        _navigation.value = Event(medication to UPDATE_MEDICATION)
    }


}