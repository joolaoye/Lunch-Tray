package com.example.lunchtray.test

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.lunchtray.R
import com.example.lunchtray.datasource.DataSource
import com.example.lunchtray.model.MenuItem
import com.example.lunchtray.model.OrderUiState
import com.example.lunchtray.ui.BaseMenuScreen
import com.example.lunchtray.ui.CheckoutScreen
import com.example.lunchtray.ui.StartOrderScreen
import com.example.lunchtray.ui.formatPrice
import org.junit.Rule
import org.junit.Test

class LunchTrayOrderScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val entreeMenuItems = listOf(
        MenuItem.EntreeItem(
            name = "Cauliflower",
            description = "Whole cauliflower, brined, roasted, and deep fried",
            price = 7.00,
        ),
        MenuItem.EntreeItem(
            name = "Three Bean Chili",
            description = "Black beans, red beans, kidney beans, slow cooked, topped with onion",
            price = 4.00,
        ),
        MenuItem.EntreeItem(
            name = "Mushroom Pasta",
            description = "Penne pasta, mushrooms, basil, with plum tomatoes cooked in garlic " +
                    "and olive oil",
            price = 5.50,
        ),
        MenuItem.EntreeItem(
            name = "Spicy Black Bean Skillet",
            description = "Seasonal vegetables, black beans, house spice blend, served with " +
                    "avocado and quick pickled onions",
            price = 5.50,
        )
    )

    @Test
    fun startOrderScreen_verifyContent() {
        composeTestRule.setContent {
            StartOrderScreen(
                onStartOrderButtonClicked = {}
            )
        }

        buttonExists(R.string.start_order)
    }

    @Test
    fun MenuOption_verifyContent() {
        setMenuOption()

        entreeMenuItems.forEach {
            item ->
            composeTestRule.onNodeWithText(item.name).assertIsDisplayed()
        }
    }

    @Test
    fun MenuOption_selectOption_nextButtonEnabled() {
        setMenuOption()

        composeTestRule.onNodeWithText("Cauliflower").performClick()

        composeTestRule.onNode(
            hasStringId(R.string.next) and hasClickAction())
            .assertIsEnabled()
    }

    @Test
    fun CheckoutScreen_verifyContent() {
        val orderUiState = OrderUiState(
            entree = DataSource.entreeMenuItems[0],
            sideDish = DataSource.sideDishMenuItems[0],
            accompaniment = DataSource.accompanimentMenuItems[0],
            itemTotalPrice = 15.00,
            orderTax = 1.00,
            orderTotalPrice = 16.00
        )

        composeTestRule.setContent {
            CheckoutScreen(
                orderUiState = orderUiState,
                onNextButtonClicked = {},
                onCancelButtonClicked = {}
            )
        }

        composeTestRule.onNodeWithText("Cauliflower").assertIsDisplayed()
        composeTestRule.onNodeWithText("Summer Salad").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lunch Roll").assertIsDisplayed()

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.subtotal, orderUiState.itemTotalPrice.formatPrice()
            )
        )

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.tax, orderUiState.orderTax.formatPrice()
            )
        )

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.total, orderUiState.orderTotalPrice.formatPrice()
            )
        )


        buttonExists(R.string.cancel)

        buttonExists(R.string.submit)
    }

    private fun setMenuOption() {
        composeTestRule.setContent {
            BaseMenuScreen(
                options = entreeMenuItems,
                onCancelButtonClicked = {},
                onNextButtonClicked = {},
                onSelectionChanged = {}
            )
        }
    }

    private fun buttonExists(@StringRes Id: Int) {
        composeTestRule.onNode(
            hasStringId(Id) and hasClickAction())
            .assertIsDisplayed()
    }
}