package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    override val data = MutableLiveData(
        Post(
            R.drawable.ic_launcher_foreground,
            "Нетология. Университет интернет-профессий",
            "21 мая в 18:36",
            "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов " +
                    "по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике " +
                    "и управлению. Мы растем сами и помагаем расти студентам: от новичков до " +
                    "уверенных профессионалов. Но самое важное остается с нами: мы верим, что в " +
                    "каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать " +
                    "быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен - " +
                    "http://netolo.gy/fyb",
            999,
            990,
            5849921
        )
    )

    override fun likeClick() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }


        val likePost = currentPost.copy(
            isLike = !currentPost.isLike,
            amountLike = currentPost.amountLike + 1,
        )


        data.value = likePost
    }

    override fun likeClickNo() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }

        val likePost = currentPost.copy(
            isLike = !currentPost.isLike,
            amountLike = currentPost.amountLike - 1,
        )

        data.value = likePost
    }

    override fun shareClick() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }

        val sharePost = currentPost.copy(
            amountShare = currentPost.amountShare + 1,
        )

        data.value = sharePost
    }

    override fun getPostIsLike(): Boolean {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        return currentPost.isLike
    }
}