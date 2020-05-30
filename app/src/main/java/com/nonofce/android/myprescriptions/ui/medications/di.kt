package com.nonofce.android.myprescriptions.ui.medications

import com.nonofce.android.data.repository.Repository
import com.nonofce.android.usecases.medication.AddMedication
import com.nonofce.android.usecases.medication.DeleteMedication
import com.nonofce.android.usecases.medication.LoadMedication
import com.nonofce.android.usecases.medication.UpdateMedication
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Singleton

@Module
class MedicationListModule {

    @Provides
    fun provideLoadMedicationUseCase(repository: Repository) = LoadMedication(repository)

    @Provides
    fun provideDeleteMedicationUseCase(repository: Repository) = DeleteMedication(repository)

    @Provides
    fun getMedicationListViewModelProvider(
        loadMedication: LoadMedication,
        deleteMedication: DeleteMedication
    ) = MedicationListViewModel(loadMedication, deleteMedication)
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
    fun provideUpdateMedicationUseCase(repository: Repository) = UpdateMedication(repository)

    @Provides
    fun provideMedicationDataViewModel(addMedication: AddMedication, updateMedication: UpdateMedication) =
        MedicationDataViewModel(addMedication, updateMedication)


}

@Subcomponent(modules = [MedicationDataModule::class])
interface MedicationDataComponent {
    val viewModel: MedicationDataViewModel
}