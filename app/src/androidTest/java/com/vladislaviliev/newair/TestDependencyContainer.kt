package com.vladislaviliev.newair

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton
import com.vladislaviliev.newair.DependencyContainer as ProductionContainer

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ProductionContainer::class]
)
class TestDependencyContainer {

    @Provides
    @Singleton
    fun provideLocationsRepository() = InMemoryUserLocationsRepository()

    @Provides
    @Singleton
    fun provideResponseRepository() = InMemoryResponseRepository()

    @Provides
    @Singleton
    fun provideSettingsRepository() = InMemorySettingsRepository()
}