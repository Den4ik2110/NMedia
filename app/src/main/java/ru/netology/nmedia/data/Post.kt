package ru.netology.nmedia.data

import ru.netology.nmedia.R

data class Post(
    val postId: Int,
    val author: String,
    val date: String,
    val message: String,
    val icon: Int = R.drawable.ic_launcher_foreground,
    var amountLike: Int = 0,
    var amountShare: Int = 0,
    var amountView: Int = 0,
    var urlVideo: String? = null,
    var isLike: Boolean = false
)