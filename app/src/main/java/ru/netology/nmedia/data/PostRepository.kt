package ru.netology.nmedia.data

import androidx.lifecycle.LiveData

interface PostRepository {

    val data: LiveData<Post>

    fun likeClick()

    fun likeClickNo()

    fun shareClick()

    fun getPostIsLike(): Boolean
}