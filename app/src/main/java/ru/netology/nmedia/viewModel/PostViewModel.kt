package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.MainActivity
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel : ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    fun onLikeClicked(post: Post) = repository.likeClick(post.postId)

    fun onShareClicked(post: Post) = repository.shareClick(post.postId)


}