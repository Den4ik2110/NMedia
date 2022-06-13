package ru.netology.nmedia.data

import androidx.lifecycle.LiveData

interface PostRepository {

    val data: LiveData<List<Post>>

    fun likeClick(postId: Int)

    fun shareClick(postId: Int)
}