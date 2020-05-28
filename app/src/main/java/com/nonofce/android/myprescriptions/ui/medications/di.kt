package com.nonofce.android.myprescriptions.ui.medications

import com.nonofce.android.data.repository.Repository
import com.nonofce.android.usecases.medication.AddMedication
import com.nonofce.android.usecases.medication.LoadMedication
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MedicationListModule {

    @Provides
    fun provideLoadMedicationUseCase(repository: Repository) = LoadMedication(repository)

    @Provides
    fun getMedicationListViewModelProvider(loadMedication: LoadMedication) = MedicationListViewModel(loadMedication)
}

@Subcomponent(modules = [MedicationListModule::class])
interface MedicationListComponent {
    val viewModel: MedicationListViewModel
}

@Module
class MedicationDataModule {
    @Provides
    fun provideAddMedicationUseCase(repository: Repository) = AddMedication(repository)

    @Provides
    fun provideMedicationDataViewModel(addMedication: AddMedication) =
        MedicationDataViewModel(addMedication)


}

@Subcomponent(modules = [MedicationDataModule::class])
interface MedicationDataComponent {
    val viewModel: MedicationDataViewModel
}