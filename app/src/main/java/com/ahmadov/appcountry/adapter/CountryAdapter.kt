package com.ahmadov.appcountry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ahmadov.appcountry.R
import com.ahmadov.appcountry.databinding.CountrylistRowBinding
import com.ahmadov.appcountry.model.Country

import com.ahmadov.appcountry.view.ListFragmentDirections


class CountryAdapter(val countryList:ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    private lateinit var binding: CountrylistRowBinding

   class CountryViewHolder(var binding: CountrylistRowBinding):RecyclerView.ViewHolder(binding.root){

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        binding=CountrylistRowBinding.inflate(inflater,parent,false)
        //val view=DataBindingUtil.inflate<CountrylistRowBinding>(inflater, R.layout.countrylist_row,parent,false)

        return CountryViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.binding.country=countryList[position]
        //holder.binding.listener=this

        holder.itemView.setOnClickListener {
            var uuid=holder.binding.uuid.text.toString().toInt()
            val action=ListFragmentDirections.actionListFragmentToDetailFragment(uuid)
            Navigation.findNavController(holder.binding.root).navigate(action)
        }




        /*
        holder.binding.name.text=countryList[position].countryName
        holder.binding.region.text=countryList[position].countryRegion
        holder.binding.imageView.downloadImageUrl(countryList[position].imageUrl, placeHolderProgressBar(holder.binding.root.context)  )

        holder.itemView.setOnClickListener {

            val action=ListFragmentDirections.actionListFragmentToDetailFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }*/
    }
    fun updateCountryList(newCountryList:List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    /*override fun onCountryClicked(v: View) {


        val uuid=binding.uuid.text.toString().toInt()
        val action=ListFragmentDirections.actionListFragmentToDetailFragment(uuid)
        Navigation.findNavController(binding.root).navigate(action)



    }*/
}