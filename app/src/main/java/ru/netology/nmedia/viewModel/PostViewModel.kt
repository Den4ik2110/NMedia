package ru.netology.nmedia.viewModel

import SingleLiveEvent
import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.GsonPostRepository

class PostViewModel(application: Application) : AndroidViewModel(application), PostInteractionListener {

    private val repository: PostRepository = GsonPostRepository(application)

    val data by repository::data

    val navigateToPostContentScreenEvent = SingleLiveEvent<Int>()
    val sharePostContent = SingleLiveEvent<String>()
    val playVideoContent = SingleLiveEvent<Uri?>()
    private val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClicked(content: String, uri: Uri?) {
        if (content.isBlank()) return
        val post = currentPost.value?.copy(
            message = content,
            urlVideo = uri
        ) ?: Post(
            postId = PostRepository.NEW_POST_ID,
            author = "Me",
            date = "20.06.2022",
            message = content,
            urlVideo = uri
        )
        repository.save(post)
        currentPost.value = null
    }

    override fun onLikeClicked(post: Post) = repository.likeClick(post.postId)

    override fun onShareClicked(post: Post) {
        sharePostContent.value = post.message
        repository.shareClick(post.postId)
    }

    override fun onRemoveClicked(post: Post) = repository.deletePost(post.postId)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = post.postId
    }

    override fun onAddClicked() = navigateToPostContentScreenEvent.call()

    override fun getPostFromDate(postId: Int?): Post {
        return repository.getPostFromDate(postId)
    }

    override fun onPlayVideoClicked(post: Post) {
        playVideoContent.value = post.urlVideo
    }

}