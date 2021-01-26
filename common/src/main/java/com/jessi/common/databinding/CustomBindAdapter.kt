package com.jessi.common.databinding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.jessi.base.recyclerview.BaseBindingAdapter
import com.jessi.common.glide.GlideApp


object CustomBindAdapter {
    /**
     * glide加载图片
     */
    @BindingAdapter(value = ["imageUrl", "error" , "placeHolder"], requireAll = false)
    @JvmStatic
    fun loadImage(imageView: ImageView, url : String , error : Drawable? , placeHolder : Drawable?){
        GlideApp.with(imageView.context)
            .load(url)
            .transition(withCrossFade())
            .placeholder(placeHolder)
            .error(error)
            .centerCrop()
            .into(imageView)

    }

    /**
     * recyclerview
     */
    @JvmStatic
    @BindingAdapter(value = ["adapter","layoutManager"], requireAll = false)
    fun setAdapter(recyclerView: RecyclerView, adapter: BaseBindingAdapter<*, *>, layoutManager : RecyclerView.LayoutManager) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }
}