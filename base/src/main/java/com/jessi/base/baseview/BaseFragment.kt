package com.jessi.base.baseview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jessi.base.ext.getClazz

abstract class BaseFragment<VM : ViewModel, DB : ViewDataBinding> : Fragment() {
    lateinit var mViewModel: VM

    lateinit var mDatabind: DB

    private var isFirst = true
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDatabind = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mDatabind.lifecycleOwner = this
        mViewModel = initViewModel()
        initView(savedInstanceState)
        registorUIChange()
        initData()
        return mDatabind.root
    }
    
    override fun onResume() {
        super.onResume()
        onVisible()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isFirst = false
    }


    private fun initViewModel() : VM{
        return ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(getClazz(this))
    }

    private fun onVisible(){
        if (isFirst){
            isFirst = false
            lazyLoadData()
        }
    }


    @LayoutRes
    protected abstract fun getLayoutId() :  Int

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun registorUIChange()

    abstract fun initData()

    protected open fun lazyLoadData() {}
}