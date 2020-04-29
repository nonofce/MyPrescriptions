package com.nonofce.android.myprescriptions.ui.prescription

import com.nonofce.android.data.repository.Repository
import com.nonofce.android.usecases.AddNewPrescription
import com.nonofce.android.usecases.DeletePrescription
import com.nonofce.android.usecases.LoadPrescriptions
import com.nonofce.android.usecases.UpdatePrescription
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class PrescriptionListModule {

    @Provides
    fun loadAllPrescriptionsUseCaseprovider(repository: Repository): LoadPrescriptions =
        LoadPrescriptions(repository)

    @Provides
    fun deletePrescriptionUseCaseProvider(repository: Repository): DeletePrescription =
        DeletePrescription(repository)

    @Provides
    fun modelProvider(
        loadPrescriptions: LoadPrescriptions,
        deletePrescription: DeletePrescription
    ): PrescriptionListViewModel =
        PrescriptionListViewModel(loadPrescriptions, deletePrescription)
}

@Subcomponent(modules = [PrescriptionListModule::class])
interface PrescriptionListComponent {
    val viewModel: PrescriptionListViewModel
}

@Module
class PrescriptionDataModule {

    @Provides
    fun addNewPrescriptionUseCaseProvider(repository: Repository): AddNewPrescription =
        AddNewPrescription(repository)

    @Provides
    fun updatePrescriptionUseCaseProvider(repository: Repository): UpdatePrescription =
        UpdatePrescription(repository)

    @Provides
    fun modelProvider(addNewPrescription: AddNewPrescription, updatePrescription: UpdatePrescription): PrescriptionDataViewModel =
        PrescriptionDataViewModel(addNewPrescription, updatePrescription)
}

@Subcomponent(modules = [PrescriptionDataModule::class])
interface PrescriptionDataComponent {
    val viewModel: PrescriptionDataViewModel
}