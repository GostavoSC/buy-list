package com.gstv.buylist.ui.core

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gstv.buylist.ui.theme.BuyListColors

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: () -> Unit = {},
    floatActionButton: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = floatActionButton,
        bottomBar = bottomBar,
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                IconButton(
                    onClick = onBackPressed
                ) {
                    Icon(Icons.Default.ArrowBack, null)
                }
                Text(
                    title, style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = BuyListColors.Dark
                    )
                )
            }
        }
    ) {
        content(it)
    }
}