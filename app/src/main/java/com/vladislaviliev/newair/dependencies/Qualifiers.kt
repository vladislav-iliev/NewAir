package com.vladislaviliev.newair.dependencies

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ReadingsDependency

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PreferencesDependency