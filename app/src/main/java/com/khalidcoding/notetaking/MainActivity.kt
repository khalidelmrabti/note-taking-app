package com.khalidcoding.notetaking

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.khalidcoding.notetaking.navigation.AppNavGraph
import com.khalidcoding.notetaking.screens.home.HomeScreen
import com.khalidcoding.notetaking.ui.theme.SpecialNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MyApp {
                val navController = rememberNavController()
                AppNavGraph(navController)
            }

        }

    }
}


@Composable
fun MyApp(content: @Composable () -> Unit) {

    SpecialNoteAppTheme() {
        content()
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainPreview(navController: NavHostController = rememberNavController()) {

    SpecialNoteAppTheme {
        MyApp {
            HomeScreen(navHostController = navController)
        }
    }

}
