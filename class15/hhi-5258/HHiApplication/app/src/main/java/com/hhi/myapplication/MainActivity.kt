package com.hhi.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hhi.myapplication.data.repository.RepositoryDataSourceImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val recyclerAdapter = RecyclerAdapter()
    private val repositoryDataSourceImpl = RepositoryDataSourceImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        main_recyclerview.setHasFixedSize(false)
        main_recyclerview.adapter = recyclerAdapter
        main_btn_search.setOnClickListener {
            val searchText = main_edit_search.text
            if (searchText.isNotEmpty()) {
                searchMovie(searchText.toString())
            }
        }
    }

    private fun searchMovie(query: String) {
        repositoryDataSourceImpl.searchMovies(query,
            { recyclerAdapter.setMovieList(it.items) },
            { Log.e("search_failed", it.toString()); }
        )
    }
}