package com.nonofce.android.myprescriptions.ui.medications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nonofce.android.domain.Medication
import com.nonofce.android.myprescriptions.common.Event
import com.nonofce.android.myprescriptions.common.Operations
import com.nonofce.android.myprescriptions.common.Operations.ADD_MEDICATION
import com.nonofce.android.usecases.medication.LoadMedication
import kotlinx.coroutines.launch

class MedicationListViewModel(private val loadMedication: LoadMedication) : ViewModel() {

    private var _navigation = MutableLiveData<Event<Pair<Medication, Operations>>>()
    val navigation: LiveData<Event<Pair<Medication, Operations>>>
        get() = _navigation


    sealed class UiModel{
        class MedicationLoaded(val medications:List<Medication>): UiModel()
    }

    private var _uiModel = MutableLiveData<UiModel>()
    val uiModel:LiveData<UiModel>
    get() = _uiModel

    fun loadmedications(prescriptionId: String){
        viewModelScope.launch {
            val result:List<Medication> = loadMedication.execute(prescriptionId)
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

    fun onSelectMedication(medication:Medication){
        println("onSelectMedication ${medication.id}")
    }

    fun onDeleteMedication(medication: Medication){
        println("onDeleteMedication ${medication.id}")
    }

    fun onEditMedication(medication: Medication){
        println("onEditMedication ${medication.id}")
    }


}