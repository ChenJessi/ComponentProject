package com.jessi.base.baseview

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import com.jessi.base.ext.getClazz

/**
 * @author Created by CHEN on 2020/4/30
 * @email 188669@163.com
 */
abstract class BaseVmDbActivity <VM : ViewModel, DB : ViewDataBinding> : AppCompatActivity() {

    lateinit var mViewModel: VM

    lateinit var mDatabind: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewDataBinding()
        mViewModel = initViewModel()
        initView()
        initListener()
        initData()
    }

    private fun initViewDataBinding() {
        mDatabind = DataBindingUtil.setContentView(this, getLayoutId())
        mDatabind.lifecycleOwner = this
    }

    private fun initViewModel() : VM{
        return ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(getClazz(this))
    }

    @LayoutRes
    protected abstract fun getLayoutId() :  Int

    protected abstract fun initView()

    protected abstract fun initListener()

    protected abstract fun initData()
}