package com.nonofce.android.myprescriptions.ui.prescription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nonofce.android.myprescriptions.Application
import com.nonofce.android.myprescriptions.R
import com.nonofce.android.myprescriptions.common.DataOperations.ADD
import com.nonofce.android.myprescriptions.common.DataOperations.UPDATE
import com.nonofce.android.myprescriptions.common.EventObserver
import com.nonofce.android.myprescriptions.common.getApp
import com.nonofce.android.myprescriptions.common.getViewModel
import com.nonofce.android.myprescriptions.databinding.FragmentPrescriptionListBinding
import com.nonofce.android.myprescriptions.model.Prescription
import com.nonofce.android.myprescriptions.model.toLocal
import com.nonofce.android.myprescriptions.model.toNewLocal
import com.nonofce.android.myprescriptions.ui.prescription.PrescriptionListViewModel.UiModel
import com.nonofce.android.myprescriptions.ui.prescription.PrescriptionListViewModel.UiModel.*
import kotlinx.android.synthetic.main.fragment_prescription_list.*

class PrescriptionList : Fragment() {

    private lateinit var navController: NavController
    private lateinit var component: PrescriptionListComponent
    private val viewModel by lazy { getViewModel { component.viewModel } }
    private lateinit var binding: FragmentPrescriptionListBinding
    private lateinit var adapter: PrescriptionAdapter

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
            val (operation, prescription) = it
            var action:NavDirections? = null
            when (operation) {
                ADD -> {
                    action = PrescriptionListDirections.fromPrescriptionListToPrescriptionData(
                        prescription.toNewLocal(), operation
                    )
                    navController.graph.findNode(R.id.prescriptionData)?.let {
                        it.label = getString(R.string.new_prescription_data)
                    }
                }
                UPDATE -> {

                    action = PrescriptionListDirections.fromPrescriptionListToPrescriptionData(
                        prescription.toLocal(), operation
                    )
                    navController.graph.findNode(R.id.prescriptionData)?.let {
                        it.label = getString(R.string.edit_prescription_data)
                    }
                }
                else -> {
                    // Deletion are processed on another flow
                }
            }
            action?.let {
                navController.navigate(it)
            }

        })

        viewModel.loadPrescriptions()

        with(binding) {
            addPrescriptionButton.setOnClickListener {
                viewModel.navigateToNewPrescription()
            }
        }

        adapter = PrescriptionAdapter(
            viewModel::onPrescriptionSelect,
            viewModel::onPrescriptionEdit,
            viewModel::onPrescriptionDelete
        )
        prescriptionsList.adapter = adapter
    }

    fun updateUi(uiModel: UiModel) {
        when (uiModel) {
            is StartLoading -> {
                binding.prescriptionListProgressBar.visibility = View.VISIBLE
            }
            is EndLoading -> {
                binding.prescriptionListProgressBar.visibility = View.GONE
            }
            is PrescriptionsLoaded -> {
                adapter.items = uiModel.prescriptions
            }
            is DeletePrescription -> {
                uiModel.event.getContentIfNotHandled()?.let { prescription ->
                    MaterialAlertDialogBuilder(context).setMessage(getString(R.string.prescription_delete_confirmation))
                        .setTitle(getString(R.string.confirmation))
                        .setNeutralButton(
                            getString(
                                R.string.cancel_button
                            )
                        ) { _, _ ->

                        }.setPositiveButton(getString(R.string.ok_button)) { _, _ ->
                            viewModel.deletePrescription(prescription)
                        }.show()
                }
            }
        }
    }

}
