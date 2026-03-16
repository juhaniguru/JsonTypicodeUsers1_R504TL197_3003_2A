package com.example.jsontypicodeusers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsontypicodeusers.domain.typiCodeService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {



    private val _state = MutableStateFlow(UsersState())
    val state = _state.asStateFlow()


    init {
        getAllUsers()
    }




    fun getAllUsers() {
        viewModelScope.launch {
            try {

                _state.update { currentState ->
                    currentState.copy(loading = true)
                }


                val users = typiCodeService.getUsers()
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