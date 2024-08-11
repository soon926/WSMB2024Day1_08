package mobile.wsmb2024.W08.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mobile.wsmb2024.W08.uiState.RideUiState
import mobile.wsmb2024.W08.uiState.UserUiState
import java.util.UUID

class RideViewModel : ViewModel() {
    private val _rideUiState = MutableStateFlow(RideUiState())
    val rideUiState: StateFlow<RideUiState> = _rideUiState.asStateFlow()

    var db = Firebase.firestore


    var driver by mutableStateOf<UserUiState>(UserUiState())
    var date by mutableStateOf("")
    var time by mutableStateOf("")
    var origin by mutableStateOf("")
    var destination by mutableStateOf("")
    var fare by mutableStateOf("")

    var showDate by mutableStateOf(false)
    var showTime by mutableStateOf(false)

    var rideList by mutableStateOf(ArrayList<RideUiState>())

    fun createNewRide() {
        var collection = db.collection("Rides")
        var rideId = UUID.randomUUID().toString()
        var ride = RideUiState(
            rideId,
            driver,
            date,
            time,
            origin,
            destination,
            fare
        )
        collection.add(ride).addOnSuccessListener {

        }


    }

    fun retrieveRide() = CoroutineScope(Dispatchers.IO).launch {

        var collection = db.collection("Rides")
        var querySnapshot = collection.get().await()

        for (ride in querySnapshot.documents) {
            val ride = ride.toObject<RideUiState>()
            rideList.add(ride!!)
        }
    }
}