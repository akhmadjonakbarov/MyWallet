package com.akbarovdev.mywallet.features.wallet.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AppSnackBarHostState(
    snackBarHostState: SnackbarHostState,
    snackBarColor: Color = Color.Red,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500)
) {
    Box(
        modifier = Modifier
            .fillMaxSize()

            .padding(15.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        SnackbarHost(

            hostState = snackBarHostState,
            snackbar = { snackBarData ->
                Snackbar(
                    containerColor = snackBarColor,
                    action = {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            onClick = {
                                snackBarHostState.currentSnackbarData?.dismiss()
                            }
                        ) {
                            Icon(
                                Icons.Filled.Clear,
                                "Close Snackbar",
                            )
                        }
                    }
                ) {
                    Text(
                        snackBarData.visuals.message,
                        style = textStyle
                    )
                }
            }
        )
    }
}

