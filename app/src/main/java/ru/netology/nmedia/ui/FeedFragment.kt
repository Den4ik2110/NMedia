package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.data.BundleProperties
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.util.Constants
import ru.netology.nmedia.viewModel.PostViewModel

class FeedFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(
            requestKey = Constants.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != Constants.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent = bundle.getSerializable(Constants.RESULT_KEY) as BundleProperties
            viewModel.onSaveButtonClicked(newPostContent.text, newPostContent.url)
        }

        viewModel.navigateToPostContentScreenEvent.observe(this)
        { postId ->
            val direction = FeedFragmentDirections.toEditAndCreatePostFragment(postId)
            findNavController().navigate(direction)
        }

        viewModel.navigateToPostClickedEvent.observe(this)
        { postId ->
            val direction = FeedFragmentDirections.toFullPostFragment(postId)
            findNavController().navigate(direction)
        }

        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = ContactsContract.Contacts.CONTENT_TYPE
            }
            val shareIntent = Intent.createChooser(intent, getString(android.R.string.search_go))
            startActivity(shareIntent)
        }

        viewModel.playVideoContent.observe(this) { urlVideo ->
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(urlVideo)
            }

            val videoPlayIntent =
                Intent.createChooser(intent, getString(android.R.string.search_go))
            startActivity(videoPlayIntent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFeedBinding.inflate(layoutInflater, container, false).also { binding ->
        val adapter = PostAdapter(viewModel)
        binding.postsRecycleView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { postsFromData ->
            adapter.submitList(postsFromData)
        }

        binding.createNewPost.setOnClickListener {
            viewModel.onAddClicked()
        }
    }.root
}

