package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return
        val post = currentPost.value?.copy(
            message = content
        ) ?: Post(
            postId = PostRepository.NEW_POST_ID,
            author = "Me",
            date = "20.06.2022",
            message = content
        )
        repository.save(post)
        currentPost.value = null
    }

    override fun onLikeClicked(post: Post) = repository.likeClick(post.postId)

    override fun onShareClicked(post: Post) = repository.shareClick(post.postId)

    override fun onRemoveClicked(post: Post) = repository.deletePost(post.postId)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
    }


}