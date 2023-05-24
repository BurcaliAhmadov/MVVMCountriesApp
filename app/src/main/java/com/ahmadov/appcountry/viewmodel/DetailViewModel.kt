package com.ahmadov.appcountry.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmadov.appcountry.model.Country
import com.ahmadov.appcountry.service.CountryDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): BaseViewModel(application){
    val countryLiveData= MutableLiveData<Country>()

    fun getDataFromRoom(uuid:Int){
      launch {
          val dao=CountryDatabase(getApplication()).countryDao()
          val country =dao.getCountry(uuid)
          countryLiveData.value=country
      }
    }
}