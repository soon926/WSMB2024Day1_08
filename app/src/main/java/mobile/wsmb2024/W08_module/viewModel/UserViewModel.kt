package mobile.wsmb2024.W08.viewModel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mobile.wsmb2024.W08.uiState.UserUiState
import mobile.wsmb2024.W08_module.Navigate


class UserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    var currentStep by mutableStateOf(1)

    var userId by mutableStateOf("")
    var photo by mutableStateOf("")
    var ic by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var name by mutableStateOf("")
    var gender by mutableStateOf("")
    var phone by mutableStateOf("")
    var address by mutableStateOf("")
    var isDriver by mutableStateOf(false)
    var model by mutableStateOf("")
    var capacity by mutableStateOf("")
    var special by mutableStateOf("")

    var photoLink by mutableStateOf("")
    var errorLogin by mutableStateOf("")

    var userData by mutableStateOf(UserUiState())
    var showPassword by mutableStateOf(false)

    val db = Firebase.firestore
    var auth = Firebase.auth

    fun uploadImage(uri: Uri) = CoroutineScope(Dispatchers.IO).launch {
        var storage = Firebase.storage
        var storageRef = storage.reference
        var photoName = ic
        Log.d("test", photoName)

        var spaceRef = storageRef.child("profile/${photoName}.jpg")

        var uploadTask = spaceRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                photoLink = it.toString()
                Log.d("test", "Upload Image $photoLink")
            }
        }

    }


    fun registerUser() {
        uploadImage(photo.toUri())

        var collection = db.collection("User")
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userData = UserUiState(
                    auth.currentUser?.uid ?: "",
                    photoLink,
                    ic,
                    email,
                    password,
                    name,
                    gender,
                    phone,
                    address,
                    isDriver,
                    model,
                    capacity,
                    special
                )
                db.collection("User").document(auth.currentUser?.uid ?: "")
                    .set(userData)
            }
        }
        Firebase.auth.signOut()
    }

    fun signIn(navController: NavController) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnFailureListener {
                errorLogin = "Wrong Email/Password."
            }
            .addOnSuccessListener {
                if (auth.currentUser!!.uid != null) {
                    getUserData(auth.currentUser!!.uid)
//                    if (userData.isDriver) {
                    navController.navigate(Navigate.DriverHome.name)
//                    } else {
//                        navController.navigate(Navigate.RiderHome.name)
//                    }
                }
            }

        Log.d("test", "Successs login")

    }

    fun getUserData(userId: String) = CoroutineScope(Dispatchers.IO).launch {
        var collection = db.collection("User")
        var userRef = collection.document(userId).get().await()
        if (userRef.exists()) {
            val user = userRef.toObject<UserUiState>()
            user.let {

                userData = it!!
            }
        }

    }

    fun retrieveUser() = CoroutineScope(Dispatchers.IO).launch {
        var collection = db.collection("User")
        var querySnapshot = collection.get().await()

        for (document in querySnapshot.documents) {
        }

    }


}