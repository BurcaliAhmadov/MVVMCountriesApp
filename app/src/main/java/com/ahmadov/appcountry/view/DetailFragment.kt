package com.ahmadov.appcountry.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.ahmadov.appcountry.view.DetailFragmentArgs
import com.ahmadov.appcountry.R
import com.ahmadov.appcountry.databinding.FragmentDetailBinding
import com.ahmadov.appcountry.util.downloadImageUrl
import com.ahmadov.appcountry.util.placeHolderProgressBar
import com.ahmadov.appcountry.viewmodel.DetailViewModel


class DetailFragment : Fragment() {
    private lateinit var binding:FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    private  var countryUuid=0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDetailBinding.inflate(LayoutInflater.from(requireContext()),container,false)


        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            countryUuid= DetailFragmentArgs.fromBundle(it).countryUuid

        }
        viewModel=ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)
        obserLiveData()

    }
    private fun obserLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {country->
            country?.let{


                binding.countryName.text=country.countryName
                binding.countryCapital.text=country.countryCapital
                binding.countryCurrency.text=country.countryCurrency
                binding.countryLanguage.text=country.countryLanguage
                binding.countryRegion.text=country.countryRegion
                binding.countryImage.downloadImageUrl(country.imageUrl, placeHolderProgressBar(binding.root.context))
            }

        })
    }

}