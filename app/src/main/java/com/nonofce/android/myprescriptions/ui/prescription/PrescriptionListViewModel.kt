package com.nonofce.android.myprescriptions.ui.prescription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nonofce.android.myprescriptions.common.Event

class PrescriptionListViewModel : ViewModel() {

    private val _uiModel = MutableLiveData<UiModel>()
    val uiModel: LiveData<UiModel>
        get() = _uiModel

    private val _navigation = MutableLiveData<Event<Boolean>>()
    val navigation: LiveData<Event<Boolean>>
        get() = _navigation

    sealed class UiModel {
        object Loading : UiModel()
    }

    fun loadPrescriptions() {

        _uiModel.value = UiModel.Loading
    }

    fun navigateToNewPrescription() {
        _navigation.value = Event(true)
    }
}