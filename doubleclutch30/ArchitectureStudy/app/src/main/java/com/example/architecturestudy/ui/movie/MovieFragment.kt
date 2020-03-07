package com.example.architecturestudy.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturestudy.Injection
import com.example.architecturestudy.R
import com.example.architecturestudy.data.model.MovieItem
import com.example.architecturestudy.databinding.FragmentMovieBinding

class MovieFragment : Fragment(), MovieContract.View {

    private lateinit var binding: FragmentMovieBinding

    private lateinit var movieAdapter: MovieAdapter

    private val presenter: MovieContract.Presenter by lazy {
        MoviePresenter(
            view = this,
            repository = context?.let { Injection.provideNaverSearchRepository(it) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie,
            container,
            false
        )

        binding.view = this
        binding.presenter = presenter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter = MovieAdapter()

        presenter.getLastData()

        binding.recycleview.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(
                DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            )
        }
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun updateUi(keyword: String, item: List<MovieItem>) {
        binding.searchEdit.setText(keyword)
        showResult(item)
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showEmpty(message: String) {
        Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showResult(item: List<MovieItem>) {
        movieAdapter.update(item)
    }
}