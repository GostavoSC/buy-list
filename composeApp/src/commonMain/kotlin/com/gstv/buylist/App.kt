package com.gstv.buylist

import androidx.compose.runtime.Composable
import com.gstv.buylist.ui.navigation.BuyListColorsNavHostController
import com.gstv.buylist.ui.theme.BuyListTheme

@Composable
fun App() {
    BuyListTheme {
        BuyListColorsNavHostController()
    }
}