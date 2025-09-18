package com.help.roadhelpapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Bu effect, Composable ekrana geldiğinde sadece bir kez çalışır.
    LaunchedEffect(key1 = true) {
        delay(3000L) // 3 saniye bekle

        // Ana ekrana yönlendir ve splash ekranını geri tuşuyla dönülemeyecek şekilde kapat.
        navController.navigate("register_screen") {
            popUpTo("splash_screen") {
                inclusive = true
            }
        }
    }

    // Arayüz Tasarımı
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2C3E50)), // Koyu mavi/gri bir arka plan
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Yol Yardımı Logosu",
                tint = Color(0xFFF39C12), // Turuncu renk
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Yol Yardım Asistanı",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Güvenliğiniz bizimle emin ellerde.",
                color = Color.LightGray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(50.dp))

            CircularProgressIndicator(
                color = Color(0xFFF39C12)
            )
        }
    }
}