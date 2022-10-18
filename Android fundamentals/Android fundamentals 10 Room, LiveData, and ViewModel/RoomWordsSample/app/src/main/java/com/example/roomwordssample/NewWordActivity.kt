package com.example.roomwordssample

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.roomwordssample.data.Word
import com.example.roomwordssample.databinding.ActivityNewWordBinding

class NewWordActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_REPLY =
            "com.example.android.roomwordssample.REPLY"
        const val EXTRA_UPDATE_REPLY =
            "com.example.android.roomwordssample.REPLY_UPDATE"
    }

    private lateinit var binding: ActivityNewWordBinding
    private val viewModel: WordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wordUpdate: Word? = intent.getSerializableExtra(MainActivity.EXTRA_UPDATE) as Word?
        if (wordUpdate != null) {
            binding.editWord.setText(wordUpdate.mWord)
            binding.buttonSave.setOnClickListener {
                val word = wordUpdate.copy(mWord = binding.editWord.text.toString())
                val replyIntent = Intent()
                if (TextUtils.isEmpty(binding.editWord.text.toString())) {
                    setResult(RESULT_CANCELED, replyIntent)
                } else {
                    replyIntent.putExtra(EXTRA_UPDATE_REPLY, word)
                    setResult(RESULT_OK, replyIntent)
                }
                finish()
            }
        } else {
            binding.buttonSave.setOnClickListener {
                val replyIntent = Intent()
                if (TextUtils.isEmpty(binding.editWord.text.toString())) {
                    setResult(RESULT_CANCELED, replyIntent)
                } else {
                    replyIntent.putExtra(EXTRA_REPLY, binding.editWord.text.toString())
                    setResult(RESULT_OK, replyIntent)
                }
                finish()
            }
        }


    }
}