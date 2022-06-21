package ru.netology.nmedia.data

import androidx.lifecycle.LiveData

interface PostRepository {

    val data: LiveData<List<Post>>

    fun likeClick(postId: Int)

    fun shareClick(postId: Int)

    fun deletePost(postId: Int)

    fun save(post: Post)

    companion object {
        const val NEW_POST_ID = 0
    }
}