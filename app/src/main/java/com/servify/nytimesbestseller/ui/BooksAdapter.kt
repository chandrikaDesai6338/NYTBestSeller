package com.servify.nytimesbestseller.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.servify.nytimesbestseller.databinding.ItemBookLayoutBinding
import com.servify.nytimesbestseller.model.BookResult

/**
 * Created by Chan on 16/08/21.
 */
class BooksAdapter(val books : ArrayList<BookResult>, val onListItemClickListener: OnListItemClickListener) :
    RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemBookLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(ItemBookLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvTitle.text = books[position].book_details.get(0).title
        if (books[position].book_details.get(0).description.isNotEmpty()){
            holder.binding.tvDesc.text = books[position].book_details.get(0).description
        }else {
            holder.binding.tvDesc.visibility = View.GONE
        }
       var author :String = "By "+ books[position].book_details.get(0).author
        holder.binding.tvAuthor.text = author
        if(books[position].book_details.get(0).price > 0){
            val price:String = "₹ "+ books[position].book_details.get(0).price.toString()
            holder.binding.tvPrice.text = price
        }else{
            holder.binding.tvPrice.visibility = View.GONE
        }
        holder.binding.root.setOnClickListener {
            onListItemClickListener.onItemClick(position)
        }
    }
}