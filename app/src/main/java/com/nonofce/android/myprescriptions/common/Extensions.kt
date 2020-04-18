package com.nonofce.android.myprescriptions.common

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

inline fun <ViewHolder : RecyclerView.ViewHolder, T> RecyclerView.Adapter<ViewHolder>.basicDiffUtil(
    initialValue: List<T>,
    crossinline areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) = Delegates.observable(initialValue) { _, old, new ->
    DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            areItemsTheSame(old[oldItemPosition], new[newItemPosition])

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            areContentsTheSame(old[oldItemPosition], new[newItemPosition])

        override fun getOldListSize(): Int = old.size
        override fun getNewListSize(): Int = new.size

    }).dispatchUpdatesTo(this@basicDiffUtil)

}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> Fragment.getViewModel(crossinline factory: () -> T): T {
    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }
    return ViewModelProvider(this, vmFactory).get(T::class.java)
}

@Suppress("UNCHECKED_CAST")
fun <T : Application> Fragment.getApp() =
    context!!.applicationContext as T


