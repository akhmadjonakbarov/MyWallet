package com.akbarovdev.mywallet.features.wallet.ui.components

import android.icu.text.NumberFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale


@Composable
fun Budget(
    balance: Double,
    openDialog: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Monthly Budget:", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W600)
        )
        Spacer(
            Modifier.width(10.dp)
        )
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(15.dp))
                .clickable { openDialog() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            when {
                balance != 0.0 -> {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = NumberFormat.getInstance(Locale("UZ")).format(balance)
                            .toString() + " so'm",
                        style = TextStyle(
                            fontSize = 18.sp, fontWeight = FontWeight.W800, color = Color.White
                        )
                    )
                }

                else -> {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = NumberFormat.getInstance(Locale("UZ")).format(0)
                            .toString() + " so'm",
                        style = TextStyle(
                            fontSize = 18.sp, fontWeight = FontWeight.W800, color = Color.White
                        )
                    )
                }
            }
            Icon(
                Icons.Default.Edit, contentDescription = null, tint = Color.White
            )
            Spacer(
                Modifier.width(3.dp)
            )
        }
    }
}