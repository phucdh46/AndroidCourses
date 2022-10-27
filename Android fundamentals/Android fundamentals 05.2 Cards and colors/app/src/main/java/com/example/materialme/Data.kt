package com.example.materialme

import android.app.Application

class Data() {
    fun initialzeData(app: Application): ArrayList<Sport> {
        val tilte = app.resources.getStringArray(R.array.sports_titles)
        val infor = app.resources.getStringArray(R.array.sports_info)
        val imgs = app.resources.obtainTypedArray(R.array.sports_images)
        val list = arrayListOf<Sport>()
        for (i in 0 until tilte.size) {
            list.add(
                Sport(
                    tilte[i], infor[i], imgs.getResourceId(i, 0)
                )
            )

        }
        return list
    }
}