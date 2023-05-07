package com.example.campusevents.di

import com.example.campusevents.data.EventsRepository
import com.example.campusevents.data.EventsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun providesFireStoreRepo(
        repo: EventsRepositoryImpl
    ): EventsRepository

}