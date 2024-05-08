package com.example.calculator.ui.screens

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.calculator.CalculatorViewModel
import com.example.calculator.ui.components.CameraOverlay
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.navigation.NavController
import java.io.OutputStream
import kotlin.math.roundToInt


@Composable
fun CameraScreen(viewModel: CalculatorViewModel, navController: NavController) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = remember { ImageCapture.Builder().build() }

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())

        var scanArea by remember { mutableStateOf<Rect?>(null) }

        CameraOverlay(onScanAreaDefined = { area ->
            scanArea = area
        })

        //CameraOverlay()

        Row {
            Button(onClick = { captureImage(
                imageCapture, context, viewModel, navController, scanArea!!
            ) }) {
                Text("Take Photo")
            }
            Button(onClick = {navController.navigate("adaptiveCalculatorUI")}) {
                Text("Exit")
            }
        }
    }
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}

private fun captureImage(
    imageCapture: ImageCapture,
    context: Context,
    viewModel: CalculatorViewModel,
    navController: NavController,
    scanArea: Rect
) {
    val name = "CameraxImage.jpeg"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
        }
    }

    val outputOptions = ImageCapture.OutputFileOptions.Builder(
        context.contentResolver,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    ).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val uri = outputFileResults.savedUri
                val image = outputFileResults.savedUri?.let { uri ->
                    InputImage.fromFilePath(context, uri)
                }

                if (image != null) {
                    val croppedImage = cropInputImage(image, scanArea, context, uri!!)

                    recognizeText(croppedImage, viewModel)

                    //context.contentResolver.delete(uri, null, null)

                    navController.navigate("adaptiveCalculatorUI");
                } else {
                    viewModel.userInput = "Image capture failed"
                }
            }

            override fun onError(exception: ImageCaptureException) {}
        }
    )
}

private fun recognizeText(image: InputImage, viewModel: CalculatorViewModel) {
    val options = TextRecognizerOptions.Builder().build()
    val recognizer = TextRecognition.getClient(options)

    recognizer.process(image)
        .addOnSuccessListener { visionText ->
            if (visionText.text.isNotEmpty()) {
                viewModel.onSymbolClicked(visionText.text)
            }
            else {
                viewModel.onSymbolClicked("empty")
            }
        }
        .addOnFailureListener { exception ->
            viewModel.userInput = "Text recognition failed"
        }
}

fun cropInputImage(
    image: InputImage,
    scanArea: Rect,
    context: Context,
    uri: Uri
): InputImage {
    val bitmap = when {
        image.bitmapInternal != null -> image.bitmapInternal

        else -> {
            val resolver = context.contentResolver
            val stream = resolver.openInputStream(uri)
            Bitmap.createBitmap(BitmapFactory.decodeStream(stream))
        }
    }

    val croppedBitmap = Bitmap.createBitmap(
        bitmap!!,
        (scanArea.left * 12).roundToInt(),
        (scanArea.top * 1.7).roundToInt(),
        (scanArea.width + 850).roundToInt(),
        (scanArea.height + 750).roundToInt()
    )


    //return saveCroppedImage(context, croppedBitmap, "cropped_image.jpeg")
    return InputImage.fromBitmap(croppedBitmap, 0)
}

fun saveCroppedImage(
    context: Context,
    croppedBitmap: Bitmap,
    fileName: String
): Uri? {
    // Подготовка данных для нового изображения в медиа хранилище
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/Calculator")
        }
    }

    // Получение URI для файла из медиа хранилища
    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    uri?.let {
        var outputStream: OutputStream? = null
        try {
            outputStream = resolver.openOutputStream(it)
            croppedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream!!)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            outputStream?.close()
        }
    }
    return uri
}
