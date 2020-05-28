package com.nonofce.android.myprescriptions.ui.prescription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nonofce.android.domain.Prescription
import com.nonofce.android.myprescriptions.common.Operations
import com.nonofce.android.myprescriptions.common.Operations.*
import com.nonofce.android.myprescriptions.common.Event
import com.nonofce.android.usecases.prescription.DeletePrescription
import com.nonofce.android.usecases.prescription.LoadPrescriptions
import kotlinx.coroutines.launch

class PrescriptionListViewModel(
    private val loadPrescriptions: LoadPrescriptions,
    private val deletePrescription: DeletePrescription
) : ViewModel() {

    private val _uiModel = MutableLiveData<UiModel>()
    val uiModel: LiveData<UiModel>
        get() = _uiModel

    private val _navigation = MutableLiveData<Event<Pair<Operations, Prescription>>>()
    val navigation: LiveData<Event<Pair<Operations, Prescription>>>
        get() = _navigation

    private var _progressVisibility = MutableLiveData<Boolean>()
    val progressVisibility: LiveData<Boolean>
        get() = _progressVisibility

    sealed class UiModel {
        object StartLoading : UiModel()
        object EndLoading : UiModel()
        class PrescriptionsLoaded(val prescriptions: List<Prescription>) : UiModel()
        class DeletePrescription(val event: Event<Prescription>) : UiModel()
    }

    init {
        _progressVisibility.value = false
    }

    fun loadPrescriptions() {
        viewModelScope.launch {
            _uiModel.value = UiModel.StartLoading
            val result = loadPrescriptions.execute()
            _uiModel.value = UiModel.PrescriptionsLoaded(result)
            _uiModel.value = UiModel.EndLoading
        }

    }

    fun onPrescriptionDelete(prescription: Prescription) {
        _uiModel.value = UiModel.DeletePrescription(Event((prescription)))
    }

    fun deletePrescription(prescription: Prescription) {
        viewModelScope.launch {
            deletePrescription.execute(prescription.id)
            val result = loadPrescriptions.execute()
            _uiModel.value = UiModel.PrescriptionsLoaded(result)
        }
    }

    fun onPrescriptionEdit(prescription: Prescription) {
        _navigation.value = Event(UPDATE_PRESCRIPTION to prescription)
    }

    fun onPrescriptionSelect(prescription: Prescription) {
        _navigation.value = Event(LOAD_MEDICATION_LIST to prescription)
    }

    fun navigateToNewPrescription() {
        _navigation.value = Event(ADD_PRESCRIPTION to Prescription("", "", "", ""))
    }
}