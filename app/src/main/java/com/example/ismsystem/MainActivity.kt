package com.example.ismsystem

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.roundToInt
import kotlin.text.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: InteractiveShapesViewModel = viewModel()
            InteractiveShapesScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun InteractiveShapesScreen(viewModel: InteractiveShapesViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        viewModel.shapes.filter { it.isVisible.value }.forEach { shape ->
            ShapeWithProperties(
                shape = shape,
                onRemove = { viewModel.removeShape(shape.id) }
            )
        }

        Button(
            onClick = { viewModel.addShape() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            Text("Create Shape (${viewModel.shapes.size}/10)")
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun ShapeWithProperties(
    shape: ShapeProperties,
    onRemove: () -> Unit
) {
    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    shape.offset.value.first.roundToInt(),
                    shape.offset.value.second.roundToInt()
                )
            }
    ) {
        Column(
            modifier = Modifier
                .offset(y = (-60).dp)
                .background(Color(0x88FFFFFF))
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "X: ${shape.offset.value.first.roundToInt()}, Y: ${shape.offset.value.second.roundToInt()}",
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
            Text(
                "Rotation: ${shape.rotation.value.roundToInt()}°",
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
            Text(
                "Scale: ${String.format("%.2f", shape.scale.value)}",
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = shape.scale.value,
                    scaleY = shape.scale.value,
                    rotationZ = shape.rotation.value
                )
                .size(50.dp)
                .background(shape.color, shape.shape)
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, zoom, rotationDelta ->
                        shape.scale.value = (shape.scale.value * zoom).coerceIn(0.5f, 2.0f)

                        shape.offset.value = Pair(
                            shape.offset.value.first + pan.x,
                            shape.offset.value.second + pan.y
                        )

                        shape.rotation.value = (shape.rotation.value + rotationDelta) % 360f
                    }
                }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = { onRemove() }
                    )
                }
        )
    }
}

