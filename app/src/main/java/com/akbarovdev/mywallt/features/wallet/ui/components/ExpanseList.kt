package com.akbarovdev.mywallt.features.wallet.ui.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.core.Animatable

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.akbarovdev.mywallt.features.wallet.domain.models.ExpanseModel
import com.akbarovdev.mywallt.utils.DateFormatter
import com.akbarovdev.mywallt.utils.IconManager
import com.akbarovdev.mywallt.utils.NumberFormat
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@SuppressLint("NewApi")
@Composable
fun ExpanseList(
    expanses: List<ExpanseModel>,
    configuration: Configuration
) {


    LazyColumn {

        items(count = expanses.count()) { index ->
            val expanse: ExpanseModel = expanses[index]
            ExpanseItem(expanse, configuration)
        }
    }


}


@SuppressLint("NewApi")
@Composable
fun ExpanseItem(expanse: ExpanseModel, configuration: Configuration) {
    // State for alpha animation
    val alphaAnimation = remember { Animatable(0f) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Start the fade-in animation when the composable is launched
        scope.launch {
            alphaAnimation.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 1250,  // Duration of the fade-in effect
                    delayMillis = 250,      // Delay before animation starts
                    easing = LinearEasing   // Easing function for smooth transition
                )
            )
        }
    }



    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                alpha = alphaAnimation.value
            }
            .padding(20.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(color = Color.LightGray)

                        .padding(10.dp)
                ) {
                    Icon(IconManager.iconByName(expanse.icon), contentDescription = null)
                }
                Spacer(Modifier.width((configuration.screenWidthDp / 40).dp))
                Column {
                    Text(
                        expanse.title,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        DateFormatter.formatWithDayHour(LocalDateTime.parse(expanse.date)),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
            Column {
                Text(
                    "${NumberFormat.format(expanse.price * expanse.qty)} so'm",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W700)
                )
                Text(
                    "${expanse.qty} ${expanse.measure}",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W700)
                )
            }
        }
    }
}