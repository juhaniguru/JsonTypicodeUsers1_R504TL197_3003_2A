package com.example.jsontypicodeusers.presentation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jsontypicodeusers.R
import com.example.jsontypicodeusers.ui.theme.JsonTypicodeUsersTheme


@Composable
fun UsersScreenRoot(modifier: Modifier = Modifier, onMenuOpen: () -> Unit) {
    val vm = viewModel<UsersViewModel>(factory = UsersViewModel.createFactory())
    val state by vm.state.collectAsStateWithLifecycle()
    //val state = vm.state.collectAsStateWithLifecycle().value

    UsersScreen(state = state, onMenuOpen = onMenuOpen, onRemoveUser = { userId ->
        vm.deleteUser(userId)
    })


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    modifier: Modifier = Modifier,
    state: UsersState,
    onMenuOpen: () -> Unit,
    onRemoveUser: (Int) -> Unit
) {
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
                else -> UsersList(users = state.items, onRemoveUser = onRemoveUser)
            }
        }
    }
}

@Composable
fun UsersList(modifier: Modifier = Modifier, users: List<User>, onRemoveUser: (Int) -> Unit) {

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = users, key = { user ->
            user.id
        }) { user ->
            Box(
                modifier = Modifier.animateItem(
                    fadeOutSpec = tween(300),
                    placementSpec = spring(stiffness = Spring.StiffnessLow))
                ) {
                UserCard(user=user, onRemoveUser = onRemoveUser)
            }

        }
    }
}

@Composable
fun UserCard(
    user: User,
    onRemoveUser: (Int) -> Unit

) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = user.email.take(1).uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))


            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Customer ID: ${user.id}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }


            IconButton(onClick = {
                onRemoveUser(user.id)
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete User",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
private fun UsersScreenPreview() {
    JsonTypicodeUsersTheme {
        UsersScreen(state = UsersState(), onMenuOpen = {}, onRemoveUser = {})
    }
}

