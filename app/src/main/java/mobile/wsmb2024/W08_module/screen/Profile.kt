package mobile.wsmb2024.W08_module.screen

import android.provider.ContactsContract.Profile
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import mobile.wsmb2024.W08.viewModel.UserViewModel

@Composable
fun Profile(userViewModel: UserViewModel, navController: NavController) {

    LaunchedEffect(key1 = true) {
        userViewModel.retrieveUser()
    }

    var isValid by remember { (mutableStateOf(false)) }
    val user = userViewModel.userData

    var password by remember {
        mutableStateOf("")
    }

    if (isValid) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        model = user.photo, contentDescription = "profile",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(50))
                            .border(1.dp, Color.Black, CircleShape)

                    )
                    Text(text = "Name: ${user.name}")
                    Text(text = "IC: ${user.ic}")
                    Text(text = "Email: ${user.email}")
                    Text(text = "Gender: ${user.gender}")
                    Text(text = "Phone: ${user.phone}")
                    Text(text = "Address: ${user.address}")

                    if (user.isDriver) {
                        val text = "Driver"
                        Text(text = "Status: $text")
                        Text(text = "Car Model: ${user.model}")
                        Text(text = "Car Capacity: ${user.capacity}")
                        Text(text = "Special Feature: ${user.special}")
                    } else {
                        val text = "Rider"
                        Text(text = "Status: $text")
                    }

                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Enter Password For Verification")
            OutlinedTextField(
                label = { Text(text = "Password") },
                value = password, onValueChange = { password = it })
            Button(onClick = {
                if (password == user.password) {
                    isValid = true
                }
            }) {
                Text(text = "Submit")
            }
        }
    }
}