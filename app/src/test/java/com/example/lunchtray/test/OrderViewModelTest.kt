package com.example.lunchtray.test

import androidx.compose.runtime.collectAsState
import com.example.lunchtray.model.MenuItem
import com.example.lunchtray.model.OrderUiState
import com.example.lunchtray.ui.OrderViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class OrderViewModelTest {
    val viewModel = OrderViewModel()
    @Test
    fun OrderViewModel_initialization_noOrder() {
        val uiState = viewModel.uiState.value

        assertEquals(uiState.entree, null)
        assertEquals(uiState.sideDish, null)
        assertEquals(uiState.accompaniment, null)
        assertEquals(uiState.itemTotalPrice, 0.0)
        assertEquals(uiState.orderTax, 0.0)
        assertEquals(uiState.orderTotalPrice, 0.0)
    }

    @Test
    fun OrderViewModel_updateEntree_uiStateUpdated() {
        val entree = MenuItem.EntreeItem(
            name = "Cauliflower",
            description = "Whole cauliflower, brined, roasted, and deep fried",
            price = 7.00,
        )

        viewModel.updateEntree(entree)

        val uiState = viewModel.uiState.value

        assertEquals(uiState.entree, entree)
    }

    @Test
    fun OrderViewModel_updateSideDish_uiStateUpdated() {
        val sideDish = MenuItem.SideDishItem(
            name = "Coconut Rice",
            description = "Rice, coconut milk, lime, and sugar",
            price = 1.50,
        )

        viewModel.updateSideDish(sideDish)

        val uiState = viewModel.uiState.value

        assertEquals(uiState.sideDish, sideDish)
        assertEquals(uiState.itemTotalPrice, sideDish.price)
    }

    @Test
    fun OrderViewModel_updateAccompaniment_uiStateUpdated() {
        val accompanimentItem = MenuItem.AccompanimentItem(
            name = "Lunch Roll",
            description = "Fresh baked roll made in house",
            price = 0.50,
        )

        viewModel.updateAccompaniment(accompanimentItem)

        val uiState = viewModel.uiState.value

        assertEquals(uiState.accompaniment, accompanimentItem)
        assertEquals(uiState.itemTotalPrice, accompanimentItem.price)
    }

    @Test
    fun OrderViewModel_updateItem_PriceUpdatedAccurately() {
        val initialEntree = MenuItem.EntreeItem(
            name = "Cauliflower",
            description = "Whole cauliflower, brined, roasted, and deep fried",
            price = 7.00,
        )

        val nextEntree = MenuItem.EntreeItem(
            name = "Three Bean Chili",
            description = "Black beans, red beans, kidney beans, slow cooked, topped with onion",
            price = 4.00,
        )

        viewModel.updateEntree(initialEntree)

        var uiState = viewModel.uiState.value

        assertEquals(uiState.itemTotalPrice, initialEntree.price)

        viewModel.updateEntree(nextEntree)

        uiState = viewModel.uiState.value

        assertEquals(uiState.itemTotalPrice, nextEntree.price)
    }

    @Test
    fun OrderViewModel_resetOrder_OrderClearedAnduiStateUpdated() {
        val entree = MenuItem.EntreeItem(
            name = "Cauliflower",
            description = "Whole cauliflower, brined, roasted, and deep fried",
            price = 7.00,
        )

        val sideDish = MenuItem.SideDishItem(
            name = "Coconut Rice",
            description = "Rice, coconut milk, lime, and sugar",
            price = 1.50,
        )

        val accompanimentItem = MenuItem.AccompanimentItem(
            name = "Lunch Roll",
            description = "Fresh baked roll made in house",
            price = 0.50,
        )

        viewModel.updateEntree(entree)
        viewModel.updateSideDish(sideDish)
        viewModel.updateAccompaniment(accompanimentItem)

        viewModel.resetOrder()

        val uiState = viewModel.uiState.value

        assertEquals(uiState, OrderUiState())
    }
}