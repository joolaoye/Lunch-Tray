package com.example.lunchtray.test

import androidx.navigation.NavController
import junit.framework.TestCase.assertEquals

fun NavController.assertRouteName(expectedRouteName : String) {
    assertEquals(expectedRouteName,currentBackStackEntry?.destination?.route)
}