package com.ahmadov.appcountry.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmadov.appcountry.model.Country
import com.ahmadov.appcountry.service.CountryApiService
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel: ViewModel() {
    private val countryApiService=CountryApiService()
    private val disposable=CompositeDisposable()

    val countries =MutableLiveData<List<Country>>()
    val countryError =MutableLiveData<Boolean>()
    val countryLoading=MutableLiveData<Boolean>()

    fun refreshData(){
        getDataFromApi()
    }
    fun getDataFromApi(){
        countryLoading.value=true
        disposable.add(countryApiService.getData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object :DisposableSingleObserver<List<Country>>(){
                override fun onSuccess(t: List<Country>) {

                    countries.value=t
                    countryLoading.value=false
                    countryError.value=false
                }

                override fun onError(e: Throwable) {
                    countryError.value=true
                    countryLoading.value=false
                    e.printStackTrace()
                }

            })

        )
    }
}