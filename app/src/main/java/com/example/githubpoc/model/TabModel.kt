package com.example.githubpoc.model

import androidx.compose.ui.graphics.vector.ImageVector

data class TabItem(
    val id: String,
    val title: String,
    val icon: ImageVector? = null
)
