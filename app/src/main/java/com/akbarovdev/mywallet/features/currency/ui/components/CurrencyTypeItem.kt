package com.akbarovdev.mywallet.features.currency.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.akbarovdev.mywallet.features.currency.domain.model.CurrencyModel
import kotlinx.coroutines.delay


@Composable
fun CurrencyTypeItem(
    currencyType: CurrencyModel,
    isSelected: Boolean = false,
    onSelect: (CurrencyModel) -> Unit
) {
    val configuration = LocalConfiguration.current
    val scale = animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.0f, animationSpec = tween(durationMillis = 1000),
        label = "Currency Type Item Scale"
    )

    LaunchedEffect(Unit) {
        delay(500)

    }
    Box(
        modifier = Modifier
            .padding(bottom = 5.dp, end = 5.dp, start = 5.dp)
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(15))
            .background(color = Color.White)
            .clickable {
                onSelect(currencyType)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSelected) {
                Icon(
                    modifier = Modifier
                        .scale(
                            scale = scale.value
                        )
                        .clip(shape = CircleShape)
                        .background(color = Color.LightGray)
                        .padding(2.dp),

                    imageVector = Icons.Default.Check,
                    contentDescription = "",
                    tint = Color.Red
                )
            }

            Spacer(modifier = Modifier.width((configuration.screenWidthDp / 60).dp))
            Text(currencyType.name, style = MaterialTheme.typography.labelLarge)
        }
    }
}