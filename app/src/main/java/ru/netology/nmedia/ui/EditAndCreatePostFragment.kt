package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.data.BundleProperties
import ru.netology.nmedia.databinding.FragmentEditAndCreatePostBinding
import ru.netology.nmedia.util.Constants
import ru.netology.nmedia.viewModel.PostViewModel

class EditAndCreatePostFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>()

    private val postId by lazy {
        val args by navArgs<EditAndCreatePostFragmentArgs>()
        args.postId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentEditAndCreatePostBinding.inflate(layoutInflater, container, false).also { binding ->
        if (postId != null) {
            val editPost = viewModel.getPostFromDate(postId!!.toInt())
            binding.messagePost.setText(editPost.message)
            if (editPost.urlVideo != null) {
                binding.urlText.setText(editPost.urlVideo.toString())
                binding.urlText.visibility = View.VISIBLE
            }
        }
        binding.messagePost.requestFocus()
        binding.okMessagePost.setOnClickListener {
            onClickOkMessagePost(binding)
        }

        binding.addUrlButton.setOnClickListener {
            val urlText = binding.urlText
            if (urlText.isVisible) urlText.visibility = View.GONE else urlText.visibility =
                View.VISIBLE
        }
    }.root

    private fun onClickOkMessagePost(binding: FragmentEditAndCreatePostBinding) {
        val text = binding.messagePost.text.toString()
        val url = binding.urlText.text.toString().ifBlank { null }
        if (text.isNotBlank()) {
            val resultBundle = Bundle(1)
            resultBundle.putSerializable(Constants.RESULT_KEY, BundleProperties(text, url))
            setFragmentResult(Constants.REQUEST_KEY, resultBundle)
        }
        findNavController().popBackStack()
    }
}

