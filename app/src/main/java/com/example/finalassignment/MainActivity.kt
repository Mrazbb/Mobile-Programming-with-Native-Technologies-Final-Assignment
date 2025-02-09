package com.example.finalassignment

import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.finalassignment.ui.theme.FinalAssignmentTheme
import android.os.Bundle
import android.provider.Settings.Global.getString
import androidx.activity.ComponentActivity
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalassignment.model.QuoteReview
import com.example.finalassignment.model.RatingsApi
import com.example.finalassignment.ui.navigation.BottomBar
import com.example.finalassignment.ui.navigation.DrawerBar
import com.example.finalassignment.ui.navigation.TopBar
import com.example.finalassignment.ui.screens.QuotesHomeScreen
import com.example.finalassignment.ui.screens.QuotesListScreen
import com.example.finalassignment.ui.screens.SettingsScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        RatingsApi.init(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinalAssignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var darkModeEnabled by remember { mutableStateOf(false) }
                    FinalAssignmentTheme(darkTheme = darkModeEnabled) {
                        QuoteApp(darkModeEnabled = darkModeEnabled, onDarkModeChange = { darkModeEnabled = it })
                    }
                }
            }
        }
    }
}

@Composable
fun QuoteApp(
    darkModeEnabled: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {

    val reviewedQuotes = remember { mutableStateListOf<QuoteReview>() }
    val viewedQuotes = remember { mutableStateListOf<Number>() }
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val context = LocalContext.current
    val title = when {
        currentRoute == "home" || currentRoute?.startsWith("home/") == true -> context.getString(R.string.home)
        currentRoute == "quotes" -> context.getString(R.string.quotes)
        currentRoute == "settings" -> context.getString(R.string.settings)
        else -> context.getString(R.string.app_name)
    }

    Scaffold(
        topBar = { TopBar(navController,title = title, onMenuClick = { drawerState.apply {
            scope.launch {
                drawerState.apply {
                    if (isClosed) open() else close()
                }
            }
        }}) },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->

        // Navigation
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            // HOME
            composable("home") {
                // Provide a default quote id (or null) as needed
                QuotesHomeScreen(
                    modifier = Modifier,
                    navController = navController,
                    quoteid = null  // or a default value
                )
            }
            // HOME/quoteid
            composable(
                route = "home/{quoteid}",
                arguments = listOf(
                    navArgument("quoteid") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val quoteid = backStackEntry.arguments?.getInt("quoteid") ?: 0
                QuotesHomeScreen(
                    modifier = Modifier,
                    navController = navController,
                    quoteid = quoteid
                )
            }
            // QUOTES
            composable(route = "quotes") { QuotesListScreen(modifier = Modifier, navController = navController) }

            // SETTINGS
            composable(route = "settings") { SettingsScreen(modifier = Modifier, navController = navController, darkModeEnabled = darkModeEnabled, onDarkModeChange = onDarkModeChange ) }
        }
    }

}

