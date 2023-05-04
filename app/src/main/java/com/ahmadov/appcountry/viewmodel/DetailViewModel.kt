package com.ahmadov.appcountry.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmadov.appcountry.model.Country

class DetailViewModel: ViewModel() {
    val countryLiveData= MutableLiveData<Country>()

    fun getDataFromRoom(){
        val country=Country("Azerbaijan","Asia","Baku","AZN","Azerbaijani","www.ss.com")
        countryLiveData.value=country
    }
}