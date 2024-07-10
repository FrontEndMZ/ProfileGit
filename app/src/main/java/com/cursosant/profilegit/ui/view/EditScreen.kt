package com.cursosant.profilegit.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cursosant.profilegit.User
import com.cursosant.profilegit.ui.theme.ProfileGitTheme

/****
 * Project: Profile Git
 * From: com.cursosant.profilegit.ui.view
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
@Composable
fun EditView(user: User, modifier: Modifier = Modifier, onSaveUser:(User) -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    name = user.name
    email = user.email

    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(value = name,
            onValueChange = { name = it; user.name = it },
            label = { Text(text = "Nombre")})

        OutlinedTextField(modifier = Modifier.padding(top = 16.dp),
            value = email,
            onValueChange = {email = it; user.email = it},
            label = {Text(text = "Email")})

        Button(modifier = Modifier.padding(32.dp),
            onClick = { onSaveUser(user) }){
            Icon(imageVector = Icons.Default.Check, contentDescription = "")
            Text(text = "Guardar usuario")
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun EditPreview() {
    ProfileGitTheme {
        EditView(user = User("", "", "", 0.0, 0.0)) {}
    }
}