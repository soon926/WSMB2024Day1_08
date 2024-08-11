package mobile.wsmb2024.W08.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Button
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import mobile.wsmb2024.W08.viewModel.UserViewModel
import mobile.wsmb2024.W08_module.Navigate

@Composable
fun Register(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val userUiState by userViewModel.uiState.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {

        when (userViewModel.currentStep) {
            1 -> Step1(userViewModel,
                onNext = {
                    userViewModel.currentStep = 2
                    userViewModel.uploadImage(userViewModel.photo.toUri())
                })

            2 -> Step2(
                userViewModel,
                onPrev = { userViewModel.currentStep = 1 },
                onNext = { userViewModel.currentStep = 3 },
                onSubmit = {
                    userViewModel.registerUser()
                    navController.navigate(Navigate.Login.name)
                }
            )

            3 -> Step3(
                userViewModel,
                onPrev = { userViewModel.currentStep = 2 },
                onSubmit = {
                    userViewModel.registerUser()
                    navController.navigate(Navigate.Login.name)
                })
        }
    }
}

@Composable
fun Step3(
    userViewModel: UserViewModel,
    onPrev: () -> Unit,
    onSubmit: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Register", fontSize = 36.sp)
            Text(text = "Step 3: Enter Vehicle Details ")
            Text(text = "")

            ElevatedCard(
                colors = CardDefaults.elevatedCardColors(
                    contentColor = Color.White,
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

                    OutlinedTextField(
                        label = { Text(text = "Car Model") },
                        value = userViewModel.model,
                        onValueChange = { userViewModel.model = it })
                    OutlinedTextField(
                        label = { Text(text = "Capacity") },
                        value = userViewModel.capacity,
                        onValueChange = { userViewModel.capacity = it })
                    OutlinedTextField(
                        label = { Text(text = "Special Features") },
                        value = userViewModel.special,
                        onValueChange = { userViewModel.special = it })

                    Button(onClick = onPrev) {
                        Text(text = "Previous")
                    }

                    Button(onClick = onSubmit) {
                        Text(text = "Submit")

                    }
                }
            }
        }
    }
}

@Composable
fun Step2(
    userViewModel: UserViewModel,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Register", fontSize = 36.sp)
        Text(text = "Step 2: Enter Personal Detail")

        ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                contentColor = Color.White,
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

                OutlinedTextField(
                    label = { Text(text = "Name") },
                    value = userViewModel.name,
                    onValueChange = { userViewModel.name = it })
                OutlinedTextField(
                    label = { Text(text = "Gender") },
                    value = userViewModel.gender,
                    onValueChange = { userViewModel.gender = it })
                OutlinedTextField(
                    label = { Text(text = "Phone") },
                    value = userViewModel.phone,
                    onValueChange = { userViewModel.phone = it })
                OutlinedTextField(
                    label = { Text(text = "Address") },
                    value = userViewModel.address,
                    onValueChange = { userViewModel.address = it })
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Register As Driver?", color = Color.Black)
                    Spacer(modifier = Modifier.width(64.dp))
                    Switch(
                        checked = userViewModel.isDriver,
                        onCheckedChange = { userViewModel.isDriver = it })
                }

                Button(onClick = onPrev) {
                    Text(text = "Previous")
                }
                if (userViewModel.isDriver) {
                    Button(onClick = onNext) {
                        Text(text = "Next")
                    }
                } else {
                    Button(onClick = onSubmit) {
                        Text(text = "Submit")
                    }
                }
            }
        }
    }
}

@Composable
fun Step1(userViewModel: UserViewModel, onNext: () -> Unit) {

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            if (userViewModel.photo != null) {
                userViewModel.photo = it.toString()
            }
        })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Register", fontSize = 36.sp)
        Text(text = "Step 1: Create Account & Password")


        ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                contentColor = Color.White,
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
                if (userViewModel.photo != "") {
                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        model = userViewModel.photo, contentDescription = "profile",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(50))
                            .border(1.dp, Color.Black, CircleShape)
                            .clickable(onClick = {
                                photoPicker.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            })
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .height(150.dp)
                            .width(150.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(50))
                            .clickable(onClick = {
                                photoPicker.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            })
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()

                        ) {
                            Icon(imageVector = Icons.Default.AddAPhoto, contentDescription = "")
                            Text(text = "Add Photo")
                        }
                    }
                }
                OutlinedTextField(
                    label = { Text(text = "IC") },
                    value = userViewModel.ic,
                    onValueChange = { userViewModel.ic = it })
                OutlinedTextField(
                    label = { Text(text = "Email") },
                    value = userViewModel.email,
                    onValueChange = { userViewModel.email = it })
                OutlinedTextField(
                    label = { Text(text = "Password") },
                    value = userViewModel.password,
                    onValueChange = { userViewModel.password = it })

                Button(onClick = onNext) {
                    Text(text = "Next")
                }
            }
        }
    }
}

