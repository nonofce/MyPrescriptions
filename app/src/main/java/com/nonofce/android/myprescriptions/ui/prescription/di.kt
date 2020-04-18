package com.nonofce.android.myprescriptions.ui.prescription

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class PrescriptionModule {

    @Provides
    fun modelProvider(): PrescriptionViewModel = PrescriptionViewModel()
}

@Subcomponent(modules = [PrescriptionModule::class])
interface PrescriptionComponent {
    val viewModel: PrescriptionViewModel
}