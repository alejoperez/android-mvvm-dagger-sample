package com.mvvm.dagger.sample.photos

import com.mvvm.dagger.sample.R
import com.mvvm.dagger.sample.base.BaseRecyclerViewAdapter
import com.mvvm.dagger.sample.data.room.Photo

class PhotosAdapter(photoList: List<Photo>?,listener: BaseRecyclerViewAdapter.OnItemClickListener) : BaseRecyclerViewAdapter(photoList,listener) {

    override fun getItemLayoutId(): Int = R.layout.item_photo

}