package com.example.myapplication.models

data class Edificio(val name: String, val imageURL: String, val description: String, var comments: List<Comment>)