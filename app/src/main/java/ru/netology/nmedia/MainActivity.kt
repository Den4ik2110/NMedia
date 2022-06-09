package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postFirst = createPost()

        postBind(postFirst)

        binding.buttonLike.setOnClickListener { likeClick(postFirst) }

        binding.buttonShare.setOnClickListener { shareClick(postFirst) }
    }

    private fun createPost(): Post {
        return Post(
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
    }

    private fun postBind(post: Post) {
        binding.apply {
            postAuthor.text = post.author
            postIcon.setImageResource(post.icon)
            postDate.text = post.date
            postFullText.text = post.message
            valueLike.text = rounding(post.amountLike)
            valueShare.text = rounding(post.amountShare)
            valueSee.text = rounding(post.amountView)
        }

    }

    private fun likeClick(post: Post) {
        binding.apply {
            if (!post.isLike) {
                buttonLike.setImageResource(R.drawable.ic_like_true)
                post.isLike = !post.isLike
                post.amountLike++
                valueLike.text = rounding(post.amountLike)
            } else {
                buttonLike.setImageResource(R.drawable.ic_like)
                post.isLike = !post.isLike
                post.amountLike--
                valueLike.text = rounding(post.amountLike)
            }
        }
    }

    private fun shareClick(post: Post) {
        post.amountShare++
        binding.valueShare.text = rounding(post.amountShare)
    }

    private fun rounding(value: Int): String {
        return when (value) {
            in 0..999 -> "$value"
            in 1000..1099 -> getString(R.string.value_1000_1099, value / 1000)
            in 1100..9999 -> getString(R.string.value_1100_9999, value / 1000, value % 1000 / 100)
            in 10000..10999 -> getString(R.string.value_10000_10999, value / 10000)
            in 11000..999999 -> getString(
                R.string.value_11000_99999,
                value / 10000,
                value % 10000 / 1000
            )
            else -> getString(R.string.value_more_1000000, value / 100000, value % 100000 / 10000)
        }
    }
}
