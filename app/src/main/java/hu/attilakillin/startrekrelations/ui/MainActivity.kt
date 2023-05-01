package hu.attilakillin.startrekrelations.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.attilakillin.startrekrelations.ui.screen.characters.CharactersScreen
import hu.attilakillin.startrekrelations.ui.screen.details.DetailsScreen
import hu.attilakillin.startrekrelations.ui.theme.StarTrekRelationsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarTrekRelationsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val nav = rememberNavController()

                    NavHost(navController = nav, startDestination = "home") {
                        composable("home") {
                            CharactersScreen(
                                onDetailsClick = { uid ->
                                    nav.navigate("details/$uid")
                                }
                            )
                        }

                        composable("details/{uid}") { entry ->
                            DetailsScreen(
                                characterUid = entry.arguments?.getString("uid") ?: ""
                            )
                        }
                    }

                }
            }
        }
    }
}
