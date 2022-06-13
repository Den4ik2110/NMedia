package ru.netology.nmedia.data

data class Post(
    val postId: Int,
    val icon: Int,
    val author: String,
    val date: String,
    val message: String,
    var amountLike: Int,
    var amountShare: Int,
    var amountView: Int,
    var isLike: Boolean = false
)