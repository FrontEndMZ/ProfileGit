package com.cursosant.profilegit

import android.os.Bundle
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
import com.cursosant.profilegit.ui.view.EditView
import kotlinx.coroutines.launch

/****
 * Project: Profile Git
 * From: com.cursosant.profilegit
 * Created by Alain Nicolás Tello on 07/07/24 at 20:36
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
class EditActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userState = mutableStateOf(User("", "", "", 0.0, 0.0))
        DataStoreSource.checkSafeUser(this).observe(this) { user ->
            userState.value = user
        }

        enableEdgeToEdge()
        setContent {
            ProfileGitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EditView(user = userState.value, Modifier.padding(innerPadding)) { user ->
                        saveUser(user)
                    }
                }
            }
        }
    }

    private fun saveUser(user: User) {
        lifecycleScope.launch {
            DataStoreSource.saveUser(user, this@EditActivity) {
                finish()
            }
        }
    }
}