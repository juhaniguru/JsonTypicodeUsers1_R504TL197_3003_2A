package com.example.jsontypicodeusers.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsersViewModel : ViewModel() {
    private val _state = MutableStateFlow(UsersState())
    val state = _state.asStateFlow()
}