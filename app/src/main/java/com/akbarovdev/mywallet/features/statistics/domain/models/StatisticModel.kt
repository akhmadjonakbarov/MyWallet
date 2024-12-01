package com.akbarovdev.mywallet.features.statistics.domain.models

import com.akbarovdev.mywallet.features.wallet.domain.models.ExpanseModel

data class StatisticModel(
    val label: String,
    val list: List<ExpanseModel>
)