package com.example.jsontypicodeusers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jsontypicodeusers.domain.JsonTypiCodeAPI
import com.example.jsontypicodeusers.domain.typiCodeService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersViewModel(private val api: JsonTypiCodeAPI) : ViewModel() {

    companion object {
        fun createFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                UsersViewModel(typiCodeService)
            }
        }

    }


    private val _state = MutableStateFlow(UsersState())
    val state = _state.asStateFlow()

    private val _addUserState = MutableStateFlow(AddUserState())

    val addUserState = _addUserState.asStateFlow()


    init {
        getAllUsers()
    }

    fun updateEmail(newEmail: String) {
        _addUserState.update { currentState -> currentState.copy(email = newEmail) }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            try {
                api.deleteUser(id)
                _state.update { currentState ->
                    currentState.copy(items= currentState.items.filter { user ->
                        user.id != id

                    })
                }
            } catch(e: Exception) {}
        }
    }


    fun getAllUsers() {
        viewModelScope.launch {
            try {

                _state.update { currentState ->
                    currentState.copy(loading = true)
                }


                val users = api.getUsers()
                _state.update { currentState ->
                    currentState.copy(items = users)
                }
            } catch (e: Exception) {
                _state.update { currentState -> currentState.copy(error = e.message) }
            } finally {
                _state.update { currentState -> currentState.copy(loading = false) }
            }
        }

    }
}