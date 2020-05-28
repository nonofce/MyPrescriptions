package com.nonofce.android.myprescriptions.ui.medications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nonofce.android.domain.Medication
import com.nonofce.android.myprescriptions.common.basicDiffUtil
import com.nonofce.android.myprescriptions.databinding.MedicationViewBinding

class MedicationAdapter(
    private val onSelectMedication: (Medication) -> Unit,
    private val onEditMedication: (Medication) -> Unit,
    private val onDeleteMedication: (Medication) -> Unit
) : RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder>() {

    private lateinit var binding: MedicationViewBinding

    var items: List<Medication> by basicDiffUtil(emptyList(), areItemsTheSame = { old, new ->
        old.id == new.id
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        binding =
            MedicationViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicationViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        val medicationData = items[position]

        with(binding){

            medication = medicationData
            root.setOnClickListener {
                onSelectMedication(medicationData)
            }

            medicationEditButton.setOnClickListener {
                onEditMedication(medicationData)
            }

            medicationDeleteButton.setOnClickListener {
                onDeleteMedication(medicationData)
            }
        }

    }

    class MedicationViewHolder(binding: MedicationViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}