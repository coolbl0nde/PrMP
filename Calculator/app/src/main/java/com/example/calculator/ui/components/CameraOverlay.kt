package com.example.calculator.ui.components

import androidx.compose.ui.geometry.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

@Composable
fun CameraOverlay(onScanAreaDefined: (Rect) -> Unit){
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val offsetVertical = 700f
        val offsetHorizontal = 50f

        Canvas(modifier = Modifier.fillMaxSize()){
            val dashLength = 10.dp.toPx()
            val spaceLength = 10.dp.toPx()
            val paint = Paint().apply {
                color = Color.White
                style = PaintingStyle.Stroke
                strokeWidth = 2.dp.toPx()
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, spaceLength), phase = 0f)
            }

            drawRect(
                color = Color.Black.copy(alpha = 0.5f),
                topLeft = Offset(0f, 0f),
                size = Size(size.width, offsetVertical),
                style = Fill
            )

            drawRect(
                color = Color.Black.copy(alpha = 0.5f),
                topLeft = Offset(0f, size.height-offsetVertical),
                size = Size(size.width, offsetVertical),
                style = Fill
            )

            drawRect(
                color = Color.Black.copy(alpha = 0.5f),
                topLeft = Offset(0f, 0f + offsetVertical),
                size = Size(
                    offsetHorizontal,
                    size.height - 2 * offsetVertical
                ),
                style = Fill
            )

            drawRect(
                color = Color.Black.copy(alpha = 0.5f),
                topLeft = Offset(size.width - offsetHorizontal, 0f + offsetVertical),
                size = Size(
                    offsetHorizontal,
                    size.height - 2 * offsetVertical
                ),
                style = Fill
            )

            /*drawRect(
                color = Color.White,
                topLeft = Offset(
                    0 + offsetHorizontal,
                    0f + offsetVertical
                ),
                size = Size(
                    size.width - 2 * offsetHorizontal,
                    size.height - 2 * offsetVertical
                ),
                style = Stroke()
            )*/

            val scanArea = Rect(
                offsetHorizontal,
                offsetVertical,
                size.width - offsetHorizontal,
                size.height - offsetVertical,
            )

            onScanAreaDefined(scanArea)

            drawContext.canvas.nativeCanvas.drawRect(
                offsetHorizontal,
                offsetVertical,
                size.width - offsetHorizontal,
                size.height - offsetVertical,
                paint.asFrameworkPaint()
            )
        }
    }
}
