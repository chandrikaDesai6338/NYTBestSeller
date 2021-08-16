package com.servify.nytimesbestseller.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.servify.nytimesbestseller.databinding.ItemLayoutBinding
import com.servify.nytimesbestseller.model.Result

class BestSellerAdapter(
    val bestSellerList: ArrayList<Result>?,
    var onListItemClickListener: OnListItemClickListener
) : RecyclerView.Adapter<BestSellerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nameDate : String = bestSellerList?.get(position)?.list_name + " ("+ bestSellerList?.get(position)?.newest_published_date+ ")"
        holder.binding.tvBestSeller.text = nameDate
        holder.binding.root.setOnClickListener {
            onListItemClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        if (bestSellerList != null) {
            return bestSellerList.size
        }
        return 0
    }

}
