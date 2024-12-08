package com.akbarovdev.mywallet.features.debt.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.akbarovdev.mywallet.features.debt.domain.models.PersonModel


@SuppressLint("NewApi", "MutableCollectionMutableState")
@Composable
fun PersonEditDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onSave: (PersonModel) -> Unit,
    personModel: PersonModel? = null
) {


    var nameError by remember { mutableStateOf<String?>(null) }
    var phoneNumberError by remember { mutableStateOf<String?>(null) }



    if (isOpen) {
        var name by remember { mutableStateOf("") }
        var phoneNumber by remember { mutableStateOf("") }


        if (personModel != null) {
            name = personModel.name
            phoneNumber = personModel.phoneNumber
        }

        fun validateFields(): Boolean {
            nameError = if (name.isEmpty()) "Name cannot be empty" else null
            phoneNumberError =
                if (phoneNumber.isEmpty() || phoneNumber.toDoubleOrNull() == null) "Phone number must be a valid number" else null


            return nameError == null && phoneNumberError == null
        }





        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = if (false) "Edit Person" else "Add Person")
            },
            text = {
                Column {
                    // Title TextField
                    TextField(
                        value = name,
                        onValueChange = {
                            name = it
                            nameError = if (it.isEmpty()) "Name cannot be empty" else null
                        },
                        label = { Text("Name") },
                        isError = nameError != null
                    )
                    if (nameError != null) {
                        Text(
                            text = nameError.orEmpty(),
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // Amount TextField
                    TextField(
                        value = phoneNumber.toString(),
                        onValueChange = {
                            phoneNumber = it
                            phoneNumberError = if (it.isEmpty() || it.toDoubleOrNull() == null) {
                                "Phone number must be a valid number"
                            } else {
                                null
                            }
                        },
                        placeholder = { Text("+998 90 123 45 67") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = phoneNumberError != null
                    )
                    if (phoneNumberError != null) {
                        Text(
                            text = phoneNumberError.orEmpty(),
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (validateFields()) {
                            onSave(
                                PersonModel(
                                    name = name,
                                    phoneNumber = phoneNumber
                                )
                            )
                            onDismiss()
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            },
        )

    }
}