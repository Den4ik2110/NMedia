package ru.netology.nmedia.data

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.OnePostBinding

internal class PostAdapter(
    private val onLikeClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit,
    private val context: Context
) : ListAdapter<Post, PostAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OnePostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: OnePostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) = with(binding) {
            postAuthor.text = post.author
            postIcon.setImageResource(post.icon)
            postDate.text = post.date
            postFullText.text = post.message
            valueLike.text = rounding(post.amountLike)
            valueShare.text = rounding(post.amountShare)
            valueSee.text = rounding(post.amountView)
            buttonLike.setImageResource(getLikeIconRes(post.isLike))
            buttonLike.setOnClickListener { onLikeClicked(post) }
            buttonShare.setOnClickListener { onShareClicked(post) }
        }

        private fun getLikeIconRes(isLike: Boolean): Int {
            return if (isLike) R.drawable.ic_like_true else R.drawable.ic_like
        }

        private fun rounding(value: Int): String {
            return when (value) {
                in 0..999 -> "$value"
                in 1000..1099 -> context.getString(R.string.value_1000_1099, value / 1000)
                in 1100..9999 -> context.getString(R.string.value_1100_9999, value / 1000, value % 1000 / 100)
                in 10000..10999 -> context.getString(R.string.value_10000_10999, value / 10000)
                in 11000..999999 -> context.getString(
                    R.string.value_11000_99999,
                    value / 10000,
                    value % 10000 / 1000
                )
                else -> context.getString(R.string.value_more_1000000, value / 100000, value % 100000 / 10000)
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem.postId == newItem.postId


        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
            newItem == oldItem

    }
}