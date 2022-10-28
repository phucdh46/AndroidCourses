package com.example.rxkotlinexample

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

class Test {
}
fun main(){
    val list = listOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon")

  /*  list.toObservable()
        .filter { it.length > 5 }
        .subscribeBy(
            onNext = (::println),
            onError = {it.printStackTrace()},
            onComplete = {(println("Done"))}
        )*/

    val service = Retrofit.Builder()
        .baseUrl("http://search.maven.org")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(MavenSearchService::class.java)

    service.search("rxkotlin")
        .flatMapIterable { it.response.docs }
        .subscribe (
            { println("${it.id} - ${it.latestVersion}") },
            {it.printStackTrace()},
            { println("DONE") }
                )
}

data class SearchResultEntry(val id : String, val latestVersion : String)
data class SearchResults(val docs : List<SearchResultEntry>)
data class MavenSearchResponse(val response : SearchResults)

interface MavenSearchService{
    @GET("/solrsearch/select?wt=json")
    fun search(@Query("q") s : String, @Query("rows") rows : Int = 20) : Observable<MavenSearchResponse>
}