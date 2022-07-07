package ru.netology.nmedia.data.impl

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository

object InMemoryPostRepository : PostRepository {

    private var nextId = 1000

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override val data = MutableLiveData(
        listOf(
            Post(
                1,
                "Нетология. Университет интернет-профессий",
                "13 июня в 18:36",
                "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов " +
                        "по онлайн-маркетингу",
                icon = R.drawable.ic_launcher_foreground,
                (0..10000).random(),
                (0..5000).random(),
                (0..20000).random()
            ),
            Post(
                2,
                "Нетология. Университет интернет-профессий",
                "13 июня в 18:37",
                "Затем появились курсы по дизайну, разработке, аналитике и управлению",
                icon = R.drawable.ic_launcher_foreground,
                (0..10000).random(),
                (0..5000).random(),
                (0..20000).random(),
                "https://www.youtube.com/watch?v=TbRk4leyWxs"
            ),
            Post(
                3,
                "Нетология. Университет интернет-профессий",
                "13 июня в 18:38",
                "Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов",
                icon = R.drawable.ic_launcher_foreground,
                (0..10000).random(),
                (0..5000).random(),
                (0..20000).random(),
                "https://www.youtube.com/watch?v=TbRk4leyWxs"
            ),
            Post(
                4,
                "Нетология. Университет интернет-профессий",
                "13 июня в 18:39",
                "Но самое важное остается с нами: мы верим, что в каждом уже есть сила," +
                        "которая заставляет хотеть больше, целиться выше, бежать быстрее",
                icon = R.drawable.ic_launcher_foreground,
                (0..10000).random(),
                (0..5000).random(),
                (0..20000).random(),
                "https://www.youtube.com/watch?v=TbRk4leyWxs"
            ),
            Post(
                5,
                "Нетология. Университет интернет-профессий",
                "13 июня в 18:40",
                "Наша миссия - помочь встать на путь роста и начать цепочку перемен - " +
                        "http://netolo.gy/fyb",
                icon = R.drawable.ic_launcher_foreground,
                (0..10000).random(),
                (0..5000).random(),
                (0..20000).random()
            ),
        )
    )

    override fun likeClick(postId: Int) {
        data.value = posts.map {
            if (it.postId != postId) it
            else {
                if (it.isLike) it.copy(isLike = !it.isLike, amountLike = it.amountLike - 1)
                else it.copy(isLike = !it.isLike, amountLike = it.amountLike + 1)
            }
        }
    }

    override fun shareClick(postId: Int) {
        data.value = posts.map {
            if (it.postId != postId) it
            else it.copy(amountShare = it.amountShare + 1)
        }
    }

    override fun getPostFromDate(postId: Int?): Post {
        val currentPost = posts.filter { it.postId == postId }
        return currentPost[0]
    }

    override fun deletePost(postId: Int) {
        data.value = posts.filter { it.postId != postId }
    }

    override fun save(post: Post) {
        Log.d("MyLog", "postSave = ${post.postId}")
        if (post.postId == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.postId == post.postId) post else it
        }
    }

    private fun insert(post: Post) {
        data.value = listOf(post.copy(postId = ++nextId)) + posts
    }

}