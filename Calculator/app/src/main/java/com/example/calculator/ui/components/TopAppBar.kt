package com.example.calculator.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.calculator.ui.theme.LocalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(){

    var showMenu by remember { mutableStateOf(false) }
    val currentTheme = LocalTheme.current

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.tertiary,
        ),

        title = {
            Text(text = "calculator")
        },

        actions = {
            IconButton(onClick = { showMenu = true }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
            
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "barby core") },
                    onClick = {
                        showMenu = false
                        currentTheme.value = "pink"
                    }
                )

                DropdownMenuItem(
                    text = { Text(text = "blue theme") },
                    onClick = {
                        showMenu = false
                        currentTheme.value = "blue"
                    }
                )
            }
        }
    )


    
}