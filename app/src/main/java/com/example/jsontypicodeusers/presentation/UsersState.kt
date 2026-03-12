package com.example.jsontypicodeusers.presentation

data class User(val id: Int, val email: String)

// UsersState on luokka, jonka datan mukaan käyttöliittymä päivitetään
data class UsersState(
    // loading: tämän mukaan näytetään latausikoni
    // jos tietojen haku kestää pitkään
    val loading: Boolean = false,
    // tämä on lista käyttäjiä (User data class)
    val items: List<User> = emptyList(),
    // jos haku ei onnistu, näytetään käyttäjälle virhe
    val error: String? = null
)