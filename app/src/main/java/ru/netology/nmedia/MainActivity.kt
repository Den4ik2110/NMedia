package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { postFromData ->
            binding.postBind(postFromData)
        }

        binding.buttonLike.setOnClickListener { viewModel.onLikeClicked() }

        binding.buttonShare.setOnClickListener { viewModel.onShareClicked() }
    }


    private fun ActivityMainBinding.postBind(post: Post) {
            postAuthor.text = post.author
            postIcon.setImageResource(post.icon)
            postDate.text = post.date
            postFullText.text = post.message
            valueLike.text = rounding(post.amountLike)
            valueShare.text = rounding(post.amountShare)
            valueSee.text = rounding(post.amountView)
            buttonLike.setImageResource(getLikeIconRes(post.isLike))
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

    private fun getLikeIconRes(isLike: Boolean): Int {
        return if (isLike) R.drawable.ic_like_true else R.drawable.ic_like
    }
}
