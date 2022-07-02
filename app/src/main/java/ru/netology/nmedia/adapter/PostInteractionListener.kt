package ru.netology.nmedia.adapter

import ru.netology.nmedia.data.Post

interface PostInteractionListener {

    fun onLikeClicked(post: Post)

    fun onShareClicked(post: Post)

    fun onRemoveClicked(post: Post)

    fun onEditClicked(post: Post)

    fun onAddClicked()

    fun getPostFromDate(postId: Int?): Post

    fun onPlayVideoClicked(post: Post)

}