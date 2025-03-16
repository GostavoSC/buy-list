@file:OptIn(ExperimentalMaterial3Api::class)

package com.gstv.buylist.ui.screens.creation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gstv.buylist.model.Item
import com.gstv.buylist.ui.core.BaseScreen
import com.gstv.buylist.ui.core.ModalBottomSheetLayout
import com.gstv.buylist.ui.core.OutlinedButton
import com.gstv.buylist.ui.core.OutlinedTextField
import com.gstv.buylist.ui.theme.BuyListColors
import kotlinx.coroutines.flow.SharedFlow
import org.koin.compose.koinInject

@Composable
fun NewListScreen(
    modifier: Modifier = Modifier,
    viewModel: NewListViewModel = koinInject(),
    listId: Long? = null,
    onBackPressed: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showBottomSheet by remember { mutableStateOf(false) }

    BaseScreen(
        modifier = modifier,
        onBackPressed = viewModel::onBackPressed,
        title = "Manual List Creation",
        floatActionButton = {
            AnimatedVisibility(
                state.uiState is UiNewListState.Saved,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FloatingActionButton(
                    onClick = {
                        viewModel.setIsEditing()
                    },
                    content = {
                        Icon(Icons.Default.Create, null)
                    }
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(
                state.uiState is UiNewListState.Editing,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp),
                    onClick = viewModel::saveList,
                    content = {
                        Text(
                            text = "Save",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }
                )
            }
        }

    ) {
        ScreenContent(
            modifier = Modifier.padding(it),
            state = state,
            events = viewModel.events,
            showBottomSheet = showBottomSheet,
            onTitleChange = viewModel::updateTitle,
            onDescriptionChange = viewModel::updateDescription,
            onAddItemClicked = viewModel::insertItem,
            onItemCheckedChange = viewModel::updateItem,
            snackbarHostState = snackbarHostState,
            onCloseScreenConfirmed = onBackPressed,
            onBottomSheetDismiss = {
                showBottomSheet = false
            },
            onShowBottomSheet = {
                showBottomSheet = true
            }
        )
    }

    LaunchEvents(
        events = viewModel.events,
        snackbarHostState = snackbarHostState,
        onBackPressed = onBackPressed,
        onShowBottomSheet = {
            showBottomSheet = true
        }
    )

    LaunchedEffect(Unit) {
        if (listId != null) viewModel.fetchList(listId)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    state: NewListState,
    snackbarHostState: SnackbarHostState,
    showBottomSheet: Boolean,
    events: SharedFlow<Events>,
    onDescriptionChange: (String) -> Unit,
    onTitleChange: (String) -> Unit,
    onAddItemClicked: (String) -> Unit,
    onItemCheckedChange: (Item) -> Unit,
    onCloseScreenConfirmed: () -> Unit,
    onBottomSheetDismiss: () -> Unit,
    onShowBottomSheet: () -> Unit
) {

    ModalBottomSheetLayout(
        expanded = showBottomSheet,
        onSheetDismissed = onBottomSheetDismiss,
        sheetContent = {
            BottomSheetContent(
                title = state.title,
                description = state.description,
                onTitleChange = onTitleChange,
                onDescriptionChange = onDescriptionChange,
                events = events,
                onDismiss = onBottomSheetDismiss,
                onConfirm = onCloseScreenConfirmed
            )
        }
    ) {
        LazyColumn(
            modifier = modifier.padding(horizontal = 18.dp)
                .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical),
            verticalArrangement = Arrangement.spacedBy(22.dp),
        ) {
            item {
                Header(
                    title = state.title,
                    description = state.description,
                    isEditing = state.uiState is UiNewListState.Editing,
                    onEditClick = onShowBottomSheet
                )
            }

            item {
                AnimatedVisibility(
                    state.uiState is UiNewListState.Editing, enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    AddItemContent(
                        onAddItemClicked = onAddItemClicked
                    )
                }
            }

            itemsContent(items = state.items, onCheckedChange = onItemCheckedChange)
        }

        SnackbarHost(
            hostState = snackbarHostState,
            snackbar = {
                Snackbar {
                    Text(it.visuals.message)
                }
            }
        )
    }
}


private fun LazyListScope.itemsContent(
    items: List<Item>,
    onCheckedChange: (Item) -> Unit = {}
) {
    items(items) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    onCheckedChange(it)
                }
                .padding(vertical = 12.dp)
        ) {
            Checkbox(checked = it.isChecked, onCheckedChange = null)
            Text(
                it.title,
                textDecoration = if (it.isChecked) TextDecoration.LineThrough else null
            )
        }
    }
}


@Composable
private fun AddItemContent(modifier: Modifier = Modifier, onAddItemClicked: (String) -> Unit = {}) {
    var value by remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier.height(50.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().weight(1f),
            placeholder = "Add an item...",
            value = value,
            onValueChange = {
                value = it
            }
        )

        OutlinedButton(
            modifier = Modifier.wrapContentWidth().fillMaxHeight(),
            onClick = {
                onAddItemClicked(value)
                value = ""
            },
            content = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(24.dp)
                )
            }
        )
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    isEditing: Boolean,
    onEditClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.clickable(isEditing) { onEditClick() }
        ) {
            Text(
                title, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Medium
                )
            )
            AnimatedVisibility(isEditing) {
                Icon(Icons.Default.Edit, null, modifier = Modifier.size(12.dp))
            }
        }
        Text(
            description,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Normal,
                color = BuyListColors.gray
            )
        )
    }
}

@Composable
private fun BottomSheetContent(
    modifier: Modifier = Modifier,
    title: String,
    description: String?,
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    events: SharedFlow<Events>
) {
    val eventState by events.collectAsStateWithLifecycle(null)

    if (eventState is Events.WarningClose) {
        WarningCloseContent(
            onDismiss = onDismiss,
            onConfirm = onConfirm
        )
    } else
        TitleAndDescriptionEditContent(
            modifier = modifier,
            onTitleChange = onTitleChange,
            onDescriptionChange = onDescriptionChange,
            title = title,
            description = description ?: ""
        )
}

@Composable
private fun WarningCloseContent(onDismiss: () -> Unit = {}, onConfirm: () -> Unit = {}) {
    Column(
        modifier = Modifier.padding(horizontal = 18.dp)
            .padding(bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Are you sure you want to close this list?",
            style = MaterialTheme.typography.titleLarge
        )
        Text("This action cannot be undone.", style = MaterialTheme.typography.titleMedium)

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onDismiss
        ) {
            Text("No")
        }

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            borderStroke = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            onClick = onConfirm,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.Transparent,
            )
        ) {
            Text("Yes", color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
private fun TitleAndDescriptionEditContent(
    modifier: Modifier = Modifier,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    description: String,
    title: String
) {
    Column(
        modifier = modifier.padding(horizontal = 18.dp, vertical = 22.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Title",
            value = title,
            onValueChange = onTitleChange
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Description",
            value = description,
            onValueChange = onDescriptionChange
        )
    }
}


@Composable
private fun LaunchEvents(
    events: SharedFlow<Events>,
    onBackPressed: () -> Unit,
    onShowBottomSheet: () -> Unit = {},
    snackbarHostState: SnackbarHostState
) {
    LaunchedEffect(Unit) {
        events.collect {
            when (it) {
                Events.Close -> onBackPressed()
                is Events.OnError -> {
                    snackbarHostState.showSnackbar(it.message)
                }

                is Events.WarningClose -> onShowBottomSheet()
                else -> Unit
            }
        }
    }
}