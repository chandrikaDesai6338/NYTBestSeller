package com.servify.nytimesbestseller.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.servify.nytimesbestseller.BestSellerViewModel
import com.servify.nytimesbestseller.ViewModelFactory
import com.servify.nytimesbestseller.databinding.ActivityBestsellerListBinding
import com.servify.nytimesbestseller.model.Result
import com.servify.nytimesbestseller.model.Status
import com.servify.nytimesbestseller.network.ApiHelper
import com.servify.nytimesbestseller.network.Rest

class BestSellerActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBestsellerListBinding
    private lateinit var viewModel : BestSellerViewModel
    private lateinit var bestSellerWeeklyAdapter: BestSellerAdapter
    private lateinit var bestSellerMonthlyAdapter: BestSellerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBestsellerListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        setUpObserver()
    }

    private fun setUpViewModel(){
        viewModel = ViewModelProviders.of(this, ViewModelFactory(ApiHelper(Rest.apiService))).get(BestSellerViewModel::class.java)
    }

    private fun setUpObserver(){
        viewModel.getBestSellerList().observe(this, Observer { bestSeller ->
            when(bestSeller.status){
                Status.SUCCESS -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.tvErrormsg.visibility = View.GONE
                    binding.rvBestSellerWeekly.visibility = View.VISIBLE
                    binding.rvBestSellerMonthly.visibility = View.VISIBLE
                    val list : ArrayList<Result> = bestSeller.data?.results as ArrayList<Result>
                    val groupedMap: HashMap<String, ArrayList<Result>> = viewModel.groupByUpdated(viewModel.sortByDate(list))
                    val weeklyList: ArrayList<Result>? = groupedMap.get("WEEKLY")
                    val monthlyList: ArrayList<Result>? = groupedMap.get("MONTHLY")
                    setBestSellerMonthlyAdapter(monthlyList)
                    setBestSellerWeeklyAdapter(weeklyList)
                }
                Status.LOADING -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.rvBestSellerMonthly.visibility = View.GONE
                    binding.rvBestSellerWeekly.visibility = View.GONE
                    binding.tvMonthly.visibility = View.GONE
                    binding.tvWeekly.visibility = View.GONE
                    binding.tvErrormsg.visibility = View.VISIBLE
                    binding.tvErrormsg.text = bestSeller.message
                }
            }
        })
    }


    private fun setBestSellerMonthlyAdapter(list: ArrayList<Result>?) {
        bestSellerMonthlyAdapter = BestSellerAdapter(list, object : OnListItemClickListener{
            override fun onItemClick(position: Int) {
                list?.get(position)?.list_name_encoded?.let { getBookActivity(it) }
            }
        })
        binding.rvBestSellerMonthly.adapter = bestSellerMonthlyAdapter
        binding.rvBestSellerMonthly.setLayoutManager(LinearLayoutManager(this))
        binding.rvBestSellerMonthly.addItemDecoration(DividerItemDecoration(binding.rvBestSellerMonthly.context, LinearLayoutManager(this).orientation))
    }

    private fun setBestSellerWeeklyAdapter(list: ArrayList<Result>?) {
        bestSellerWeeklyAdapter = BestSellerAdapter(list, object : OnListItemClickListener{
            override fun onItemClick(position: Int) {
                list?.get(position)?.list_name_encoded?.let { getBookActivity(it) }
            }
        })
        binding.rvBestSellerWeekly.adapter = bestSellerWeeklyAdapter
        binding.rvBestSellerWeekly.setLayoutManager(LinearLayoutManager(this))
        binding.rvBestSellerWeekly.addItemDecoration(DividerItemDecoration(binding.rvBestSellerWeekly.context, LinearLayoutManager(this).orientation))
    }

    fun getBookActivity(listname :String){
        val intent = Intent(this, BooksActivity::class.java).apply {
            putExtra("list_name_encoded", listname)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
//        binding = null
    }
}