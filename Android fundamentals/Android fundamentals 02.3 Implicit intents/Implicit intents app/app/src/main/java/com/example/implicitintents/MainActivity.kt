package com.example.implicitintents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun openWebsite(view: View?) {
        val mWebsiteEditText: EditText = findViewById(R.id.website_edittext)
        val url = mWebsiteEditText.getText().toString()
        val webpage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent)
        } else {
            Log.d("ImplicitIntents", "Can't handle this!");
        }
    }

    fun openLocation(view: View?) {
        val mLocationEditText: EditText = findViewById(R.id.location_edittext)
        val loc = mLocationEditText.getText().toString()
        val addressUri = Uri.parse("geo:0,0?q=" + loc)
        val intent = Intent(Intent.ACTION_VIEW, addressUri)

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }

    fun shareText(view: View?) {
        val mShareTextEditText: EditText = findViewById(R.id.share_edittext)
        val txt = mShareTextEditText.getText().toString()
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
            .from(this)
            .setType(mimeType)
            .setChooserTitle("Share this text with: ")
            .setText(txt)
            .startChooser()
    }
}