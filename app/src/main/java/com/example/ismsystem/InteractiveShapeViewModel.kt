package com.example.ismsystem

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.lifecycle.ViewModel

class InteractiveShapesViewModel : ViewModel() {
    var shapes by mutableStateOf(
        listOf(
            ShapeProperties(
                id = 0,
                shape = CircleShape,
                offset = mutableStateOf(Pair(100f, 100f)),
                scale = mutableFloatStateOf(1f),
                rotation = mutableFloatStateOf(0f),
                color = Color.Blue
            ),
            ShapeProperties(
                id = 1,
                shape = RectangleShape,
                offset = mutableStateOf(Pair(200f, 100f)),
                scale = mutableFloatStateOf(1f),
                rotation = mutableFloatStateOf(0f),
                color = Color.Red
            )
        )
    )
        private set

    private var nextId by mutableIntStateOf(2)

    fun removeShape(shapeId: Int) {
        val shape = shapes.find { it.id == shapeId }
        shape?.isVisible?.value = false
        shapes = shapes.filter { it.isVisible.value }
    }

    fun addShape() {
        if (shapes.size < 10) {
            val isCircle = nextId % 2 == 0
            val newShape = ShapeProperties(
                id = nextId,
                shape = if (isCircle) CircleShape else RectangleShape,
                offset = mutableStateOf(Pair(150f + (nextId * 20f), 150f + (nextId * 20f))),
                scale = mutableFloatStateOf(1f),
                rotation = mutableFloatStateOf(0f),
                color = if (isCircle) Color.Blue else Color.Red
            )
            shapes = shapes + newShape
            nextId++
        }
    }
}