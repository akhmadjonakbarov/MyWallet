package com.akbarovdev.mywallt.features.wallet.ui.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akbarovdev.mywallt.features.wallet.ui.view_model.WalletViewModel
import com.akbarovdev.mywallt.utils.DateFormatter
import java.time.LocalDate

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@SuppressLint("NewApi")
@Composable
fun Balance(
    walletViewModel: WalletViewModel,
    balance: Double = 0.0, configuration: Configuration
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    DateFormatter.format(LocalDateTime.now()),
                    style = TextStyle(fontSize = 19.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconBtn(onClick = {}, icon = {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null
                        )
                    })
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            com.akbarovdev.mywallt.utils.NumberFormat.format(balance),
                            style = TextStyle(
                                fontSize = 25.sp
                            )
                        )
                        Text("so'm", style = TextStyle(fontWeight = FontWeight.Bold))
                    }
                    IconBtn(onClick = {}, icon = {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null
                        )
                    })
                }
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
            BudgetManager(walletViewModel, configuration)
        }
    }

}