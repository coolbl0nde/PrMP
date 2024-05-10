package com.example.calculator.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.DialogProperties

@Composable
fun ChangeKeyDialog(
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit,
    deleteKey: (Boolean) -> Unit,
    onConfirm: (String) -> Unit
){

    var newPassword by remember { mutableStateOf("") }

    if (showDialog){
        AlertDialog(
            onDismissRequest = { setShowDialog(false) },
            title = {
                Text(text = "Settings")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = {
                            newPassword = it
                        },
                        label = { Text(text = "Введите новый пароль") }
                    )

                    Button(onClick = { deleteKey(true) }) {
                        Text(text = "Delete Password")
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    onConfirm(newPassword)
                    newPassword = ""
                    setShowDialog(false)
                }) {
                    Text(text = "Поменять")
                }
            },
            dismissButton = {
                Button(onClick = { setShowDialog(false) }) {
                    Text(text = "Отмена")
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }

}