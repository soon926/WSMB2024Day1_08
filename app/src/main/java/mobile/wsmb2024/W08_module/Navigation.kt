package mobile.wsmb2024.W08_module

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import mobile.wsmb2024.W08.screen.Login
import mobile.wsmb2024.W08.screen.Register
import mobile.wsmb2024.W08.uiState.UserUiState
import mobile.wsmb2024.W08.viewModel.RideViewModel
import mobile.wsmb2024.W08.viewModel.UserViewModel
import mobile.wsmb2024.W08_module.screen.DriverHome
import mobile.wsmb2024.W08_module.screen.NewRide
import mobile.wsmb2024.W08_module.screen.Profile

enum class Navigate() {
    Register,
    Login,
    DriverHome,
    RiderHome,
    Profile,
    NewRide
}

@Composable
fun Navigation(
    userViewModel: UserViewModel = viewModel(),
    rideViewModel: RideViewModel = viewModel()
) {

    val navController = rememberNavController()
    val db = Firebase.database
    val auth = Firebase.auth
    var currentUser = auth.currentUser

    var userData by remember {
        mutableStateOf(UserUiState())
    }
//
//    if (currentUser!!.uid != null) {
//        userViewModel.getUserData(currentUser.uid)
//    }

    LaunchedEffect(key1 = true) {
        userViewModel.getUserData(currentUser!!.uid)
    }
    NavHost(
        navController = navController,

        startDestination = Navigate.Login.name
//        startDestination =
//        if (auth.currentUser != null) {
//            if (userData.isDriver) {
//                Navigate.DriverHome.name
//            } else {
//                Navigate.RiderHome.name
//            }
//        } else {
//            Navigate.Login.name
//        }

    ) {
        composable(route = Navigate.Register.name) {
            Register(
                navController = navController,
                userViewModel = userViewModel,
                modifier = Modifier
            )
        }
        composable(route = Navigate.Login.name) {
            Login(userViewModel = userViewModel, navController, modifier = Modifier)
        }
        composable(route = Navigate.NewRide.name) {
            NewRide(
                rideViewModel = rideViewModel,
                userViewModel = userViewModel,
                navController = navController
            )
        }
        composable(route = Navigate.DriverHome.name) {
            DriverHome(
                userViewModel = userViewModel,
                rideViewModel = rideViewModel,
                navController = navController
            )

        }
        composable(route = Navigate.Profile.name) {
            Profile(userViewModel = userViewModel, navController = navController)
        }
    }
}

