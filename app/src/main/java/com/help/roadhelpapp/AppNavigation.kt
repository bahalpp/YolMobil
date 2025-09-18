package com.help.roadhelpapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.help.roadhelpapp.screens.RegisterScreen
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash_screen") {

        // Splash Screen rotası
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }
        composable("register_screen") {
            RegisterScreen()
        }

        // Ana Ekran (Haritanızın olduğu ekran) rotası
        composable("main_screen") {
            // SİZİN ESKİ MAİN ACTİVİTY'DEKİ KODUNUZ ARTIK BURADA
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    // Harita kodunuzu buraya taşıdık.
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