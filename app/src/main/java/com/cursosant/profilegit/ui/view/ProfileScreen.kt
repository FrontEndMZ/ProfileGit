package com.cursosant.profilegit.ui.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cursosant.profilegit.EditActivity
import com.cursosant.profilegit.User
import com.cursosant.profilegit.ui.theme.ProfileGitTheme
import com.cursosant.profilegit.ui.theme.Typography

/****
 * Project: Profile Git
 * From: com.cursosant.profilegit.ui.view
 * Created by Alain Nicolás Tello on 07/07/24 at 09:39
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
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileView(user: User, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally) {
        GlideImage(model = user.imgUrl,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(16.dp)
                .aspectRatio(1f)
                .clip(CircleShape))

        Text(text = "Nombre")
        Text(text = user.name, style = Typography.headlineLarge)

        Text(text = "Email", modifier = Modifier.padding(top = 16.dp))
        Text(text = user.email,
            modifier = Modifier.clickable { sendEmail(user.email, context) },
            style = Typography.bodyLarge)

        Text(text = "Ubicación", modifier = Modifier.padding(top = 16.dp))
        Text(text = "${user.lat}, ${user.long}",
            modifier = Modifier.clickable { showMap(user.lat, user.long, context) },
            style = Typography.titleMedium)

        TextButton(modifier = Modifier.padding(32.dp),
            onClick = { editUser(context) }) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "")
            Text(text = "Editar usuario")
        }
    }
}

private fun editUser(context: Context) {
    context.startActivity(Intent(context, EditActivity::class.java))
}

private fun sendEmail(email: String, context: Context) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, "From android git course")
        putExtra(Intent.EXTRA_TEXT, "Version control...")
    }
    launchIntent(intent, context)
}

private fun showMap(lat: Double, long: Double, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("geo:0,0?q=$lat,$long(Cursos ANT)")
        `package` = "com.google.android.apps.maps"
    }
    launchIntent(intent, context)
}

private fun launchIntent(intent: Intent, context: Context) {
    val packageManager = context.packageManager
    if (intent.resolveActivity(packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "No se encontró una app compatible.", Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProfileViewPreview() {
    ProfileGitTheme {
        ProfileView(user = User("CursosANT", "alainnt@cursosant.com",
            "https://i.ytimg.com/vi/eAgNdiTlLFc/hqdefault.jpg",
            0.0, 0.0))
    }
}