package com.nonofce.android.myprescriptions.ui.prescription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.nonofce.android.myprescriptions.Application
import com.nonofce.android.myprescriptions.R
import com.nonofce.android.myprescriptions.common.DataOperations
import com.nonofce.android.myprescriptions.common.DataOperations.*
import com.nonofce.android.myprescriptions.common.EventObserver
import com.nonofce.android.myprescriptions.common.getApp
import com.nonofce.android.myprescriptions.common.getViewModel
import com.nonofce.android.myprescriptions.databinding.FragmentPrescriptionListBinding
import com.nonofce.android.myprescriptions.model.Prescription

class PrescriptionList : Fragment() {

    private lateinit var navController: NavController
    private lateinit var component: PrescriptionListComponent
    private val viewModel by lazy { getViewModel { component.viewModel } }
    private lateinit var binding: FragmentPrescriptionListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrescriptionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        component = getApp<Application>().appComponent.plus(PrescriptionListModule())

        viewModel.uiModel.observe(viewLifecycleOwner, Observer(::updateUi))

        viewModel.navigation.observe(viewLifecycleOwner, EventObserver {
            val action = PrescriptionListDirections.fromPrescriptionListToPrescriptionData(
                Prescription(), ADD
            )
            navController.graph.findNode(R.id.prescriptionData)?.let {
                it.label = getString(R.string.new_prescription_data)
            }
            navController.navigate(action)
        })

        viewModel.loadPrescriptions()

        binding.addPrescriptionButton.setOnClickListener {
            viewModel.navigateToNewPrescription()
        }
    }

    fun updateUi(uiModel: PrescriptionListViewModel.UiModel) {
        when (uiModel) {
            is PrescriptionListViewModel.UiModel.Loading -> {
                Toast.makeText(this.context, "123", Toast.LENGTH_LONG).show()
            }
        }
    }

}
