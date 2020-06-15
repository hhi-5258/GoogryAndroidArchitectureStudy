package com.example.architecture.activity.search

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.architecture.R
import com.example.architecture.activity.search.adapter.MovieAdapter
import com.example.architecture.data.repository.NaverRepositoryImpl
import com.example.architecture.databinding.ActivitySearchBinding
import com.example.architecture.provider.ResourceProviderImpl
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    @Suppress("UNCHECKED_CAST")
    private val vm: SearchViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SearchViewModel(
                    NaverRepositoryImpl(this@SearchActivity)
                    , ResourceProviderImpl(this@SearchActivity.applicationContext)
                ) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        binding.vm = vm
        binding.lifecycleOwner = this

        setupRecyclerview()
        setupViewModelObserve()
    }

    private fun setupRecyclerview() {
        rv_search_movieList.adapter = MovieAdapter()
    }

    private fun setupViewModelObserve() {
        vm.toastMessage.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })
    }
}
