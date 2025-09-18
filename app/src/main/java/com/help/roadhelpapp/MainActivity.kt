package com.help.roadhelpapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

// NOT: com.help.roadhelpapp.ui.theme.RoadHelpAppTheme kısmını
// kendi projenizin tema adıyla değiştirmeniz gerekebilir.
// Genellikle projenizle aynı adda bir tema olur.
import com.help.roadhelpapp.ui.theme.SupabaseCourseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Projenizin kendi temasını kullanmak en iyisidir.
            // Eğer bir temanız yoksa bu satırı ve Surface bloğunu silebilirsiniz.
            SupabaseCourseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // MainActivity artık sadece navigasyonu başlatıyor.
                    AppNavigation()
                }
            }
        }
    }
}