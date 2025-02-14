package com.vladislaviliev.newair.screens.home.locationPicker

import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelectable
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.Density
import androidx.paging.PagingData
import com.vladislaviliev.newair.CommonFunctions.getContext
import com.vladislaviliev.newair.CommonFunctions.getString
import com.vladislaviliev.newair.R
import com.vladislaviliev.newair.screens.paging.Model
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PickerTest {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @get: Rule
    val composeRule = createComposeRule()

    private lateinit var header: Model.Header
    private lateinit var preselectedLocation: Model.Location
    private lateinit var otherLocation: Model.Location
    private val preselectedId = 0

    private val getHeaderNode = { composeRule.onNodeWithText(header.char.toString()) }
    private val getLocationNode =
        { l: Model.Location -> composeRule.onNode(hasText(l.title) and isSelectable()) }


    private var confirmedId = Integer.MIN_VALUE
    private var isDismissClicked = false

    @Before
    fun before() {
        hiltRule.inject()

        header = Model.Header('A')
        preselectedLocation = Model.Location(preselectedId, "A0")
        otherLocation = Model.Location(1, "B1")
    }

    private fun setTripleItems() {
        val dataFlow =
            MutableStateFlow(PagingData.from(listOf(header, preselectedLocation, otherLocation)))

        composeRule.setContent {
            LocationPicker(
                dataFlow,
                preselectedId,
                { id: Int -> confirmedId = id },
                { isDismissClicked = true })
        }

    }

    @Test
    fun displays_items() {
        setTripleItems()
        getHeaderNode().assertIsDisplayed()
        getLocationNode(preselectedLocation).assertIsDisplayed()
        getLocationNode(otherLocation).assertIsDisplayed()
    }

    @Test
    fun items_are_selectable() {
        setTripleItems()
        getHeaderNode().assertHasNoClickAction()
        getLocationNode(preselectedLocation).assertIsSelectable()
        getLocationNode(otherLocation).assertIsSelectable()
    }

    @Test
    fun preselected_item_is_selected() {
        setTripleItems()
        getLocationNode(preselectedLocation).assertIsSelected()
    }

    @Test
    fun other_item_is_not_selected() {
        setTripleItems()
        getLocationNode(otherLocation).assertIsNotSelected()
    }

    @Test
    fun selecting_other_item_switches_selection() {
        setTripleItems()
        getLocationNode(otherLocation).performClick()
        getLocationNode(preselectedLocation).assertIsNotSelected()
        getLocationNode(otherLocation).assertIsSelected()
    }

    @Test
    fun confirm_calls_onLocationSelected() {
        setTripleItems()
        getLocationNode(otherLocation).performClick()
        composeRule.onNodeWithText(getString(android.R.string.ok)).performClick()
        Assert.assertEquals(confirmedId, otherLocation.id)
    }

    @Test
    fun cancel_calls_onDismiss() {
        setTripleItems()
        composeRule.onNodeWithText(getString(android.R.string.cancel)).performClick()
        Assert.assertTrue(isDismissClicked)
    }

    @Test
    fun dialog_does_not_exceed_max_height() {
        val dataFlow = MutableStateFlow(PagingData.from(List<Model>(20) { Model.Location(it, "$it") }))

        composeRule.setContent {
            LocationPicker(
                dataFlow,
                preselectedId,
                { id: Int -> confirmedId = id },
                { isDismissClicked = true })

            dimensionResource(R.dimen.picker_dialog_max_height)
        }

        val allowedPixels = getContext().resources.getDimension(R.dimen.picker_dialog_max_height)
        val density = Density(getContext().resources.displayMetrics.density)
        val allowedDp = with(density) {allowedPixels.toDp()}

        composeRule.onNode(SemanticsMatcher.keyIsDefined(SemanticsProperties.IsDialog))
            .assertHeightIsEqualTo(allowedDp)
    }
}