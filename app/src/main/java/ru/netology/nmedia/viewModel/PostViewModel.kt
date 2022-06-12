package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel : ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    fun onLikeClickedTrue() = repository.likeClick()

    fun onLikeClickedFalse() = repository.likeClickNo()

    fun onShareClicked() = repository.shareClick()

    fun getIsLikePost() = repository.getPostIsLike()
}