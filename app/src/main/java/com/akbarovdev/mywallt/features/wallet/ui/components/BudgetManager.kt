package com.akbarovdev.mywallt.features.wallet.ui.components

import android.content.res.Configuration
import android.icu.text.NumberFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.akbarovdev.mywallt.features.wallet.ui.view_model.WalletViewModel
import java.util.Locale


@Composable
fun BudgetManager(walletViewModel: WalletViewModel, configuration: Configuration) {
    Column(
        modifier = Modifier
            .height((configuration.screenHeightDp * 0.8).dp)
            .clip(
                shape = RoundedCornerShape(
                    topStart = 25.dp, topEnd = 25.dp
                )
            )

            .background(Color(0xFF8B5DFF))

            .fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Budget()
            }
            Spacer(
                Modifier.height(15.dp)
            )
            ProgressBar(configuration)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 25.dp, topEnd = 25.dp
                    )
                )
                .height((configuration.screenHeightDp * 0.67).dp)
                .background(color = Color.White),
        ) {
            ExpanseList(walletViewModel.expanseState.value.expanses, configuration)
        }
    }
}


@Composable
fun Budget() {
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
                .clickable { },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = NumberFormat.getInstance(Locale("UZ")).format(1000000).toString() + " so'm",
                style = TextStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.W800, color = Color.White
                )
            )
            Icon(
                Icons.Default.Edit, contentDescription = null, tint = Color.White
            )
            Spacer(
                Modifier.width(3.dp)
            )
        }
    }
}


