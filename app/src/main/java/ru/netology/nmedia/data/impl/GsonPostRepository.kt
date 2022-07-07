package ru.netology.nmedia.data.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.util.Constants
import kotlin.properties.Delegates

class GsonPostRepository(private val application: Application) : PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    private val prefs = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE
    )

    private var nextId: Int by Delegates.observable(
        prefs.getInt(Constants.ID_PREFS_KEY, 0)
    ) { _, _, newValue ->
        prefs.edit { putInt(Constants.ID_PREFS_KEY, newValue) }
    }

    private var posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }
        set(value) {
            application.openFileOutput(
                Constants.FILE_NAME, Context.MODE_PRIVATE
            ).bufferedWriter().use {
                it.write(gson.toJson(value))
            }
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val postsFile = application.filesDir.resolve(Constants.FILE_NAME)
        val posts: List<Post> = if (postsFile.exists()) {
            val inputStream = application.openFileInput(Constants.FILE_NAME)
            val reader = inputStream.bufferedReader()
            reader.use { gson.fromJson(it, type) }
        } else emptyList()
        data = MutableLiveData(posts)
    }

    override fun likeClick(postId: Int) {
        posts = posts.map {
            if (it.postId != postId) it
            else {
                if (it.isLike) it.copy(isLike = !it.isLike, amountLike = it.amountLike - 1)
                else it.copy(isLike = !it.isLike, amountLike = it.amountLike + 1)
            }
        }
    }

    override fun shareClick(postId: Int) {
        posts = posts.map {
            if (it.postId != postId) it
            else it.copy(amountShare = it.amountShare + 1)
        }
    }

    override fun getPostFromDate(postId: Int?): Post {
        val currentPost = posts.filter { it.postId == postId }
        return currentPost[0]
    }

    override fun deletePost(postId: Int) {
        posts = posts.filter { it.postId != postId }
    }

    override fun save(post: Post) {
        if (post.postId == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun update(post: Post) {
        posts = posts.map {
            if (it.postId == post.postId) post else it
        }
    }

    private fun insert(post: Post) {
        posts = listOf(post.copy(postId = ++nextId)) + posts
    }

    companion object {
        private var instance: GsonPostRepository? = null
        fun getInstance(application: Application) = instance ?: GsonPostRepository(application).also {
            instance = it
        }
    }
}