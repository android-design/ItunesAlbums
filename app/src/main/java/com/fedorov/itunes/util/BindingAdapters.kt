package com.fedorov.itunes.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


@BindingAdapter("url", "errorImage")
fun loadImage(view: ImageView, url: String, errorImage: Drawable) {
    Picasso.get().load(url).into(view)
}