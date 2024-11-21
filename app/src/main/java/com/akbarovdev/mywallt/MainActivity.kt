package com.akbarovdev.mywallt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.akbarovdev.mywallt.features.WalletApp
import com.akbarovdev.mywallt.ui.theme.MyWalltTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyWalltTheme {
                WalletApp()
            }
        }
    }
}

