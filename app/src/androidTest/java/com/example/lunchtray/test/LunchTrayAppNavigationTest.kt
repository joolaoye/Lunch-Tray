package com.example.lunchtray.test

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.lunchtray.R
import com.example.lunchtray.LunchTrayApp
import com.example.lunchtray.LunchTrayScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LunchTrayAppNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController : TestNavHostController

    @Before
    fun setupNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }

            LunchTrayApp(navController = navController)
        }
    }

    @Test
    fun LunchTrayScreen_verifyOnStartDestination() {
        navController.assertRouteName(LunchTrayScreen.Start.name)
    }

    @Test
    fun LunchTrayScreen_verifyNoBackButtonOnStartScreen() {
        val contentDescription = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(contentDescription)
            .assertDoesNotExist()
    }

    @Test
    fun LunchTrayScreen_clickStartOrderButton_navigateToEntreeMenuScreen() {
        navigateToEntreeMenuScreen()

        navController.assertRouteName(LunchTrayScreen.EntreeMenu.name)
    }

    @Test
    fun LunchTrayScreen_backButtonClickedOnEntreeMenuScreen_navigateToStartScreen() {
        navigateToEntreeMenuScreen()

        clickBackButton()

        navController.assertRouteName(LunchTrayScreen.Start.name)
    }

    @Test
    fun LunchTrayScreen_cancelButtonClickedOnEntreeMenuScreen_navigateToStartScreen() {
        navigateToEntreeMenuScreen()

        clickCancelButton()

        navController.assertRouteName(LunchTrayScreen.Start.name)
    }


    @Test
    fun LunchTrayScreen_nextButtonClickedOnEntreeMenuScreen_navigateToSideDishMenuScreen() {
        navigateToSideMenuScreen()

        navController.assertRouteName(LunchTrayScreen.SideDishMenu.name)
    }

    @Test
    fun LunchTrayScreen_backButtonClickedOnSideDishMenuScreen_navigateToEntreeMenuScreen() {
        navigateToSideMenuScreen()

        clickBackButton()

        navController.assertRouteName(LunchTrayScreen.EntreeMenu.name)
    }

    @Test
    fun LunchTrayScreen_cancelButtonClickedOnSideDishMenuScreen_navigateToStartScreen() {
        navigateToSideMenuScreen()

        clickCancelButton()

        navController.assertRouteName(LunchTrayScreen.Start.name)
    }

    @Test
    fun LunchTrayScreen_nextButtonClickedOnSideMenuScreen_navigateToAccompanimentMenuScreen() {
        navigateToAccompanimentMenuScreen()

        navController.assertRouteName(LunchTrayScreen.AccompanimentMenu.name)
    }

    @Test
    fun LunchTrayScreen_backButtonClickedOnAccompanimentMenuScreen_navigateToSideDishMenuScreen() {
        navigateToAccompanimentMenuScreen()

        clickBackButton()

        navController.assertRouteName(LunchTrayScreen.SideDishMenu.name)
    }

    @Test
    fun LunchTrayScreen_cancelButtonClickedOnAccompanimentMenuScreen_navigateToStartScreen() {
        navigateToAccompanimentMenuScreen()

        clickCancelButton()

        navController.assertRouteName(LunchTrayScreen.Start.name)
    }

    @Test
    fun LunchTrayScreen_nextButtonOnAccompanimentMenuScreen_navigateToCheckoutScreen() {
        navigateToCheckoutScreen()

        navController.assertRouteName(LunchTrayScreen.Checkout.name)
    }

    @Test
    fun LunchTrayScreen_backButtonClickedOnCheckoutScreen_navigateToAccompanimentMenuScreen() {
        navigateToCheckoutScreen()

        clickBackButton()

        navController.assertRouteName(LunchTrayScreen.AccompanimentMenu.name)
    }

    @Test
    fun LunchTrayScreen_cancelButtonClickedOnCheckoutScreen_navigateToStartScreen() {
        navigateToCheckoutScreen()

        clickCancelButton()

        navController.assertRouteName(LunchTrayScreen.Start.name)
    }

    @Test
    fun LunchTrayScreen_submitButtonClickedOnCheckoutScreen_navigateToStartScreen() {
        navigateToCheckoutScreen()

        clickButton(R.string.submit)

        navController.assertRouteName(LunchTrayScreen.Start.name)
    }

    private fun clickBackButton() {
        val contentDescription = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(contentDescription)
            .performClick()
    }

    private fun clickButton(@StringRes Id : Int) {
        composeTestRule.onNode(
            hasStringId(Id) and hasClickAction())
            .performClick()
    }

    private fun clickCancelButton() {
        clickButton(R.string.cancel)
    }

    private fun clickNextButton() {
        clickButton(R.string.next)
    }

    private fun selectOption(option : String) {
        composeTestRule.onNode(
            hasText(option) and hasClickAction())
            .performClick()
    }

    private fun navigateToEntreeMenuScreen() {
        clickButton(R.string.start_order)
    }

    private fun navigateToSideMenuScreen() {
        navigateToEntreeMenuScreen()

        selectOption("Cauliflower")

        clickNextButton()
    }

    private fun navigateToAccompanimentMenuScreen() {
        navigateToSideMenuScreen()

        selectOption("Summer Salad")

        clickNextButton()
    }

    private fun navigateToCheckoutScreen() {
        navigateToAccompanimentMenuScreen()

        selectOption("Lunch Roll")

        clickNextButton()
    }
}