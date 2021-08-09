package com.hygge.danskordbog

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ebner.roomdatabasebackup.core.RoomBackup
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

        val recyclerView1 = findViewById<RecyclerView>(R.id.recyclerview1)
        val recyclerView2 = findViewById<RecyclerView>(R.id.recyclerview2)
        val adapter1 = DanishListAdapter()
        val adapter2 = EnglishListAdapter()
        recyclerView1.adapter = adapter1
        recyclerView1.layoutManager = LinearLayoutManager(this)

        recyclerView2.adapter = adapter2
        recyclerView2.layoutManager = LinearLayoutManager(this)

        wordViewModel.danishWord.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter1.submitList(it) }
        }

        wordViewModel.englishWord.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter2.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, DisplayVocabularyActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

//        val btnBackup = findViewById<FloatingActionButton>(R.id.btn_backup)
//        btnBackup.setOnClickListener {
//            RoomBackup()
//                .context(this)
//                .useExternalStorage(true)
//                .database(DictionaryDatabase.getInstance(this))
//                .apply {
//                    onCompleteListener{success, message ->
//                        Log.d(TAG, "success: $success, message: $message")
//                        if (success) restartApp(Intent(this@MainActivity, MainActivity::class.java))
//                    }
//                }
//                .backup()
//        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            var firstWord = intentData?.getStringExtra(DisplayVocabularyActivity.EXTRA_REPLY)
            var secondWord = intentData?.getStringExtra(DisplayVocabularyActivity.SUPER_REPLY)



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