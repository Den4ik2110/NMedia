package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var likes = 10
        binding.valueLike.text = likes.toString()
        var check = false

        binding.apply {
            buttonLike.setOnClickListener {
                Toast.makeText(this@MainActivity, "Like!", Toast.LENGTH_LONG).show()
                if (!check) {
                    binding.buttonLike.setImageResource(R.drawable.ic_like_polni)
                    likes++
                    valueLike.text = likes.toString()
                } else {
                    binding.buttonLike.setImageResource(R.drawable.ic_like)
                    likes--
                    valueLike.text = likes.toString()
                }
                check = !check
            }

        }

    }
}
