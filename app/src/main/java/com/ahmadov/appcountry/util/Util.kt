package com.ahmadov.appcountry.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.ahmadov.appcountry.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import retrofit2.http.Url


fun ImageView.downloadImageUrl (url: String?,placeholderDrawable: CircularProgressDrawable){

    val optins=RequestOptions()
        .placeholder(placeholderDrawable)
        .error(R.mipmap.ic_launcher_round)


    Glide.with(context)
        .setDefaultRequestOptions(optins)
        .load(url)
        .into(this)

}

fun placeHolderProgressBar(context:Context): CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth=8f
        centerRadius=40f
        start()
    }
}

@BindingAdapter("android:downloadUrl")
fun downloadImage(view:ImageView,url:String){
    url?.let {
        view.downloadImageUrl(it, placeHolderProgressBar(view.context))
    }

}