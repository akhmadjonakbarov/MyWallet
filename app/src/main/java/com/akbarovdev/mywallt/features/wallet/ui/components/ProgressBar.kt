package com.akbarovdev.mywallt.features.wallet.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ProgressBar(configuration: Configuration) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .background(Color.LightGray, shape = CircleShape)

        ) {
            Spacer(
                Modifier.height(15.dp)
            )

        }
        Box(
            modifier = Modifier
                .width((configuration.screenWidthDp * 0.6).dp)
                .padding(horizontal = 5.dp)

                .background(
                    color = Color.Blue, // Background color of the Box
                    shape = CircleShape // Shape matching the shadow for consistency
                )


        ) {
            Spacer(
                Modifier.height(15.dp)
            )

        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "4.6%",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 13.sp, fontWeight = FontWeight.W600, color = Color.White
                )
            )
        }
    }
}