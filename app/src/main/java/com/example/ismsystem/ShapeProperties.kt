package com.example.ismsystem

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

data class ShapeProperties(
    val id: Int,
    val shape: Shape,
    val offset: MutableState<Pair<Float, Float>>,
    val scale: MutableState<Float>,
    val rotation: MutableState<Float>,
    val color: Color,
    val isVisible: MutableState<Boolean> = mutableStateOf(true)
)
