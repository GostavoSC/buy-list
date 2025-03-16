package com.gstv.buylist.ui.screens.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.sharp.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gstv.buylist.model.Lists
import com.gstv.buylist.ui.core.BaseScreen
import com.gstv.buylist.ui.theme.BuyListColors
import org.koin.compose.koinInject

@Composable
fun HistoryScreen(
    onBackPressed: () -> Unit,
    viewModel: HistoryViewModel = koinInject(),
    onItemClicked: (listId: Long) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    BaseScreen(
        title = "Shopping History",
        onBackPressed = onBackPressed
    ) {
        ScreenContent(modifier = Modifier.padding(it), list = state.list, onClick = onItemClicked)
    }
}


@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    list: List<Lists> = emptyList(),
    onClick: (listId: Long) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(list) {
            ShoppingCard(
                onClick = { _ ->
                    onClick(it.id)
                },
                list =
                    UiBuyList(
                        title = it.title,
                        itemsSize = it.items.size,
                        total = 0.0
                    )
            )
        }
    }
}


@Composable
private fun ShoppingCard(list: UiBuyList, onClick: (UiBuyList) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.LightGray),
        onClick = {
            onClick(list)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Sharp.ShoppingCart,
                        contentDescription = "Shopping Icon",
                        tint = BuyListColors.Dark,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = list.title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        color = BuyListColors.Dark
                    )
                }
                Text(
                    text = "${list.itemsSize} items • $${list.total}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Calendar Icon",
                    tint = BuyListColors.gray,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = list.date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = BuyListColors.gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


data class UiBuyList(
    val title: String,
    val itemsSize: Int,
    val total: Double,
    val date: String = "Mar 15, 2024",
)
