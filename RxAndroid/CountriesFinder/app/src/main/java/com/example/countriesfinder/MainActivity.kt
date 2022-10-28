package com.example.countriesfinder

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.countriesfinder.data.Country
import com.example.countriesfinder.data.CountryDatabase
import com.example.countriesfinder.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "DHP"
    }
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CountryViewModel by viewModels {
        CountryViewModelFactory(
            (application as CountryApplication).database.countryDao()
        )
    }
    private lateinit var disposable: Disposable
    var adapter: CountryAdapter? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        setSupportActionBar(binding.toolBar)

         adapter = CountryAdapter { country ->
            Toast.makeText(this@MainActivity, country.name, Toast.LENGTH_SHORT).show()
        }
        binding.rv.adapter = adapter

        viewModel.message.observe(this) { message ->
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }

        val buttonClickStream = createButtonClickObserable()
            .toFlowable(BackpressureStrategy.LATEST)

        val textChangeStream = createTextChangeObserable()
            .toFlowable(BackpressureStrategy.BUFFER)

        val searchTextFlowable = Flowable.merge<String>(buttonClickStream, textChangeStream)
        disposable = searchTextFlowable
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { showProgress() }
            .observeOn(Schedulers.computation())
            .map { CountryDatabase.getDatabase(this).countryDao().search("%$it%") }

            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("DHP", "list: ${it.size.toString()} -- ${it.toString()}")
                hideProgress()
                adapter!!.submitList(it)

            }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        RxSearchObserve.fromView(searchView)
            .filter { it.isNotEmpty() }
            .map { it.lowercase(Locale.ROOT) }
            .observeOn(Schedulers.io())
            //.map { viewModel.search("%$it%") }
            /*.flatMap{ i->
                Observable.create<List<Country>> { e->
                    e.onNext(viewModel.search("%$i%"))
                }
            }*/
            .switchMap { Observable.create<List<Country>> {e->
                e.onNext(viewModel.search("%$it%"))
            } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { v-> adapter!!.submitList(v)},
                {e -> Log.d(TAG,"Error: ${e.message}")},
                { Log.d(TAG,"Done!") }
            )

        return true
    }

    override fun onStop() {
        super.onStop()
        if (!disposable.isDisposed) disposable.dispose()
    }


    private fun showProgress() {
        binding.progressBar.visibility = VISIBLE
    }

    private fun hideProgress() {
        binding.progressBar.visibility = GONE
    }


    private fun createButtonClickObserable() = Observable.create<String> { emitter ->
        binding.btnSearch.setOnClickListener {
            emitter.onNext(binding.edt.text.toString())
        }
        emitter.setCancellable {
            binding.btnSearch.setOnClickListener(null)
        }
    }

    private fun createTextChangeObserable() = Observable.create<String> { emitter ->
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                s?.toString()?.let { emitter.onNext(it) }
            }

            override fun afterTextChanged(p0: Editable?) = Unit

        }
        binding.edt.addTextChangedListener(textWatcher)

        emitter.setCancellable {
            binding.edt.removeTextChangedListener(textWatcher)
        }
    }.filter { it.length > 2 }
        .debounce(1000, TimeUnit.MILLISECONDS)

}