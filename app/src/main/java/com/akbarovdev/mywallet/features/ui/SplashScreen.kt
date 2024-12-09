package com.akbarovdev.mywallet.features.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akbarovdev.mywallet.core.helper.SnackBarManager
import com.akbarovdev.mywallet.core.navigation.Screens
import com.akbarovdev.mywallet.features.profile.view_model.ProfileViewModel
import com.akbarovdev.mywallet.features.wallet.ui.components.AppSnackBarHostState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    navController: NavController,
    snackBarManager: SnackBarManager,
    profileViewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val configuration = LocalConfiguration.current
    val username = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val state = profileViewModel.state.collectAsState()

    var firstIsVisible = remember { mutableStateOf(false) }
    var secondIsVisible = remember { mutableStateOf(false) }
    var thirdIsVisible = remember { mutableStateOf(false) }
    val animationDuration = 1000 // Duration of the transition in milliseconds

    // Animating the X offset: moves the element from left to center
    val offsetX = animateDpAsState(
        targetValue = if (firstIsVisible.value) 0.dp else (350).dp, // Start off-screen on the left
        animationSpec = tween(durationMillis = animationDuration)
    )
    // Animating the Y offset: moves the element from bottom to center
    val offsetY = animateDpAsState(
        targetValue = if (firstIsVisible.value) 0.dp else (-550).dp, // Start off-screen below
        animationSpec = tween(durationMillis = animationDuration)
    )


    // Box 2 animations
    val offsetX2 = animateDpAsState(
        targetValue = if (secondIsVisible.value) 350.dp else 800.dp,
        animationSpec = tween(durationMillis = animationDuration)
    )
    val offsetY2 = animateDpAsState(
        targetValue = if (secondIsVisible.value) -550.dp else -800.dp,
        animationSpec = tween(durationMillis = animationDuration)
    )


    // scale text
    // Scaling animation: scales up from 0.5x to 1x
    val scale = animateFloatAsState(
        targetValue = if (thirdIsVisible.value) 1f else 0.1f, // Scale from half-size to full-size
        animationSpec = tween(durationMillis = (animationDuration + 700))
    )

    fun navigateWalletScreen() {
        navController.navigate(Screens.wallet) {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
        }
    }

    fun submit() {
        keyboardController?.hide()
        if (username.value.isEmpty()) {
            snackBarManager.showSnackBar(
                "Please enter your name!", isAlert = true
            )
            return
        }
        profileViewModel.setName(username.value)
        snackBarManager.showSnackBar(
            "Your account has been created successfully"
        )
        scope.launch {
            delay(1500)
            navigateWalletScreen()
        }
    }



    LaunchedEffect(Unit) {

        if (state.value.username.isNotEmpty()) {
            delay(3000)
            navigateWalletScreen()
        }
    }

    LaunchedEffect(Unit) {
        thirdIsVisible.value = true
        delay(500)
        firstIsVisible.value = true
        delay(700)
        secondIsVisible.value = true
    }



    Scaffold(
        modifier = Modifier.fillMaxSize(), snackbarHost =  {
            AppSnackBarHostState(
                snackBarHostState = snackBarManager.snackBarHostState,
                snackBarColor = snackBarManager.snackBarColor,
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Box(
                modifier = Modifier
                    .height(250.dp)
                    .width(85.dp)
                    .rotate(-120f)
                    .offset(x = offsetX.value, y = offsetY.value)
                    .background(
                        color = Color.Blue, shape = RoundedCornerShape(25)
                    )
            )
            Box(
                modifier = Modifier
                    .height(250.dp)
                    .width(85.dp)
                    .rotate(120f)
                    .offset(x = offsetX2.value, y = offsetY2.value)
                    .background(
                        color = Color.Blue, shape = RoundedCornerShape(25)
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = scale.value
                        scaleY = scale.value
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state.value.username.isNotEmpty()) {
                    Text(
                        "Hello ${state.value.username}",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Text(
                    "Welcome to Budgetify", style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(
                    modifier = Modifier.height((configuration.screenHeightDp / 120).dp)
                )
                Text(
                    "Start managing your expenses efficiently"
                )
                Spacer(
                    modifier = Modifier.height((configuration.screenHeightDp / 50).dp)
                )
                if (state.value.username.isEmpty()) {
                    TextField(value = username.value, onValueChange = {
                        username.value = it
                    }, placeholder = {
                        Text("Enter the name")
                    }, modifier = Modifier.border(
                        width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(15)
                    ), colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent, // Removes the underline when focused
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = Color.Transparent
                    )
                    )
                    Spacer(
                        modifier = Modifier.height((configuration.screenHeightDp / 50).dp)
                    )
                    Button(modifier = Modifier
                        .width((configuration.screenWidthDp * 0.5).dp)
                        .height(
                            (configuration.screenHeightDp / 18).dp
                        ), shape = RoundedCornerShape(15), onClick = {
                        submit()
                    }) {
                        Text("Let's Get Started")
                    }
                }
            }
        }
    }

}


@Preview
@Composable
fun SplashScreenPreview() {


    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .height(250.dp)
                    .width(85.dp)
                    .rotate(-120f)
                    .offset(
                        x = (-75).dp, y = (-25).dp
                    )
                    .background(
                        color = Color.Blue, shape = RoundedCornerShape(25)
                    )
            )
            Box(
                modifier = Modifier
                    .height(250.dp)
                    .width(85.dp)
                    .rotate(120f)
                    .offset(
                        x = (350).dp, y = (-550).dp
                    )
                    .background(
                        color = Color.Blue, shape = RoundedCornerShape(25)
                    )
            )


        }
    }
}