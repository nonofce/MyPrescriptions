package com.nonofce.android.myprescriptions.ui.prescription

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nonofce.android.domain.Prescription
import com.nonofce.android.myprescriptions.common.basicDiffUtil
import com.nonofce.android.myprescriptions.databinding.PrescriptionViewBinding

class PrescriptionAdapter(
    private val onSelectPrescription: (Prescription) -> Unit,
    private val onEditPrescription: (Prescription) -> Unit,
    private val onDeletePrescription: (Prescription) -> Unit
) : RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder>() {

    var items: List<Prescription> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new ->
            old.id == new.id
        }
    )

    private lateinit var binding: PrescriptionViewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionViewHolder {
        binding =
            PrescriptionViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PrescriptionViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PrescriptionViewHolder, position: Int) {
        val prescription = items[position]

        with(binding) {
            prescriptionEditButton.setOnClickListener {
                onEditPrescription(prescription)
            }
            prescriptionDeleteButton.setOnClickListener {
                onDeletePrescription(prescription)
            }
            root.setOnClickListener {
                onSelectPrescription(prescription)
            }
        }
        holder.bind(prescription)
    }

    class PrescriptionViewHolder(private val binding: PrescriptionViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(prescription: Prescription) {
            binding.who.text = prescription.who
            binding.where.text = prescription.where
            binding.`when`.text = prescription.date
        }

    }
}