package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
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

        binding.saveButton.setOnClickListener {
            with(binding) {
                val content = editTextPost.text.toString()
                viewModel.onSaveButtonClicked(content)
                closeEdit.visibility = View.GONE
                editMessage.text = null
                editTextPost.clearFocus()
                editTextPost.hideKeyboard()
            }
        }
        viewModel.currentPost.observe(this) {currentPost ->
            with(binding) {
                val content = currentPost?.message
                editTextPost.setText(content)
                if (content != null) {
                    closeEdit.visibility = View.VISIBLE
                    editMessage.text = content
                    editTextPost.requestFocus()
                    editTextPost.showKeyboard()
                } else {
                    editTextPost.clearFocus()
                    editTextPost.hideKeyboard()
                }
            }
        }

        binding.closeEditMessageButtom.setOnClickListener {
            with(binding) {
                closeEdit.visibility = View.GONE
                editMessage.text = null
                editTextPost.text = null
                editTextPost.clearFocus()
                editTextPost.hideKeyboard()
            }
        }
    }
}
