package com.example.githubpoc.ui

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun DropdownMenuButton(
    options: List<String>,
    onSelected: (selected: String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf(options[0]) }

    TextButton(onClick = { expanded.value = !expanded.value }) {
        Text(selectedOption.value)
        DropdownMenu(
            expanded = expanded.value, // 控制下拉菜单的显示状态
            onDismissRequest = { expanded.value = false }, // 点击菜单外时关闭菜单
            modifier = Modifier.wrapContentSize() // 根据内容调整大小
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption.value = option
                        expanded.value = false
                        onSelected.invoke(option)
                    },
                    text = {
                        Text(option)
                    }
                )
            }
        }
    }
}
