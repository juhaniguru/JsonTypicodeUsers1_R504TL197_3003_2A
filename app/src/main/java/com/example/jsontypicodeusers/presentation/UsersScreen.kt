package com.example.jsontypicodeusers.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.jsontypicodeusers.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(stringResource(R.string.users))
        })
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {

        }
    }
}