package com.nonofce.android.myprescriptions.ui.medications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nonofce.android.myprescriptions.Application
import com.nonofce.android.myprescriptions.R
import com.nonofce.android.myprescriptions.common.EventObserver
import com.nonofce.android.myprescriptions.common.Operations
import com.nonofce.android.myprescriptions.common.getApp
import com.nonofce.android.myprescriptions.common.getViewModel
import com.nonofce.android.myprescriptions.databinding.FragmentMedicationListBinding
import com.nonofce.android.myprescriptions.model.toLocal
import com.nonofce.android.myprescriptions.ui.medications.MedicationListViewModel.UiModel.EditMedication
import com.nonofce.android.myprescriptions.ui.medications.MedicationListViewModel.UiModel.MedicationLoaded
import kotlinx.android.synthetic.main.fragment_medication_list.*
import java.util.*

class MedicationList : Fragment() {

    private lateinit var binding: FragmentMedicationListBinding
    private val args: MedicationListArgs by navArgs<MedicationListArgs>()
    private lateinit var component: MedicationListComponent
    private val viewModel by lazy { getViewModel { component.viewModel } }
    private lateinit var navController: NavController
    private lateinit var adapter: MedicationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        component = getApp<Application>().appComponent.plus(MedicationListModule())

        navController = view.findNavController()
        val (prescription, operationArg) = args
        with(binding) {
            medicationListHeader.text = prescription.who
            addMedicationButton.setOnClickListener {
                viewModel.addNewMedication(prescription.id)
            }
        }

        adapter = MedicationAdapter(
            viewModel::onSelectMedication,
            viewModel::onEditMedication,
            viewModel::onDeleteMedication
        )
        medicationsList.adapter = adapter

        with(viewModel) {

            loadmedications(prescription.id)

            uiModel.observe(viewLifecycleOwner, Observer(::updateUi))

            navigation.observe(viewLifecycleOwner, EventObserver {
                val (medication, operation) = it
                var direction: NavDirections? = null
                when (operation) {
                    Operations.ADD_MEDICATION -> {
                        direction = MedicationListDirections.fromMedicationListToMedicationData(
                            medication.copy(id = UUID.randomUUID().toString()).toLocal(),
                            operation
                        )

                        navController.graph.findNode(R.id.medicationData)?.let {
                            it.label = getString(R.string.add_medication_label)
                        }
                    }
                    else -> {
                        // Nothing to do, all flows are covered
                    }
                }
                direction?.let {
                    navController.navigate(direction)
                }
            })
        }
    }

    private fun updateUi(uiModel: MedicationListViewModel.UiModel) {
        when (uiModel) {
            is MedicationLoaded -> {
                adapter.items = uiModel.medications
            }
            is EditMedication -> {
                uiModel.medicationEvent.getContentIfNotHandled()?.let { medication ->
                    MaterialAlertDialogBuilder(context).setMessage(getString(R.string.medication_delete_confirmation))
                        .setTitle(getString(R.string.confirmation))
                        .setNeutralButton(
                            getString(
                                R.string.cancel_button
                            )
                        ) { _, _ ->

                        }.setPositiveButton(getString(R.string.ok_button)) { _, _ ->
                            viewModel.deleteMedication(medication)
                        }.show()
                }
            }
        }
    }

}
