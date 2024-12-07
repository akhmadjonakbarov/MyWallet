package com.akbarovdev.mywallet.features.statistics.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.akbarovdev.mywallet.R
import com.akbarovdev.mywallet.features.common.components.AlertTextBox
import com.akbarovdev.mywallet.features.common.components.AlertTextBoxAnimation
import com.akbarovdev.mywallet.features.common.components.AppBar
import com.akbarovdev.mywallet.features.currency.ui.view_model.CurrencyManagerViewModel
import com.akbarovdev.mywallet.features.statistics.ui.components.BarChart
import com.akbarovdev.mywallet.features.statistics.ui.components.ListBox
import com.akbarovdev.mywallet.features.statistics.ui.view_model.StatisticViewModel
import kotlinx.coroutines.delay


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
    val isNotEnough = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (state.value.charts.size <= 2) {
            delay(200)
            isNotEnough.value = true
        }

    }

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
                                        currencyType = currencyState.value.currency.name,
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
                            val scale = animateFloatAsState(
                                targetValue = if (isNotEnough.value) 1f else 0f,
                                animationSpec = tween(durationMillis = 1500), label = ""
                            )
                            AlertTextBoxAnimation(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        color = Color.White
                                    )
                            ) {
                                Text(
                                    modifier = Modifier.scale(scale.value),
                                    text = "Data is not enough for generating statistics!",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        color = Color.Black
                                    )
                                )
                            }
                        }
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.description),
                            contentDescription = "",
                        )
                        Spacer(Modifier.height((configuration.screenHeightDp / 95).dp))
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
