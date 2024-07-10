package com.cursosant.profilegit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.cursosant.profilegit.ui.theme.ProfileGitTheme
import com.cursosant.profilegit.ui.view.ProfileView
import kotlinx.coroutines.launch

/****
 * Project: Profile Git
 * From: com.cursosant.profilegit
 * Created by Alain Nicolás Tello on 06/07/24 at 21:36
 * All rights reserved 2024.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * And Frogames formación:
 * https://cursos.frogamesformacion.com/pages/instructor-alain-nicolas
 *
 * Coupons on my Website:
 * www.alainnicolastello.com
 ***/
class MainActivity : ComponentActivity() {
    private val userState = mutableStateOf(User("", "", "", 0.0, 0.0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataStoreSource.checkSettings(this).observe(this) { isFirstTime ->
            if (isFirstTime) {
                saveUser(User("CursosANT", "alainnt@cursosant.com",
                    "https://i.ytimg.com/vi/eAgNdiTlLFc/hqdefault.jpg",
                    20.587989, -100.387914))
            }
        }

        enableEdgeToEdge()
        setContent {
            ProfileGitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProfileView(user = userState.value, Modifier.padding(innerPadding))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        DataStoreSource.checkUser(this).observe(this) { user ->
            userState.value = user
        }
    }

    private fun saveUser(user: User) {
        lifecycleScope.launch {
            DataStoreSource.saveUser(user, this@MainActivity) {
                userState.value = user
                Toast.makeText(this@MainActivity, "Bienvenido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}