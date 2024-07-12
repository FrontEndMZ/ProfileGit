package com.cursosant.profilegit

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.first
import kotlin.random.Random

/****
 * Project: Profile Git
 * From: com.cursosant.profilegit
 * Created by Alain Nicolás Tello on 08/07/24 at 17:29
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
object DataStoreSource {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val K_FIRST_TIME = booleanPreferencesKey("k_first_time")
    private val K_NAME = stringPreferencesKey("k_user_name")
    private val K_EMAIL = stringPreferencesKey("k_user_email")
    private val K_IMG_URL = stringPreferencesKey("k_user_img_url")
    private val K_LAT = doublePreferencesKey("k_user_lat")
    private val K_LONG = doublePreferencesKey("k_user_long")

    fun checkSettings(context: Context) = liveData {
        emit(mapSettingsPreferences(context.dataStore.data.first().toPreferences()))
    }

    fun checkSafeUser(context: Context) = liveData {
        val isOutside = Random.nextBoolean()
        if (isOutside && (context is MainActivity)) {
            emit(User("-", "-", "", 0.0, 0.0))
        } else {
            emit(mapUserPreferences(context.dataStore.data.first().toPreferences()))
        }
    }

    private fun mapSettingsPreferences(preferences: Preferences): Boolean {
        return try {
            preferences[K_FIRST_TIME] ?: true
        } catch (e: Exception) {
            true
        }
    }

    private fun mapUserPreferences(preferences: Preferences): User {
        try {
            val name = preferences[K_NAME] ?: ""
            val email = preferences[K_EMAIL] ?: ""
            val imgUrl = preferences[K_IMG_URL] ?: ""
            val lat = preferences[K_LAT] ?: 0.0
            val long = preferences[K_LONG] ?: 0.0
            return User(name, email, imgUrl, lat, long)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return User("", "", "", 0.0, 0.0)
    }

    suspend fun saveUser(user: User, context: Context, onSuccess: () -> Unit) {
        try {
            context.dataStore.edit { preferences ->
                preferences[K_FIRST_TIME] = false
                preferences[K_NAME] = user.name
                preferences[K_EMAIL] = user.email
                preferences[K_IMG_URL] = user.imgUrl
                preferences[K_LAT] = user.lat
                preferences[K_LONG] = user.long
            }
            onSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}