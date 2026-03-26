package com.example.jsontypicodeusers.presentation


// AddUserState

data class AddUserState(
    // isAdding määrittää, voiko lisäysnappia painaa
    // jos request on kesken, disabloidaan nappi tuplaklikkien estämiseksi
    val isAdding: Boolean = false,
    // käyttäjän sähköposti
    val email: String = "",
    // disaboidaan nappi tämän mukaan, ettei turhaa lähetetä requestin mukana
    // tyhjää emailia
    val isEmptyEmail: Boolean = true,
    // kun lisäys on valmis, muutetaan tämä trueksi
    // ja silloin voidaan käyttöliittymässä
    // ohjata takaisin käyttäjälistaukseen
    val isDone: Boolean = false
)
