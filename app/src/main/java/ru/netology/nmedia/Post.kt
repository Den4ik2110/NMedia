package ru.netology.nmedia

data class Post(
    val icon: Int,
    val author: String,
    val date: String,
    val message: String,
    var amountLike: Int,
    var amountShare: Int,
    var amountView: Int,
    var isLike: Boolean = false
)