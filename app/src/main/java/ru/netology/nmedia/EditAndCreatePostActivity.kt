package ru.netology.nmedia

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.core.view.isVisible
import ru.netology.nmedia.databinding.ActivityEditAndCreatePostBinding
import ru.netology.nmedia.viewModel.PostViewModel
import kotlin.collections.ArrayList

class EditAndCreatePostActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditAndCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val postId = intent?.getIntExtra(POST_ID_KEY, 0)
        if (postId != 0) {
            val editPost = viewModel.getPostFromDate(postId)
            binding.messagePost.setText(editPost.message)
            if (editPost.urlVideo != null) binding.urlText.setText(editPost.urlVideo.toString())
        }
        binding.messagePost.requestFocus()
        binding.okMessagePost.setOnClickListener {
            val intent = Intent()
            val text = binding.messagePost.text
            val url = binding.urlText.text
            when {
                text.isBlank() -> setResult(Activity.RESULT_CANCELED, intent)
                text.isNotBlank() && url.isBlank() -> {
                    val list: ArrayList<String> = arrayListOf()
                    list.add(text.toString())
                    intent.apply {
                        putExtra(TEXT_MESSAGE_KEY, list)}
                    setResult(Activity.RESULT_OK, intent)
                }
                else -> {
                    val list: ArrayList<String> = arrayListOf()
                    list.add(text.toString())
                    list.add(url.toString())
                    intent.apply {
                        putExtra(TEXT_MESSAGE_KEY, list)}
                    setResult(Activity.RESULT_OK, intent)
                }
            }
            finish()
        }

        binding.addUrlButton.setOnClickListener {
            val urlText = binding.urlText
            if (urlText.isVisible) urlText.visibility = View.GONE else urlText.visibility = View.VISIBLE
        }
    }

    object EditAndCreatePostResultContract : ActivityResultContract<Int?, ArrayList<String>?>() {

        override fun createIntent(context: Context, input: Int?): Intent {
            return Intent(context, EditAndCreatePostActivity::class.java).apply {
                putExtra(POST_ID_KEY, input)
            }
        }

        override fun parseResult(resultCode: Int, intent: Intent?): ArrayList<String>? =
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringArrayListExtra(TEXT_MESSAGE_KEY)
            } else null
    }

    companion object {
        private const val TEXT_MESSAGE_KEY = "textMessageKey"
        private const val POST_ID_KEY = "postIdKey"

    }
}

