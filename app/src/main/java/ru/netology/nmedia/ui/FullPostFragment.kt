package ru.netology.nmedia.ui

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentFullPostBinding
import ru.netology.nmedia.viewModel.PostViewModel

class FullPostFragment : Fragment() {

    private val viewModel by activityViewModels<PostViewModel>()

    private val postId by lazy {
        val args by navArgs<FullPostFragmentArgs>()
        args.postId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFullPostBinding.inflate(layoutInflater, container, false).also { binding ->
        val itPost = viewModel.getPostFromDate(postId)

        val popupMenu by lazy {
            PopupMenu(context, binding.buttonMenu).apply {
                inflate(R.menu.option_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.delete_post -> {
                            viewModel.onRemoveClicked(itPost)
                            findNavController().popBackStack()
                            true
                        }
                        R.id.edit_post -> {
                            viewModel.onEditClicked(itPost)
                            true
                        }
                        else -> {
                            false
                        }
                    }
                }
            }
        }

        binding.buttonLike.setOnClickListener { viewModel.onLikeClicked(itPost) }
        binding.videoPlay.setOnClickListener { viewModel.onPlayVideoClicked(itPost) }
        binding.buttonShare.setOnClickListener { viewModel.onShareClicked(itPost) }

        viewModel.data.observe(viewLifecycleOwner) {postsFromData ->
            postsFromData.forEach {
                if (it.postId == postId) {
                    binding.buttonLike.text = viewModel.rounding(it.amountLike)
                    binding.buttonShare.text = viewModel.rounding(it.amountShare)
                    binding.valueSee.text = viewModel.rounding(it.amountView)
                }
            }
        }

        binding.apply {
            postAuthor.text = itPost.author
            postIcon.setImageResource(itPost.icon)
            postDate.text = itPost.date
            postFullText.text = itPost.message
            if (itPost.urlVideo != null) videoView.visibility = View.VISIBLE
            buttonLike.isChecked = itPost.isLike
            binding.buttonMenu.setOnClickListener { popupMenu.show() }
        }

        viewModel.sharePostContent.observe(viewLifecycleOwner) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = ContactsContract.Contacts.CONTENT_TYPE
            }
            val shareIntent = Intent.createChooser(intent, getString(android.R.string.search_go))
            startActivity(shareIntent)
        }

        viewModel.navigateToPostContentScreenEvent.observe(viewLifecycleOwner)
        { postId ->
            val direction = FullPostFragmentDirections.toEditAndCreatePostFragmentFromFullPost(postId)
            findNavController().navigate(direction)
        }

    }.root
}