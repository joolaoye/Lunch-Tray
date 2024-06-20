package com.example.lunchtray.test

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.oNodeWithStringId(
    @StringRes Id : Int) : SemanticsNodeInteraction {
    return onNodeWithText(activity.getString(Id))
}

fun hasStringId( @StringRes Id : Int) : SemanticsMatcher {
    val context = ApplicationProvider.getApplicationContext<Context>()
    return hasText(context.getString(Id), ignoreCase = true)
}