package io.flesh.hyerplex2.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.flesh.hyerplex2.utils.HyperPlexer

@Module
@InstallIn(ViewModelComponent::class)
object HyperPlexModule {

    @Provides
    fun providesHyperPlexer(): HyperPlexer = HyperPlexer()
}
