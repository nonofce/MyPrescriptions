package com.nonofce.android.myprescriptions.ui.prescription

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nonofce.android.domain.Prescription
import com.nonofce.android.myprescriptions.common.basicDiffUtil
import com.nonofce.android.myprescriptions.databinding.PrescriptionViewBinding
import kotlinx.android.synthetic.main.prescription_view.view.*

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

        with(holder.itemView){
            prescription_edit_button.setOnClickListener {
                onEditPrescription(prescription)
            }
            prescription_delete_button.setOnClickListener {
                onDeletePrescription(prescription)
            }
            setOnClickListener {
                onSelectPrescription(prescription)
            }
        }
        holder.bind(prescription)
    }

    class PrescriptionViewHolder(private val binding: PrescriptionViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(prescription: Prescription) {
            itemView.who.text = prescription.who
            itemView.where.text = prescription.where
            itemView.`when`.text = prescription.date
        }

    }
}