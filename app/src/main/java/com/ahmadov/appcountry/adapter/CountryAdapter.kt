package com.ahmadov.appcountry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ahmadov.appcountry.databinding.CountrylistRowBinding
import com.ahmadov.appcountry.model.Country
import com.ahmadov.appcountry.util.downloadImageUrl
import com.ahmadov.appcountry.util.placeHolderProgressBar
import com.ahmadov.appcountry.view.ListFragmentDirections


class CountryAdapter(val countryList:ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    private lateinit var binding:CountrylistRowBinding

   class CountryViewHolder(val binding: CountrylistRowBinding):RecyclerView.ViewHolder(binding.root){

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding=CountrylistRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.binding.name.text=countryList[position].countryName
        holder.binding.region.text=countryList[position].countryRegion
        holder.binding.imageView.downloadImageUrl(countryList[position].imageUrl, placeHolderProgressBar(holder.binding.root.context)  )
        holder.itemView.setOnClickListener {
            val action=ListFragmentDirections.actionListFragmentToDetailFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }
    fun updateCountryList(newCountryList:List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}