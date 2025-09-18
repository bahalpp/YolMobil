package com.help.roadhelpapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.createSupabaseClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.MessageDigest
import java.util.UUID

public class  MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapboxMap(
                Modifier.fillMaxSize(),
                mapViewportState = rememberMapViewportState {
                    setCameraOptions {
                        zoom(2.0)
                        center(Point.fromLngLat(-98.0, 39.5))
                        pitch(0.0)
                        bearing(0.0)
                    }
                },
            )

        }
    }
}



sealed interface AuthResponse{
    data object Success: AuthResponse
    data class Error(val message: String?): AuthResponse
}


class AuthManager(private val context: Context){

    private val supabase = createSupabaseClient(
        supabaseUrl = "https://snbyyyfgaeiysfonzsfp.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNuYnl5eWZnYWVpeXNmb256c2ZwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTc2Mjk2MjksImV4cCI6MjA3MzIwNTYyOX0.Y1BKLWtoF1V5A8s-_PKjbbdJ00QV9f6xN_avHdkQJKM"
    )
    {
        install(Auth)
    }


    fun signUpWithEmail(emailValue: String, passwordValue: String): Flow<AuthResponse> = flow{

        try {
            supabase.auth.signInWith(Email){
                email= emailValue
                password= passwordValue
            }
            emit(AuthResponse.Success)
        }catch (e: Exception){
            emit(AuthResponse.Error(e.localizedMessage))
        }

    }


    fun signInWithEmail(emailValue: String, passwordValue: String): Flow<AuthResponse> = flow{

        try {
            supabase.auth.signInWith(Email){
                email= emailValue
                password= passwordValue
            }
            emit(AuthResponse.Success)
        }catch (e: Exception){
            emit(AuthResponse.Error(e.localizedMessage))
        }

    }

    fun createNonce(): String{
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }

    }

    fun loginGoogleUser(): Flow<AuthResponse> = flow{
        val hashedNonce = createNonce()
        val googleIdOption = GetGoogleIdOption.Builder().setServerClientId("350993240607-mal37d0o4pbla3dqv66mlvs1sjrq9e85.apps.googleusercontent.com")
            .setNonce(hashedNonce)
            .setAutoSelectEnabled(false)
            .setFilterByAuthorizedAccounts(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption).build()

        val credentialManager = CredentialManager.create(context)

        try {
                val result = credentialManager.getCredential(
                    context = context,
                    request = request
                )

            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)

            val googleIdToken = googleIdTokenCredential.idToken

            supabase.auth.signInWith(IDToken){
                idToken = googleIdToken
                provider = Google
            }
            emit(AuthResponse.Success)


        } catch (e: Exception){
            emit(AuthResponse.Error(e.localizedMessage))
        }
    }



}