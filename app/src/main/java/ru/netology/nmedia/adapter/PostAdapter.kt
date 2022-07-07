package ru.netology.nmedia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.OnePostBinding

internal class PostAdapter(
    private val interactionListener: PostInteractionListener,
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

        private lateinit var post: Post

        init {
            binding.buttonLike.setOnClickListener {
                interactionListener.onLikeClicked(post)
            }
            binding.videoPlay.setOnClickListener { interactionListener.onPlayVideoClicked(post) }
            binding.buttonShare.setOnClickListener { interactionListener.onShareClicked(post) }
            binding.buttonMenu.setOnClickListener { popupMenu.show() }
            binding.postFullText.setOnClickListener {interactionListener.onPostClicked(post)}
        }

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.buttonMenu).apply {
                inflate(R.menu.option_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.delete_post -> {
                            interactionListener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit_post -> {
                            interactionListener.onEditClicked(post)
                            true
                        }
                        else -> {
                            false
                        }
                    }
                }
            }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                postAuthor.text = post.author
                postIcon.setImageResource(post.icon)
                postDate.text = post.date
                postFullText.text = post.message
                buttonLike.text = interactionListener.rounding(post.amountLike)
                buttonShare.text = interactionListener.rounding(post.amountShare)
                valueSee.text = interactionListener.rounding(post.amountView)
                if (post.urlVideo != null) videoView.visibility = View.VISIBLE
                buttonLike.isChecked = post.isLike
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