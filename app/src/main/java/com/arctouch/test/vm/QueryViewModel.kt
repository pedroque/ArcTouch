package com.arctouch.test.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.arctouch.test.BR


class QueryViewModel(s: String) : BaseObservable(){
    val data: LiveData<String>
        get() = mutableQuery

    private val mutableQuery = MutableLiveData<String>()

    init {
        mutableQuery.value = s
    }

    @Bindable
    fun getQuery() = data.value!!

    fun setQuery(query: String) {
        if (this.data.value != query) {
            mutableQuery.value = query
            notifyPropertyChanged(BR.query)
        }
    }

}