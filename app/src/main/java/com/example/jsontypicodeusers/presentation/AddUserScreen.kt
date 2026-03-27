package com.example.jsontypicodeusers.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jsontypicodeusers.R


// AddUserScreen

@Composable
fun AddUserScreenRoot(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    goBack: () -> Unit,
    viewmodel: UsersViewModel
) {
    // meiltä puuttuu vielä viewmodelista sopiva data class, jossa käsitellään tämän screenin tilaa

    // nyt samasta viewmodelista tehdään toinen instanssi


    val state by viewmodel.addUserState.collectAsStateWithLifecycle()

    AddUserScreen(onBackClick = onBackClick, state = state, onEmailChange = {
        viewmodel.updateEmail(it)
    }, onUserCreate = {
        viewmodel.createUser()
    }, goBack = {
        goBack()
        viewmodel.setIsDone(false)
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserScreen(
    onBackClick: () -> Unit,
    state: AddUserState,
    onEmailChange: (String) -> Unit,
    onUserCreate: () -> Unit,
    goBack: () -> Unit
) {

    LaunchedEffect(state.isDone) {

        if (state.isDone) {

            goBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_new_user)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                // tämä data tulee viewmodelista
                value = state.email,
                // tähän lisätään lambda, joka tulee olemaan viewmodelissa
                onValueChange = { newText -> onEmailChange(newText) },
                label = { Text("Email Address") },
                placeholder = { Text("example@domain.com") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                singleLine = true
            )


            Button(
                // tämä tulee viewmodelista
                onClick = { onUserCreate() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.create_user))
            }
        }
    }
}

