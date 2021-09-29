package com.example.imdbfilmapp.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.imdbfilmapp.R
import com.squareup.picasso.Picasso

fun ImageView.downloadImage(url: String, context: Context){
    Picasso.get().load(url).placeholder(placeHolder(context)).error(R.drawable.ic_launcher_foreground).into(this)
}

fun placeHolder(context : Context) : CircularProgressDrawable {

    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }

}