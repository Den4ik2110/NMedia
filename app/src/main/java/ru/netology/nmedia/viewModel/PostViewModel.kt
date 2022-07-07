package ru.netology.nmedia.viewModel

import SingleLiveEvent
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.GsonPostRepository

class PostViewModel(application: Application) : AndroidViewModel(application),
    PostInteractionListener {

    private val repository: PostRepository = GsonPostRepository.getInstance(application)

    val data by repository::data

    val navigateToPostContentScreenEvent = SingleLiveEvent<String?>()
    val navigateToPostClickedEvent = SingleLiveEvent<Int>()
    val sharePostContent = SingleLiveEvent<String>()
    val playVideoContent = SingleLiveEvent<String?>()
    private val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClicked(content: String, url: String?) {
        if (content.isBlank()) return
        val post = currentPost.value?.copy(
            message = content,
            urlVideo = url
        ) ?: Post(
            postId = PostRepository.NEW_POST_ID,
            author = "Me",
            date = "20.06.2022",
            message = content,
            urlVideo = url
        )
        repository.save(post)
        currentPost.value = null
    }

    override fun onLikeClicked(post: Post) {

        repository.likeClick(post.postId)
    }

    override fun onShareClicked(post: Post) {
        sharePostContent.value = post.message
        repository.shareClick(post.postId)
    }

    override fun onRemoveClicked(post: Post) = repository.deletePost(post.postId)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = post.postId.toString()
    }

    override fun onAddClicked() = navigateToPostContentScreenEvent.call()

    override fun getPostFromDate(postId: Int?): Post {
        return repository.getPostFromDate(postId)
    }

    override fun onPlayVideoClicked(post: Post) {
        playVideoContent.value = post.urlVideo
    }

    override fun onPostClicked(post: Post) {
        currentPost.value = post
        navigateToPostClickedEvent.value = post.postId
    }

    override fun rounding(value: Int): String {
        val application = getApplication<Application>()
        return when (value) {
            in 0..999 -> "$value"
            in 1000..1099 -> application.getString(R.string.value_1000_1099, value / 1000)
            in 1100..9999 -> application.getString(
                R.string.value_1100_9999,
                value / 1000,
                value % 1000 / 100
            )
            in 10000..10999 -> application.getString(R.string.value_10000_10999, value / 10000)
            in 11000..999999 -> application.getString(
                R.string.value_11000_99999,
                value / 10000,
                value % 10000 / 1000
            )
            else -> application.getString(
                R.string.value_more_1000000,
                value / 100000,
                value % 100000 / 10000
            )
        }
    }

}