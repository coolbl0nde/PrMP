package com.example.calculator.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.calculator.ThemeViewModel
import com.example.calculator.services.ThemeStorageService

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val BlueColorScheme = lightColorScheme(
    primary = Color(0xFFADBEFF),
    secondary = Color(0xFFFF66A1),
    tertiary = Color(0xFFEFA8EA),
    onSecondary = Color(0xFFFFFFFF),
)

private val PinkColorScheme = lightColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFFFFFFFF),
    onSecondary = Color(0xFFFFFFFF),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFB4B4B8),
    secondary = Color(0xFFC7C8CC),
    tertiary = Color(0xFFE3E1D9),
    background = Color(0xFFFFFFFF),
    onSecondary = Color(0xFFFFFFFF),
)


@Composable
fun CalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    themeViewModel: ThemeViewModel,
    // Dynamic color is available on Android 12+
    //dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    //val theme = remember { mutableStateOf("light") }

    val theme = themeViewModel.theme.value

    /*DisposableEffect(Unit) {
        val themeListener = themeViewModel.load { fetchedTheme ->
            theme.value = fetchedTheme
        }
        onDispose { }
    }*/

    val colorScheme = when (theme){
        /*dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme*/

        "pink" -> PinkColorScheme
        "blue" -> BlueColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}