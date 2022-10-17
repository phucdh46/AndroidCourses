package com.example.clickableimages

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class OrderActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        val message = intent.getStringExtra(MainAcivity.EXTRA_MESSAGE)
        val tv = findViewById<TextView>(R.id.tvMessage)
        tv.text = message

        val spinner = findViewById<Spinner>(R.id.label_spinner)

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this, R.array.labels_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.let {
            it.onItemClickListener
            it.adapter = adapter
        }
    }

    fun onRadioButtonClicked(view: View) {
        val checked = (view as RadioButton).isChecked
        when (view.id) {
            R.id.sameday -> if (checked) displayToast(getString(R.string.same_day_messenger_service))
            R.id.nextday -> if (checked) displayToast(getString(R.string.next_day_ground_delivery))
            R.id.pickup -> if (checked) displayToast(getString(R.string.pick_up))
        }
    }

    private fun displayToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
        val spinnerLabel = adapterView?.getItemAtPosition(i).toString();
        displayToast(spinnerLabel)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}