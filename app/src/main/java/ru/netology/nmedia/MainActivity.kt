package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostAdapter(viewModel, this)
        binding.postsRecycleView.adapter = adapter

        viewModel.data.observe(this) { postsFromData ->
            adapter.submitList(postsFromData)
        }

        binding.createNewPost.setOnClickListener {
            viewModel.onAddClicked()
        }

        val postContentActivityLauncher = registerForActivityResult(
            EditAndCreatePostActivity.EditAndCreatePostResultContract
            ) { postContent ->
                postContent ?: return@registerForActivityResult
            if (postContent.size == 2) viewModel.onSaveButtonClicked(postContent[0], Uri.parse(postContent[1]))
            else viewModel.onSaveButtonClicked(postContent[0], null)
            }


        viewModel.navigateToPostContentScreenEvent.observe(this)
        {postId ->
            Log.d("MyLog", "observe = $postId")
            postContentActivityLauncher.launch(postId)
        }

        viewModel.sharePostContent.observe(this) {postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = ContactsContract.Contacts.CONTENT_TYPE
            }
            val shareIntent = Intent.createChooser(intent, getString(android.R.string.search_go))
            startActivity(shareIntent)
        }

        viewModel.playVideoContent.observe(this) {urlVideo ->
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = urlVideo
            }

            val videoPlayIntent = Intent.createChooser(intent, getString(android.R.string.search_go))
            startActivity(videoPlayIntent)
        }
    }
}
