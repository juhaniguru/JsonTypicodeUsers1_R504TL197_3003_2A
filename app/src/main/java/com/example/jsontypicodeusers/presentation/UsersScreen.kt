package com.example.jsontypicodeusers.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun UsersScreenRoot(modifier: Modifier = Modifier, onMenuOpen: () -> Unit) {
    val vm = viewModel<UsersViewModel>(factory = UsersViewModel.createFactory())
    val state by vm.state.collectAsStateWithLifecycle()
    //val state = vm.state.collectAsStateWithLifecycle().value

    UsersScreen(state = state, onMenuOpen = onMenuOpen)


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(modifier: Modifier = Modifier, state: UsersState, onMenuOpen : () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    onMenuOpen()
                }) {
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = stringResource(R.string.open_menu)
                    )
                }
            },
            title = {
                Text(stringResource(R.string.users))
            })
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.loading -> CircularProgressIndicator()
                state.error != null -> Text(state.error)
                else -> UsersList(users = state.items)
            }
        }
    }
}

@Composable
fun UsersList(modifier: Modifier = Modifier, users: List<User>) {

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = users, key = { user ->
            user.id
        }) { user ->
            ElevatedCard(modifier = Modifier.fillParentMaxWidth()) {
                Text(user.email)
            }
        }
    }
}

@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
private fun UsersScreenPreview() {
    JsonTypicodeUsersTheme {
        UsersScreen(state = UsersState(), onMenuOpen = {})
    }
}

