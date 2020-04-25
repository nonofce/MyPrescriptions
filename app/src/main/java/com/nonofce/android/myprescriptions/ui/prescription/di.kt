package com.nonofce.android.myprescriptions.ui.prescription

import com.nonofce.android.data.repository.Repository
import com.nonofce.android.usecases.AddNewPrescription
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class PrescriptionListModule {

    @Provides
    fun modelProvider(): PrescriptionListViewModel = PrescriptionListViewModel()
}

@Subcomponent(modules = [PrescriptionListModule::class])
interface PrescriptionListComponent {
    val viewModel: PrescriptionListViewModel
}

@Module
class PrescriptionDataModule {

    @Provides
    fun addNewPrescriptionUseCaseProvider(repository: Repository) = AddNewPrescription(repository)

    @Provides
    fun modelProvider(addNewPrescription: AddNewPrescription): PrescriptionDataViewModel =
        PrescriptionDataViewModel(addNewPrescription)
}

@Subcomponent(modules = [PrescriptionDataModule::class])
interface PrescriptionDataComponent {
    val viewModel: PrescriptionDataViewModel
}