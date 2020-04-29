package com.nonofce.android.myprescriptions.ui.prescription

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nonofce.android.domain.Prescription
import com.nonofce.android.myprescriptions.common.DataOperations.ADD
import com.nonofce.android.usecases.AddNewPrescription
import kotlinx.coroutines.launch
import java.util.*

class PrescriptionDataViewModel(private val addNewPrescription: AddNewPrescription) : ViewModel() {

    enum class FIELD {
        WHO, WHERE, WHEN
    }

    var who = MutableLiveData<String>("")
    var where = MutableLiveData<String>("")
    var moment = MutableLiveData<String>("")

    var dataOperation = ADD

    var prescription: Prescription = Prescription("", "", "", "")
        set(value) {
            who.value = value.who
            where.value = value.where
            moment.value = value.date
            field = value
        }

    sealed class UiModel {
        object PrescriptionRegisteredOk : UiModel()
        class InvalidData(val fields: List<FIELD>) : UiModel()
    }

    private var _uiModel = MutableLiveData<UiModel>()
    val uiModel: LiveData<UiModel>
        get() = _uiModel

    fun processPrescription(date: String) {
        val invalidFields = getInvalidFields()
        if (invalidFields.isEmpty()) {
            when (dataOperation) {
                ADD -> {
                    var copy = prescription.copy(
                        id = UUID.randomUUID().toString(),
                        who = who.value!!, where = where.value!!, date = date
                    )
                    addNewPrescription(copy)
                }
            }
        } else {
            _uiModel.value = UiModel.InvalidData(invalidFields)
        }
    }

    private fun getInvalidFields(): List<FIELD> {
        val invalidFields = mutableListOf<FIELD>()
        if (who.value.isNullOrBlank()) {
            invalidFields.add(FIELD.WHO)
        }
        if (where.value.isNullOrBlank()) {
            invalidFields.add(FIELD.WHERE)
        }
        if (moment.value.isNullOrBlank()) {
            invalidFields.add(FIELD.WHEN)
        }
        return invalidFields
    }

    private fun addNewPrescription(prescription: Prescription) {
        viewModelScope.launch {
            addNewPrescription.execute(prescription)
            resetUi()
            notifyPrescriptionRegistered()
        }
    }

    private fun resetUi() {
        who.value = ""
        where.value = ""
        moment.value = ""
    }

    private fun notifyPrescriptionRegistered() {
        _uiModel.value = UiModel.PrescriptionRegisteredOk
    }

}
