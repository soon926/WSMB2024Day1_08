package mobile.wsmb2024.W08_module.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CarRental
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mobile.wsmb2024.W08.uiState.RideUiState
import mobile.wsmb2024.W08.viewModel.RideViewModel
import mobile.wsmb2024.W08.viewModel.UserViewModel
import mobile.wsmb2024.W08_module.Navigate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverHome(
    userViewModel: UserViewModel,
    rideViewModel: RideViewModel,
    navController: NavController
) {

//    val rideList = ArrayList<RideUiState>()
    LaunchedEffect(key1 = true) {
        rideViewModel.rideList.clear()
        rideViewModel.retrieveRide()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color(177, 231, 255, 205),
                    titleContentColor = Color.Black,
                ), title = { Text(text = "Driver Homepage") },
                actions = {
                    IconButton(onClick = { navController.navigate(Navigate.Profile.name) }) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "")
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Navigate.NewRide.name) }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (rideViewModel.rideList.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "No ride record.", color = Color.Gray, fontSize = 24.sp)
                    Icon(
                        tint = Color.Gray,
                        imageVector = Icons.Filled.CarRental,
                        contentDescription = "",
                        modifier = Modifier.size(50.dp)
                    )
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.Top) {
                    items(rideViewModel.rideList) { ride ->
                        RideCard(ride, rideViewModel = rideViewModel, navController = navController)
                    }
                }

            }
        }
    }


}

@Composable
fun RideCard(ride: RideUiState, rideViewModel: RideViewModel, navController: NavController) {
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
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "Date: " + ride.date)
            Text(text = "Time: " + ride.time)
            Text(text = "Origin: " + ride.origin)
            Text(text = "Destination: " + ride.destination)
            Text(text = "Fare: RM" + ride.fare)
        }
    }
}