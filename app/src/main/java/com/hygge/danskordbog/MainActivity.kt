package com.hygge.danskordbog

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hygge.danskordbog.db.*

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private val wordViewModel: DictionaryViewModel by viewModels {
        WordViewModelFactory((application as DictionaryApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = DictionaryListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel.danishWord.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { it }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, DisplayVocabularyActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            var firstWord = intentData?.getStringExtra(DisplayVocabularyActivity.EXTRA_REPLY)
            var secondWord = intentData?.getStringExtra(DisplayVocabularyActivity.Super_Reply)



            if (firstWord != null && secondWord != null) {

            val word = Dictionary(firstWord, secondWord)
                wordViewModel.insertVocabulary(word)
            }

        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}