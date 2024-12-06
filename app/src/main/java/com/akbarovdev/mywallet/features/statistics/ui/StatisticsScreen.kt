package com.akbarovdev.mywallet.features.statistics.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.akbarovdev.mywallet.R
import com.akbarovdev.mywallet.features.common.components.AlertTextBox
import com.akbarovdev.mywallet.features.common.components.AppBar
import com.akbarovdev.mywallet.features.currency.ui.view_model.CurrencyManagerViewModel
import com.akbarovdev.mywallet.features.statistics.ui.components.BarChart
import com.akbarovdev.mywallet.features.statistics.ui.components.ListBox
import com.akbarovdev.mywallet.features.statistics.ui.view_model.StatisticViewModel


@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    navController: NavController,
    statisticViewModel: StatisticViewModel = hiltViewModel<StatisticViewModel>(),
    currencyManagerViewModel: CurrencyManagerViewModel = hiltViewModel<CurrencyManagerViewModel>()
) {

    val configuration = LocalConfiguration.current
    val state = statisticViewModel.state.collectAsState()
    val currencyState = currencyManagerViewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = "Statistic", navController = navController
            )
        }, containerColor = Color.LightGray
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            when {
                state.value.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.value.charts.isNotEmpty() -> {
                    when {
                        state.value.charts.size > 2 -> {
                            LazyColumn {
                                item {
                                    BarChart(
                                        charts = state.value.charts,
                                        height = (configuration.screenHeightDp * 0.3),
                                    )
                                }
                                items(state.value.statistics.count()) { index ->
                                    val item = state.value.statistics[index]
                                    ListBox(
                                        list = item.list,
                                        label = item.label,
                                        currencyType = currencyState.value.currency.name,
                                        height = configuration.screenHeightDp * 0.4,
                                        labelFontSize = configuration.screenWidthDp * 0.05
                                    )
                                }
                            }
                        }

                        else -> {
                            AlertTextBox("Data is not enough for generating statistics!")

                        }
                    }
                }

                else -> {
                    Column {
                        Image(
                            painter = painterResource(R.drawable.description),
                            contentDescription = ""
                        )
                        AlertTextBox("Statistics does not exist")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun StatisticsScreenPreview() {
    StatisticsScreen(rememberNavController())
}
