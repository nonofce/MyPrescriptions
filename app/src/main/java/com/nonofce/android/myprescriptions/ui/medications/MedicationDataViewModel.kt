package com.nonofce.android.myprescriptions.ui.medications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nonofce.android.domain.Medication
import com.nonofce.android.myprescriptions.common.Operations
import com.nonofce.android.myprescriptions.common.Operations.ADD_MEDICATION
import com.nonofce.android.myprescriptions.common.Operations.UPDATE_MEDICATION
import com.nonofce.android.usecases.medication.AddMedication
import com.nonofce.android.usecases.medication.UpdateMedication
import kotlinx.coroutines.launch

class MedicationDataViewModel(
    private val addMedication: AddMedication,
    private val updateMedication: UpdateMedication
) : ViewModel() {

    enum class FIELD {
        NAME, STRENGTH, AMOUNT, STARTDATE, STARTTIME, HOWMUCH
    }

    sealed class UiModel {
        object MedicationRegisteredOK : UiModel()
        object MedicationUpdatedOk : UiModel()
        class InvalidData(val invalidFields: List<FIELD>) : UiModel()
    }

    private var _uiModel = MutableLiveData<UiModel>()
    val uiModel: LiveData<UiModel>
        get() = _uiModel

    var medication: Medication = Medication(
        "", "", "", "", "",
        "", "", "", "", ""
    )
        set(value) {
            with(value) {
                medicationName.value = name
                medicationStrength.value = strength
                medicationAmount.value = amount
                medicationRoute.value = route
                medicationFreq.value = frequency
                medicationStartDate.value = startDate
                medicationStartTime.value = startTime
                medicationHowMuch.value = howMuch
            }
            field = value
        }

    var medicationName = MutableLiveData<String>("")
    var medicationStrength = MutableLiveData<String>("")
    var medicationAmount = MutableLiveData<String>("")
    var medicationRoute = MutableLiveData<String>()
    var medicationFreq = MutableLiveData<String>()
    var medicationStartDate = MutableLiveData<String>()
    var medicationStartTime = MutableLiveData<String>()
    var medicationHowMuch = MutableLiveData<String>()

    private val requiredFields = listOf<Pair<FIELD, MutableLiveData<String>>>(
        FIELD.NAME to medicationName,
        FIELD.STRENGTH to medicationStrength,
        FIELD.AMOUNT to medicationAmount,
        FIELD.STARTDATE to medicationStartDate,
        FIELD.STARTTIME to medicationStartTime,
        FIELD.HOWMUCH to medicationHowMuch
    )

    fun processMedication(operation: Operations, startDate: String) {

        val medication = medication.copy(
            name = medicationName.value!!,
            strength = medicationStrength.value!!,
            amount = medicationAmount.value!!,
            route = medicationRoute.value!!,
            frequency = medicationFreq.value!!,
            startDate = startDate,
            startTime = medicationStartTime.value!!,
            howMuch = medicationHowMuch.value!!
        )
        val infalidFields = validateData()
        if (infalidFields.isEmpty()) {
            viewModelScope.launch {
                when (operation) {
                    ADD_MEDICATION -> {
                        addMedication.execute(medication)
                        resetUI()
                        _uiModel.value = UiModel.MedicationRegisteredOK
                    }
                    UPDATE_MEDICATION -> {
                        updateMedication.execute(medication)
                        _uiModel.value = UiModel.MedicationUpdatedOk
                    }
                    else -> {

                    }
                }
            }
        } else {
            _uiModel.value = UiModel.InvalidData(infalidFields)
        }
    }

    private fun resetUI() {
        medicationName.value = ""
        medicationStrength.value = ""
        medicationAmount.value = ""
        medicationRoute.value = "PO"
        medicationFreq.value = "Q4H"
        medicationStartDate.value = ""
        medicationStartTime.value = ""
        medicationHowMuch.value = ""
    }

    private fun validateData(): List<FIELD> {
        val invalidFields = mutableListOf<FIELD>()
        requiredFields.forEach {
            val (field, data) = it
            if (data.value == "") {
                invalidFields.add(field)
            }
        }
        return invalidFields
    }
}