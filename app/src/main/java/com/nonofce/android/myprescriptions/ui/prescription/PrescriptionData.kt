package com.nonofce.android.myprescriptions.ui.prescription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.nonofce.android.myprescriptions.Application
import com.nonofce.android.myprescriptions.R
import com.nonofce.android.myprescriptions.common.ONE_DAY
import com.nonofce.android.myprescriptions.common.getApp
import com.nonofce.android.myprescriptions.common.getViewModel
import com.nonofce.android.myprescriptions.common.showWithGravity
import com.nonofce.android.myprescriptions.databinding.FragmentPrescriptionDataBinding
import com.nonofce.android.myprescriptions.model.toDomain
import com.nonofce.android.myprescriptions.ui.prescription.PrescriptionDataViewModel.UiModel
import com.nonofce.android.myprescriptions.ui.prescription.PrescriptionDataViewModel.UiModel.*
import java.text.SimpleDateFormat
import java.util.*

class PrescriptionData : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentPrescriptionDataBinding
    private lateinit var component: PrescriptionDataComponent
    private val viewModel by lazy { getViewModel { component.viewModel } }
    private val args: PrescriptionDataArgs by navArgs<PrescriptionDataArgs>()

    private val userFormatter = SimpleDateFormat("dd-MMMM-yyyy")
    private val dbFormatter = SimpleDateFormat("yyyy-MM-dd")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrescriptionDataBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        component = getApp<Application>().appComponent.plus(PrescriptionDataModule())

        with(viewModel) {
            prescription = if (!args.prescription.date.isBlank()) {
                args.prescription.copy(date = userFormatter.format(dbFormatter.parse(args.prescription.date)))
                    .toDomain()
            } else args.prescription.toDomain()
            dataOperation = args.dataOperation
        }

        with(binding) {

            lifecycleOwner = this@PrescriptionData
            vm = viewModel

            prescriptionDateButton.setOnClickListener {
                val builder = MaterialDatePicker.Builder.datePicker()
                builder.setSelection(Calendar.getInstance().timeInMillis)

                val constraintBuilder = CalendarConstraints.Builder()
                constraintBuilder.setEnd(Calendar.getInstance().timeInMillis)
                constraintBuilder.setValidator(FutureDayValidator())
                builder.setCalendarConstraints(constraintBuilder.build())

                val picker = builder.build()

                picker.addOnPositiveButtonClickListener {
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = it + ONE_DAY
                    prescriptionDate.editText?.setText(userFormatter.format(calendar.time))
                }

                picker.show(parentFragmentManager, picker.toString())
            }

            prescriptionDataDoneButton.setOnClickListener {
                who.error = null
                where.error = null
                prescriptionDate.error = null
                val rawDate = prescriptionDate.editText?.text.toString()
                val formattedDate = if (!rawDate.isBlank()) dbFormatter.format(
                    userFormatter.parse(prescriptionDate.editText?.text.toString())
                ) else ""
                viewModel.processPrescription(formattedDate)
            }
        }

        viewModel.uiModel.observe(viewLifecycleOwner, Observer(::updateUi))
    }

    private fun updateUi(uiModel: UiModel) = when (uiModel) {
        is PrescriptionRegisteredOk -> {
            Snackbar.make(
                binding.root,
                R.string.prescription_registered_OK,
                Snackbar.LENGTH_SHORT
            ).showWithGravity()
            binding.who.requestFocus()
            Unit
        }
        is PrescriptionUpdatedOk -> {
            Snackbar.make(
                binding.root,
                R.string.prescription_updated_OK,
                Snackbar.LENGTH_SHORT
            ).showWithGravity()
            binding.who.requestFocus()
            Unit
        }
        is InvalidData -> {
            uiModel.fields.forEach {
                when (it) {
                    PrescriptionDataViewModel.FIELD.WHO -> {
                        binding.who.error = getString(R.string.mandatory_field)
                    }
                    PrescriptionDataViewModel.FIELD.WHERE -> {
                        binding.where.error = getString(R.string.mandatory_field)
                    }
                    PrescriptionDataViewModel.FIELD.WHEN -> {
                        binding.prescriptionDate.error = getString(R.string.mandatory_field)
                    }
                }
            }
        }
    }
}
