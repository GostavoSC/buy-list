package com.gstv.buylist.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gstv.buylist.ui.core.OutlinedButton
import com.gstv.buylist.ui.theme.BuyListColors
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinInject(),
    onHistoryClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onNewListClick: () -> Unit = {}
) {
    val state = viewModel.state.collectAsState().value
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        HomeScreenContent(
            homeState = state,
            onHistoryClick = onHistoryClick,
            onSettingsClick = onSettingsClick,
            onNewListClick = onNewListClick
        )
    }

    LaunchedEffect(Unit) {
        viewModel.fetchLists()
    }
}


@Composable
private fun HomeScreenContent(
    homeState: HomeState,
    modifier: Modifier = Modifier,
    onNewListClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onNewListAIClick: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier.padding(horizontal = 12.dp, vertical = 24.dp)
    ) {
        Header()
        Body(
            onNewListClick = onNewListClick,
            onHistoryClick = onHistoryClick,
            onSettingsClick = onSettingsClick,
            onNewListAIClick = onNewListAIClick,
            state = homeState
        )
    }
}


@Composable
private fun Header() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Smart Buy List", style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = BuyListColors.Dark
            )
        )
        Text(
            "Create AI-powered shopping lists for you.",
            color = BuyListColors.gray,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun Body(
    modifier: Modifier = Modifier,
    state: HomeState,
    onNewListClick: () -> Unit,
    onNewListAIClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        BodyCard(
            onClick = onNewListClick,
            title = "Create New List",
            icon = Icons.Default.Add,
            content = {
                OutlinedButton(
                    Modifier.fillMaxWidth()
                ) {
                    Text("Start with AI Assistant")
                }
            }
        )

        BodyCard(
            title = "Recent Lists",
            icon = Icons.Default.List,
            content = {
                if (state.lists.isNotEmpty()) {
                    LazyColumn {
                        items(state.lists) {
                            Text(it.title)
                        }
                    }
                } else {
                    Text(
                        "No recent lists",
                        color = BuyListColors.gray,
                    )
                }
            }
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            BodyCard(
                title = "History",
                icon = Icons.Default.Refresh,
                modifier = Modifier.weight(1f),
                titleStyle = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Medium
                ),
                onClick = onHistoryClick
            )

            BodyCard(
                title = "Settings",
                icon = Icons.Default.Settings,
                modifier = Modifier.weight(1f),
                titleStyle = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Medium
                ),
                onClick = onSettingsClick
            )
        }
    }
}

@Composable
private fun BodyCard(
    modifier: Modifier = Modifier,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.headlineSmall,
    icon: ImageVector,
    content: @Composable () -> Unit = {},
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(
            1.dp, color = Color.LightGray
        ),
        onClick = onClick
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(22.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = BuyListColors.Dark,
                    modifier = Modifier.size(25.dp)
                )

                Text(
                    text = title,
                    style = titleStyle.copy(
                        fontWeight = FontWeight.Bold,
                        color = BuyListColors.Dark
                    )
                )
            }

            content()
        }
    }
}