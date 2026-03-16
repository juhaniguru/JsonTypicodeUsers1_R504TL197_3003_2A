package com.example.jsontypicodeusers.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jsontypicodeusers.R
import com.example.jsontypicodeusers.ui.theme.JsonTypicodeUsersTheme


@Composable
fun UsersScreenRoot(modifier: Modifier = Modifier) {
    val vm = viewModel<UsersViewModel>()
    val state by vm.state.collectAsStateWithLifecycle()
    //val state = vm.state.collectAsStateWithLifecycle().value

    UsersScreen(state=state)


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(modifier: Modifier = Modifier, state: UsersState) {
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
            Text("Sisältö!!")
        }
    }
}

@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
private fun UsersScreenPreview() {
    JsonTypicodeUsersTheme {
        UsersScreen(state= UsersState())
    }
}

