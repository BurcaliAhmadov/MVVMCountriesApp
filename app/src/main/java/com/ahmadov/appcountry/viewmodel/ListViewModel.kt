package com.ahmadov.appcountry.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmadov.appcountry.model.Country
import com.ahmadov.appcountry.service.CountryApiService
import com.ahmadov.appcountry.service.CountryDatabase
import com.ahmadov.appcountry.util.CustomSharedPreferences
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application): BaseViewModel(application) {
    private val countryApiService=CountryApiService()
    private val disposable=CompositeDisposable()
    private var customPreferences=CustomSharedPreferences(getApplication())

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
                    storeInSql(t)

                }

                override fun onError(e: Throwable) {
                    countryError.value=true
                    countryLoading.value=false
                    e.printStackTrace()
                }

            })

        )
    }

    private fun showCountries(countryList:List<Country>){
        countries.value=countryList
        countryLoading.value=false
        countryError.value=false
    }
    private fun storeInSql(list:List<Country>){
        launch {
            val countryDao=CountryDatabase(getApplication()).countryDao()
            countryDao.deleteAllCounties()
            val listLong = countryDao.insertAll(*list.toTypedArray())
            var i=0
            while (i<list.size){
                list[i].uuid=listLong[i].toInt()
                i=i+1
            }
            showCountries(list)
        }

        customPreferences.saveTime(System.nanoTime())


    }

}