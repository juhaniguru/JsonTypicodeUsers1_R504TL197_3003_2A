package com.example.jsontypicodeusers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.jsontypicodeusers.presentation.AddUserScreenRoot
import com.example.jsontypicodeusers.presentation.UsersScreenRoot
import com.example.jsontypicodeusers.presentation.UsersViewModel
import com.example.jsontypicodeusers.ui.theme.JsonTypicodeUsersTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JsonTypicodeUsersTheme {

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val navController = rememberNavController()
                val scope = rememberCoroutineScope()
                val currentDestination = navController.currentBackStackEntry?.destination

                ModalNavigationDrawer(


                    // tämä on drawerin sisältö
                    drawerContent = {
                        ModalDrawerSheet {
                            Spacer(modifier = Modifier.height(16.dp))
                            NavigationDrawerItem(
                                label = {
                                    Text(stringResource(R.string.users))
                                },
                                icon = {
                                    Icon(
                                        Icons.Default.Home,
                                        contentDescription = stringResource(R.string.users)
                                    )
                                },
                                onClick = {
                                    navController.navigate("users") {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                },
                                selected = currentDestination?.hierarchy?.any { it.route == "users_feature" } == true
                            )
                        }
                    }, drawerState = drawerState
                ) {
                    NavHost(
                        navController = navController,

                        startDestination = "users_feature",

                        ) {
                        navigation(startDestination = "users", route = "users_feature") {

                            composable("users") {
                                val vm = it.sharedViewModel<UsersViewModel>(
                                    navController,
                                    factory = UsersViewModel.createFactory()
                                )
                                UsersScreenRoot(
                                    vm = vm,
                                    onMenuOpen = {
                                        scope.launch {
                                            drawerState.open()
                                        }

                                    }, onNavigate = {
                                        navController.navigate("addUser")
                                    })
                            }
                            composable(route = "addUser") {
                                val vm = it.sharedViewModel<UsersViewModel>(
                                    navController,
                                    factory = UsersViewModel.createFactory()
                                )
                                AddUserScreenRoot(onBackClick = {
                                    navController.navigateUp()
                                }, goBack = {
                                    navController.navigateUp()
                                }, viewmodel = vm)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable

// inline: inline tarkoittaa, että JVM (Java Virtual Machine),
// jonka päällä Kotlin pyörii käytännössä kopioi tämän funktion joka paikkaan
// sinne, missä sitä kutsutaan

// reified: tämä tarkoittaa sitä, että kun JVM inlinettää funktion sinne, missä sitä kutsutaan
// samalla se korvaa T:n (geneerinen tyyppi)
// sillä viewmodel classilla, joka kutsussa on määritetty
// eli esim. nav.sharedViewModel<UsersViewModel>(factory = ...)
// T:stä tulee UsersViewModel


inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
    factory: ViewModelProvider.Factory? = null
): T {
    // navBackStackEntry destination.parent voi olla null
    // jos se on null, se tarkoittaa, ettei route ei ole osa subnavigationgraphia
    // vaan yksinäinen route (ilman parentia)

    // voidaan luoda viewmodelista instanssi viewmodelStoreOwnerilla null
    // jolloin viewmodel kiinnittyy kyseiseen routeen
    val navGraphRoute = destination.parent?.route ?: return viewModel(factory = factory)

    // jos route on osa subnavigationgraphia
    // sillä on parent, parentEntry on subnavigationgraphin juuri

    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    // kiinnitetään viewmodel instanssi parentrouteen
    return viewModel(viewModelStoreOwner = parentEntry, factory = factory)
}

