package com.nonofce.android.myprescriptions.ui.prescription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nonofce.android.myprescriptions.Application
import com.nonofce.android.myprescriptions.common.EventObserver
import com.nonofce.android.myprescriptions.common.getApp
import com.nonofce.android.myprescriptions.common.getViewModel
import com.nonofce.android.myprescriptions.databinding.FragmentPrescriptionBinding

class Prescription : Fragment() {

    private lateinit var component: PrescriptionComponent
    private val viewModel by lazy { getViewModel { component.viewModel } }
    private lateinit var binding: FragmentPrescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component = getApp<Application>().appComponent.plus(PrescriptionModule())

        viewModel.uiModel.observe(viewLifecycleOwner, Observer(::updateUi))

        viewModel.navigation.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(this.context, "vamos a crear una nueva prescripcion", Toast.LENGTH_LONG)
                .show()
        })

        viewModel.loadPrescriptions()

        binding.addPrescriptionButton.setOnClickListener {
            viewModel.navigateToNewPrescription()
        }
    }

    fun updateUi(uiModel: PrescriptionViewModel.UiModel) {
        when (uiModel) {
            is PrescriptionViewModel.UiModel.Loading -> {
                Toast.makeText(this.context, "123", Toast.LENGTH_LONG).show()
            }
        }
    }

}
