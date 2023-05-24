package com.ahmadov.appcountry.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListPopupWindow
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.ahmadov.appcountry.adapter.CountryAdapter
import com.ahmadov.appcountry.databinding.FragmentListBinding
import com.ahmadov.appcountry.viewmodel.ListViewModel


class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel:ListViewModel
    val countryAdapter=CountryAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentListBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refreshData()
        binding.countryList.layoutManager=LinearLayoutManager(context)
        binding.countryList.adapter=countryAdapter
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.countryList.visibility=View.GONE
            binding.countryError.visibility=View.GONE
            binding.countryLoading.visibility=View.VISIBLE
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.refreshFromApi()

        }
        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries->
            countries?.let{
                binding.countryList.visibility=View.VISIBLE
                binding.countryLoading.visibility=View.GONE
                binding.countryError.visibility=View.GONE
                countryAdapter.updateCountryList(it)
            }

        })
        viewModel.countryError.observe(viewLifecycleOwner, Observer {
            if(it) {
                binding.countryList.visibility = View.GONE
                binding.countryLoading.visibility = View.GONE
                binding.countryError.visibility = View.VISIBLE
            }
            else{
                binding.countryError.visibility=View.GONE
            }

        })
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.countryError.visibility=View.GONE
                binding.countryList.visibility=View.GONE
                binding.countryLoading.visibility=View.VISIBLE
            }
            else{
                binding.countryLoading.visibility=View.GONE
            }

        })
    }

}