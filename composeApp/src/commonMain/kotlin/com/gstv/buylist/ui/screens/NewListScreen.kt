package com.gstv.buylist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gstv.buylist.ui.core.BaseScreen
import com.gstv.buylist.ui.core.OutlinedButton

@Composable
fun NewListScreen(onBackPressed: () -> Unit, modifier: Modifier = Modifier) {
    BaseScreen(
        modifier = modifier,
        onBackPressed = onBackPressed,
        title = "Manual List Creation"
    ) {
        ScreenContent()
    }
}

@Composable
private fun ScreenContent(modifier: Modifier = Modifier) {
    var value by remember { mutableStateOf("") }
    Column(
        modifier = modifier.padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.height(50.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().weight(1f),

                placeholder = {
                    Text(text = "Add an item...")
                },
                value = value,
                shape = RoundedCornerShape(8.dp),
                textStyle = TextStyle(lineHeight = 10.sp),
                onValueChange = {
                    value = it
                }
            )

            OutlinedButton(
                modifier = Modifier.wrapContentWidth().fillMaxHeight()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(24.dp)
                )
                Text(text = "Add")
            }
        }

    }
}