package mobile.wsmb2024.W08.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mobile.wsmb2024.W08.viewModel.UserViewModel
import mobile.wsmb2024.W08_module.Navigate
import mobile.wsmb2024.W08_module.Navigation

@Composable
fun Login(
    userViewModel: UserViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var showPassword by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Login", color = Color.Black, fontSize = 36.sp)
        Spacer(modifier = Modifier.height(8.dp))

        ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                contentColor = Color.Black,
                containerColor = Color.White,
                disabledContentColor = Color.White,
                disabledContainerColor = Color.White
            ),
            modifier = Modifier.padding(8.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    label = { Text(text = "Email") },
                    value = userViewModel.email,
                    onValueChange = { userViewModel.email = it })
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    trailingIcon = {
                        Icon(
                            if (userViewModel.showPassword) {
                                Icons.Filled.Visibility
                            } else {
                                Icons.Filled.VisibilityOff
                            },
                            contentDescription = "Toggle password visibility",
                            modifier = Modifier.clickable {
                                userViewModel.showPassword = !userViewModel.showPassword
                            })
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    label = { Text(text = "Password") },
                    value = userViewModel.password,
                    onValueChange = { userViewModel.password = it })
                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = { userViewModel.signIn(navController) }) {
                    Text(text = "Login")
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            color = Color.Black,
            text = "Forget Pasword?",
            modifier = Modifier.clickable(
                onClick = { navController.navigate(Navigate.Register.name) })
        )
        Text(
            color = Color.Red,
            text = userViewModel.errorLogin

        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Register As Driver/Rider",
            modifier = Modifier.clickable(
                onClick = { navController.navigate(Navigate.Register.name) })
        )

    }
}