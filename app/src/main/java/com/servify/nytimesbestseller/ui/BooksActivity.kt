package com.servify.nytimesbestseller.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.servify.nytimesbestseller.BestSellerViewModel
import com.servify.nytimesbestseller.ViewModelFactory
import com.servify.nytimesbestseller.databinding.ActivityBooksBinding
import com.servify.nytimesbestseller.model.BookResult
import com.servify.nytimesbestseller.model.Status
import com.servify.nytimesbestseller.network.ApiHelper
import com.servify.nytimesbestseller.network.Rest

class BooksActivity : AppCompatActivity() {
    private lateinit var viewModel : BestSellerViewModel
    private lateinit var binding : ActivityBooksBinding
    private lateinit var bookAdapter: BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        val list_name_encoded : String? = intent.getStringExtra("list_name_encoded")
        if (!list_name_encoded.isNullOrBlank()) getBooks(list_name_encoded)
    }

    private fun setUpViewModel(){
        viewModel = ViewModelProviders.of(this, ViewModelFactory(ApiHelper(Rest.apiService))).get(
            BestSellerViewModel::class.java
        )
    }

    private fun getBooks(list_name_encoded: String){
        viewModel.getBestSellerBooksList(list_name_encoded).observe(
            this,
            Observer { bestSellerbook ->
                when (bestSellerbook.status) {
                    Status.SUCCESS -> {
                        binding.progressCircular1.visibility = View.GONE
                        binding.tvErrormsg1.visibility = View.GONE
                        binding.rvBestSellerbook.visibility = View.VISIBLE
                        val bookList: ArrayList<BookResult> =
                            bestSellerbook.data?.results as ArrayList<BookResult>
                        setBooksAdapter(bookList)
                    }
                    Status.LOADING -> {
                        binding.progressCircular1.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.progressCircular1.visibility = View.GONE
                        binding.rvBestSellerbook.visibility = View.GONE
                        binding.tvErrormsg1.visibility = View.VISIBLE
                        binding.tvErrormsg1.text = bestSellerbook.message
                    }
                }
            })
    }

    private fun setBooksAdapter(list: ArrayList<BookResult>) {
        bookAdapter = BooksAdapter(list, object : OnListItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(list[position].amazon_product_url)
                startActivity(intent)
            }

        })
        binding.rvBestSellerbook.adapter = bookAdapter
        binding.rvBestSellerbook.setLayoutManager(LinearLayoutManager(this))
        binding.rvBestSellerbook.addItemDecoration(
            DividerItemDecoration(
                binding.rvBestSellerbook.context, LinearLayoutManager(
                    this
                ).orientation
            )
        )
    }

}