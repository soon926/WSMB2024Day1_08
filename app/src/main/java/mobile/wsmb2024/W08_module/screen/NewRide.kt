package mobile.wsmb2024.W08_module.screen

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import mobile.wsmb2024.W08.viewModel.RideViewModel
import mobile.wsmb2024.W08.viewModel.UserViewModel
import mobile.wsmb2024.W08_module.Navigate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRide(
    rideViewModel: RideViewModel,
    userViewModel: UserViewModel,
    navController: NavController
) {

    LaunchedEffect(key1 = true) {
        userViewModel.retrieveUser()

    }

    rideViewModel.driver = userViewModel.userData
    val currentTime = Calendar.getInstance()
    var timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Create A Ride", color = Color.Black, fontSize = 36.sp)
        if (rideViewModel.showDate) {
            DatePickerModal(
                onDateSelected = {
                    rideViewModel.date = SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                    ).format(it)
                    rideViewModel.showDate = false
                },
                onDismiss = { rideViewModel.showDate = false }
            )
        }

        if (rideViewModel.showTime) {
            TimePickerModal(
                onConfirm = {
                    timePickerState = it
                    rideViewModel.time =
                        timePickerState.hour.toString() + ":" + timePickerState.minute.toString()
                },
                onDismiss = {
                    rideViewModel.showTime = false

                })
        }

        ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                contentColor = Color.Black,
                containerColor = Color.White,
                disabledContentColor = Color.White,
                disabledContainerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        label = { Text(text = "Date") },
                        value = rideViewModel.date,
                        onValueChange = {
                            rideViewModel.date
                        })
                    IconButton(onClick = { rideViewModel.showDate = true }) {
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = "date"
                        )
                    }

                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        label = { Text(text = "Time") },
                        value = rideViewModel.time,
                        onValueChange = {
                            rideViewModel.time
                        })
                    IconButton(onClick = { rideViewModel.showTime = true }) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = "time"
                        )
                    }

                }
                OutlinedTextField(
                    label = { Text(text = "Origin") },
                    value = rideViewModel.origin,
                    onValueChange = {
                        rideViewModel.origin = it
                    })

                OutlinedTextField(
                    label = { Text(text = "Destination") },
                    value = rideViewModel.destination,
                    onValueChange = {
                        rideViewModel.destination = it
                    })

                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "Fare") },
                    value = rideViewModel.fare,
                    onValueChange = {
                        rideViewModel.fare = it
                    })
                Button(onClick = { navController.navigate(Navigate.DriverHome.name) }) {
                    Text(text = "Cancel")
                }

                Button(onClick = {
                    rideViewModel.createNewRide()
                    navController.navigate(Navigate.DriverHome.name)
                }) {
                    Text(text = "Create Ride")
                }

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

//    class selectedDateMillis :
//
////    fun isSelectableDate(utcTimeMillis: Long) {
////        return utcTimeMillis >
////    }
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModal(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    TimePickerDialog(
        onDismiss = { onDismiss() },
        onConfirm = {
            onConfirm(timePickerState)
            onDismiss()
        }
    ) {
        TimePicker(
            state = timePickerState,
        )
    }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}