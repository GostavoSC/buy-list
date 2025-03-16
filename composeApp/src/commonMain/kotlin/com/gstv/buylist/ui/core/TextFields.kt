package com.gstv.buylist.ui.core

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OutlinedTextField(modifier: Modifier = Modifier, placeholder: String = "", value: String = "", onValueChange: (String) -> Unit = {}) {
    androidx.compose.material3.OutlinedTextField(
        modifier = modifier,

        placeholder = {
            Text(text = placeholder)
        },
        value = value,
        shape = RoundedCornerShape(8.dp),
        textStyle = TextStyle(lineHeight = 10.sp),
        onValueChange = onValueChange
    )
}