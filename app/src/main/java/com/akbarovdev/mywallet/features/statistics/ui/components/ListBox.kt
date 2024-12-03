package com.akbarovdev.mywallet.features.statistics.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akbarovdev.mywallet.features.wallet.domain.models.ExpanseModel
import com.akbarovdev.mywallet.features.wallet.ui.components.ExpanseItem

@Composable
fun ListBox(

    label: String,
    list: List<ExpanseModel>,
    height: Double,
    labelFontSize: Double
) {
    val configuration = LocalConfiguration.current
    Column(
        modifier = Modifier
            .padding(5.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(
                height.dp
            )
            .padding(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = labelFontSize.sp
            )
        )
        LazyColumn {
            items(list.count()) { index ->
                val item = list[index]
                ExpanseItem(
                    item, configuration = configuration,
                    padding = 10.dp, onLongPress = {}, onDelete = {

                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ListBoxPreview() {
    val configuration = LocalConfiguration.current
    val expanses = listOf(
        ExpanseModel(
            id = 1,
            title = "Car",
            price = 100.0,
            qty = 100.0,
            date = "2022-12-31",
            icon = "car",
            measure = "kg"
        ),
        ExpanseModel(
            id = 2,
            title = "Food",
            price = 50.0,
            qty = 10.0,
            date = "2022-12-30",
            icon = "food",
            measure = "kg"
        ),
        ExpanseModel(
            id = 3,
            title = "Electronics",
            price = 150.0,
            qty = 2.0,
            date = "2022-12-29",
            icon = "electronics",
            measure = "unit"
        ),
    )
//    ListBox(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(
//                (configuration.screenHeightDp * 0.3).dp
//            ),
//        label = "Expenses",
//        list = expanses
//    )
}