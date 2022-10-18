package com.example.roomwordssample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomwordssample.data.Word
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_UPDATE = "update"
    }

    private val viewModel: WordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        /*val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)*/
        fab.setOnClickListener { view ->
//            for (i in 0..10) {
//                viewModel.insertWord(Word(i.toString()))
//            }
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
       .setAction("Action", null).show()*/

            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            resultLauncher.launch(intent)
        }


        val adapter = WordListAdapter { word ->
            Toast.makeText(this@MainActivity, word.toString(), Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            intent.putExtra(EXTRA_UPDATE, word)
            resultLauncher.launch(intent)
        }
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
        viewModel.words.observe(this) {
            adapter.submitList(it)
        }

        val helper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val postion = viewHolder.adapterPosition
                val myWord = adapter.getWordAtPosition(postion)
                Toast.makeText(
                    this@MainActivity, "Deleting " +
                            myWord.mWord, Toast.LENGTH_LONG
                ).show()
                viewModel.deleteWord(myWord)
            }

        })
        helper.attachToRecyclerView(recyclerview)


    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val intent = it.data
                val wordAdd = intent?.getStringExtra(NewWordActivity.EXTRA_REPLY)
                val wordUpdate =
                    intent?.getSerializableExtra(NewWordActivity.EXTRA_UPDATE_REPLY) as Word?
                if (wordAdd != null) {
                    viewModel.insertWord(Word(null, wordAdd))
                } else {
                    viewModel.updateWord(wordUpdate!!)
                }
            } else {
                Toast.makeText(this, "Word must not empty", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.clear_data -> {
                // Add a toast just for confirmation
                Toast.makeText(
                    this, "Clearing the data...",
                    Toast.LENGTH_SHORT
                ).show();

                // Delete the existing data
                viewModel.deleteAllWords();
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }*/
}