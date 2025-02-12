package com.vladislaviliev.newair.user.paging

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vladislaviliev.newair.screens.home.locationPicker.ViewModel
import com.vladislaviliev.newair.user.location.UserLocationsRepository
import com.vladislaviliev.newair.user.settings.SettingsRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PagingTest {


    @Inject
    lateinit var locationsRepository: UserLocationsRepository

    @Inject
    lateinit var settingsRepository: SettingsRepository

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun displaysCity() {
        val vm = ViewModel(locationsRepository, settingsRepository)
    }
}