package com.ahmadov.appcountry.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmadov.appcountry.model.Country

class ListViewModel: ViewModel() {
    val countries =MutableLiveData<List<Country>>()
    val countryError =MutableLiveData<Boolean>()
    val countryLoading=MutableLiveData<Boolean>()

    fun refreshData(){

        val country=Country("Azerbaijan","Asia","Baku","AZN","Azerbaijani","www.ss.com")
        val country2=Country("Turkey","Asia","Ankara","TYL","Turkish","www.ss.com")
        val country3=Country("Pakistan","Asia","Islamabad","PKR","Urdu","www.ss.com")

        val countryList= arrayListOf<Country>(country,country2,country3)
        countries.value=countryList
        countryError.value=false
        countryLoading.value=false
    }
}