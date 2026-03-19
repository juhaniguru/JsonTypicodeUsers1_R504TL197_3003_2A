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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jsontypicodeusers.presentation.UsersScreenRoot
import com.example.jsontypicodeusers.ui.theme.JsonTypicodeUsersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JsonTypicodeUsersTheme {

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


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
                                onClick = {},
                                selected = true
                            )
                        }
                    }, drawerState = drawerState
                ) {
                    // Sisältö tähän
                }
            }
        }
    }
}

