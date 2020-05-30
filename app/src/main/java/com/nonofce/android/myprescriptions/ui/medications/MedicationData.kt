package com.nonofce.android.myprescriptions.ui.medications

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.nonofce.android.myprescriptions.Application
import com.nonofce.android.myprescriptions.R
import com.nonofce.android.myprescriptions.common.*
import com.nonofce.android.myprescriptions.databinding.FragmentMedicationDataBinding
import com.nonofce.android.myprescriptions.model.toDomain
import com.nonofce.android.myprescriptions.ui.medications.MedicationDataViewModel.FIELD
import com.nonofce.android.myprescriptions.ui.medications.MedicationDataViewModel.UiModel
import java.util.*

class MedicationData : Fragment() {

    private lateinit var binding: FragmentMedicationDataBinding
    private lateinit var component: MedicationDataComponent
    private val args: MedicationDataArgs by navArgs()

    private val viewModel by lazy { getViewModel { component.viewModel } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicationDataBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        component = getApp<Application>().appComponent.plus(MedicationDataModule())
        val (medicationArg, operation) = args

        val routes = resources.getStringArray(R.array.medication_route_name)
        val routesCode = resources.getStringArray(R.array.medication_route_code)
        val routeSelectorAdapter = MaterialSpinnerAdapter<String>(
            context!!,
            R.layout.dropdown_menu_popup_item,
            routes
        )
        val frequencies = resources.getStringArray(R.array.medication_freq_name)
        val frequenciesCode = resources.getStringArray(R.array.medication_freq_code)
        val frequencySelectorAdapter =
            MaterialSpinnerAdapter<String>(
                context!!,
                R.layout.dropdown_menu_popup_item,
                frequencies
            )


        with(binding) {

            vm = viewModel
            lifecycleOwner = this@MedicationData

            medicationRouteSelection.setAdapter(routeSelectorAdapter)
            medicationFreqSelection.setAdapter(frequencySelectorAdapter)

            medicationTimeButton.setOnClickListener {
                val calendar = Calendar.getInstance()
                TimePickerDialog(
                    context!!,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        medicationStartTime.editText?.setText("$hourOfDay:$minute")
                    }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false
                ).show()
            }

            medicationDateButton.setOnClickListener {
                val builder = MaterialDatePicker.Builder.datePicker()
                builder.setSelection(Calendar.getInstance().timeInMillis)

                val constraintBuilder = CalendarConstraints.Builder()
                constraintBuilder.setEnd(Calendar.getInstance().timeInMillis)
                constraintBuilder.setValidator(PastDayValidator())
                builder.setCalendarConstraints(constraintBuilder.build())

                val picker = builder.build()

                picker.addOnPositiveButtonClickListener {
                    updateTextControlWithTimeFromPicker(it, medicationStartDate)
                }
                picker.show(parentFragmentManager, picker.toString())
            }

            medicationDataDoneButton.setOnClickListener {
                viewModel.processMedication(operation, transformDateFormat(medicationStartDate))
            }
        }

        with(viewModel) {
            medicationRoute.value = routesCode[0]
            medicationFreq.value = frequenciesCode[0]

            medication =
                medicationArg.copy(startDate = userFormatter.format(dbFormatter.parse(medicationArg.startDate)))
                    .toDomain()

            uiModel.observe(viewLifecycleOwner, Observer(::updateUI))
        }
    }

    private fun updateUI(uiModel: UiModel) = when (uiModel) {
        is UiModel.InvalidData -> {
            val invalidInput =
                listOf<Pair<FIELD, TextInputLayout>>(
                    FIELD.NAME to binding.medicationName,
                    FIELD.STRENGTH to binding.medicationStrength,
                    FIELD.AMOUNT to binding.medicationAmount,
                    FIELD.STARTDATE to binding.medicationStartDate,
                    FIELD.STARTTIME to binding.medicationStartTime,
                    FIELD.HOWMUCH to binding.medicationHowMuch
                )

            uiModel.invalidFields.forEach { invalidField ->
                invalidInput.forEach { input ->
                    val (field, control) = input
                    if (invalidField == field) {
                        control.error = getString(R.string.mandatory_field)
                    }
                }
            }
        }
        is UiModel.MedicationRegisteredOK -> {
            Snackbar.make(
                binding.root,
                R.string.medication_registered_OK,
                Snackbar.LENGTH_SHORT
            ).showWithGravity()
            binding.medicationName.requestFocus()
            Unit
        }
        is UiModel.MedicationUpdatedOk -> {
            Snackbar.make(
                binding.root,
                R.string.medication_updated_OK,
                Snackbar.LENGTH_SHORT
            ).showWithGravity()
            binding.medicationName.requestFocus()
            Unit
        }
    }

}
