package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override val data = MutableLiveData(
        listOf(
            Post(
                1,
                R.drawable.ic_launcher_foreground,
                "Нетология. Университет интернет-профессий",
                "13 июня в 18:36",
                "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов " +
                        "по онлайн-маркетингу",
                (0..10000).random(),
                (0..5000).random(),
                (0..20000).random()
            ),
            Post(
                2,
                R.drawable.ic_launcher_foreground,
                "Нетология. Университет интернет-профессий",
                "13 июня в 18:37",
                "Затем появились курсы по дизайну, разработке, аналитике и управлению",
                (0..10000).random(),
                (0..5000).random(),
                (0..20000).random()
            ),
            Post(
                3,
                R.drawable.ic_launcher_foreground,
                "Нетология. Университет интернет-профессий",
                "13 июня в 18:38",
                "Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов",
                (0..10000).random(),
                (0..5000).random(),
                (0..20000).random()
            ),
            Post(
                4,
                R.drawable.ic_launcher_foreground,
                "Нетология. Университет интернет-профессий",
                "13 июня в 18:39",
                "Но самое важное остается с нами: мы верим, что в каждом уже есть сила," +
                        "которая заставляет хотеть больше, целиться выше, бежать быстрее",
                (0..10000).random(),
                (0..5000).random(),
                (0..20000).random()
            ),
            Post(
                5,
                R.drawable.ic_launcher_foreground,
                "Нетология. Университет интернет-профессий",
                "13 июня в 18:40",
                "Наша миссия - помочь встать на путь роста и начать цепочку перемен - " +
                        "http://netolo.gy/fyb",
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
                else it.copy(isLike = !it.isLike, amountLike = it.amountLike + 1)}
        }
    }

    override fun shareClick(postId: Int) {
        data.value = posts.map {
            if (it.postId != postId) it
            else it.copy(amountShare = it.amountShare + 1)
        }
    }
}