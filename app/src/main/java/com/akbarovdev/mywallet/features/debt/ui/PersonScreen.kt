package com.akbarovdev.mywallet.features.debt.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.akbarovdev.mywallet.core.helper.SnackBarManager
import com.akbarovdev.mywallet.features.common.components.AlertTextBoxAnimation
import com.akbarovdev.mywallet.features.common.components.AppBar
import com.akbarovdev.mywallet.features.common.components.DeleteButton
import com.akbarovdev.mywallet.features.common.components.EditButton
import com.akbarovdev.mywallet.features.debt.domain.models.DebtModel
import com.akbarovdev.mywallet.features.debt.domain.models.PersonModel
import com.akbarovdev.mywallet.features.debt.ui.components.PersonEditDialog
import com.akbarovdev.mywallet.features.debt.ui.view_models.PersonViewModel
import com.akbarovdev.mywallet.features.wallet.ui.components.AppSnackBarHostState
import com.akbarovdev.mywallet.features.wallet.ui.components.FloatButton
import com.akbarovdev.mywallet.utils.NumberFormat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@SuppressLint("NewApi")
@Composable
fun PersonScreen(
    navController: NavController,
    snackBarManager: SnackBarManager,
    personViewModel: PersonViewModel = hiltViewModel<PersonViewModel>(),
    navigateDetail: (PersonModel) -> Unit
) {
    val alphaAnimation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    val personName = remember { mutableStateOf("") }


    var deletedDialog by remember {
        mutableStateOf(false)
    }







    LaunchedEffect(Unit) {
        // Start the fade-in animation when the composable is launched
        scope.launch {
            alphaAnimation.animateTo(
                targetValue = 1f, animationSpec = tween(
                    durationMillis = 1250,  // Duration of the fade-in effect
                    delayMillis = 250,      // Delay before animation starts
                    easing = LinearEasing   // Easing function for smooth transition
                )
            )
        }
    }

    val configuration = LocalConfiguration.current
    val state = personViewModel.state.collectAsState()
    val isNotEnough = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(200)
        isNotEnough.value = true
    }


    Scaffold(
        topBar = {
            AppBar(navController, title = "Persons")
        }, snackbarHost = {
            AppSnackBarHostState(
                snackBarManager.snackBarHostState, snackBarManager.snackBarColor
            )
        },

        floatingActionButton = {
            FloatButton {
                personViewModel.openDialog()
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = (configuration.screenHeightDp / 95).dp)
                    .fillMaxWidth()
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = personName.value,
                    onValueChange = {
                        personName.value = it
                        personViewModel.searchByName(personName.value)
                    },
                    maxLines = 1,
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "")
                    },
                    placeholder = {
                        Text("Search person")
                    }
                )
            }


            when {

                state.value.isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }


                state.value.persons.isNotEmpty() -> {
                    LazyColumn {
                        items(state.value.persons.size) {
                            val person = state.value.persons[it]
                            PersonItem(
                                person = person,
                                configuration = configuration,
                                navigateDetail = {
                                    navigateDetail(it)
                                },
                                onDelete = {
                                    deletedDialog = true
                                    personViewModel.selectPerson(it)
                                },
                                onEdit = {
                                    personViewModel.selectPerson(it) {
                                        personViewModel.openDialog()
                                    }
                                }
                            )
                        }
                    }

                }

                else -> {
                    val scale = animateFloatAsState(
                        targetValue = if (isNotEnough.value) 1f else 0f,
                        animationSpec = tween(durationMillis = 1500),
                        label = ""
                    )
                    AlertTextBoxAnimation(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(scale.value)
                    ) {
                        Text(
                            "No Persons!", style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }
        }


    }


    if (personViewModel.isOpenDialog.value) {
        PersonEditDialog(
            personModel = state.value.selectedPerson,
            isOpen = personViewModel.isOpenDialog.value,
            onDismiss = { personViewModel.closeDialog() },
            onSave = {
                val selectedPerson = state.value.selectedPerson
                if (selectedPerson.id != -1) {
                    personViewModel.update(it)
                } else {
                    personViewModel.add(it)
                }

            }
        )
    }

    if (deletedDialog) {
        AlertDialog(
            title = {
                Text(
                    state.value.selectedPerson.name,
                )
            },
            text = {
                Text("Do you want to delete?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        personViewModel.delete(state.value.selectedPerson)
                        deletedDialog = false
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Icon(Icons.Outlined.Delete, contentDescription = "Delete")
                }
            },
            dismissButton = {
                Button(onClick = {
                    deletedDialog = false
                    personViewModel.selectPerson()
                }) {
                    Icon(Icons.Outlined.Close, contentDescription = "Delete")
                }
            },
            onDismissRequest = {
                deletedDialog = false
                personViewModel.selectPerson()
            },
        )
    }
}


@Composable
fun PersonItem(
    person: PersonModel,
    configuration: Configuration,
    navigateDetail: (PersonModel) -> Unit,
    onDelete: (PersonModel) -> Unit,
    onEdit: (PersonModel) -> Unit
) {
    var isSelected = remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                navigateDetail(person)
            }, onDoubleTap = {
                isSelected.value = !isSelected.value
            })

        }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()

                .padding(
                    8.dp
                ), contentAlignment = Alignment.Center
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
                            .padding(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Person, contentDescription = null
                        )
                    }
                    Spacer(Modifier.width((configuration.screenWidthDp / 40).dp))
                    Column {
                        Text(
                            person.name.capitalize(Locale.ROOT),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            person.phoneNumber, style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                Row {
                    EditButton {
                        onEdit(person)
                    }
                    DeleteButton {
                        onDelete(person)
                    }
                }
            }
        }
        HorizontalDivider()
    }
}


@Preview
@Composable
fun PersonScreenPreview() {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val snackBarManager =
        remember { SnackBarManager(snackBarHostState, scope) }
    PersonScreen(
        navController = rememberNavController(), snackBarManager = snackBarManager
    ) {

    }
}