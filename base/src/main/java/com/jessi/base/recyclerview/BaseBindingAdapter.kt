package com.jessi.base.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.getBinding
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


abstract class BaseBindingAdapter<M, DB : ViewDataBinding>(var context : Context) : RecyclerView.Adapter<BaseBindViewHolder>() {

    protected open var mList = mutableListOf<M>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(context), getLayoutResId(viewType), parent, false)
        return BaseBindViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: BaseBindViewHolder, position: Int) {
        val binding : DB? = getBinding(holder.itemView)
        onBindVH(binding ,holder, position)
        binding?.executePendingBindings()
    }

    abstract fun onBindVH(binding: DB?, holder: ViewHolder, position: Int)

    /**
     * 根据type 获取布局
     */
    @LayoutRes
    protected abstract fun getLayoutResId(viewType : Int) : Int

    fun setNewDatas(mDatas : MutableList<M>){
        mList = mDatas
        notifyDataSetChanged()
    }

    fun addData(data: M) {
        mList.add(data)
        notifyItemInserted(mList.size)
    }

    fun addData(@IntRange(from = 0)position: Int , data: M){
        mList.add(position, data);
        notifyItemInserted(position);
        compatibilityDataSizeChanged(1)
    }

    fun addData(newData: Collection<M>) {
        mList.addAll(newData)
        notifyItemRangeInserted(mList.size - newData.size , newData.size)
        compatibilityDataSizeChanged(newData.size)
    }


     fun setData(index: Int, data: M) {
        if (index >= mList.size) {
            return
        }
         this.mList[index] = data
        notifyItemChanged(index)
    }

    fun remove(item : M) {
         var position = mList.indexOf(item)
        if (-1 == position)
            return
        remove(position)
    }

    fun clear(){
        mList.clear()
        notifyDataSetChanged()
    }

    fun remove(position: Int){
        if (position >= mList.size) return
        mList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mList.size - position)
    }

    private fun compatibilityDataSizeChanged(size: Int) {
        val dataSize = mList.size
        if (dataSize == size) {
            notifyDataSetChanged()
        }
    }

}