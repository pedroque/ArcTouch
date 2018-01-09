package com.arctouch.test.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.arctouch.test.R
import com.arctouch.test.data.model.Config
import com.arctouch.test.data.model.Genre
import com.arctouch.test.data.model.Movie
import com.arctouch.test.databinding.ActivityMoviesBinding
import com.arctouch.test.ui.adapter.MoviesAdapter
import com.arctouch.test.ui.commons.EndlessScrollListener
import com.arctouch.test.vm.*
import kotlinx.android.synthetic.main.activity_movies.*
import javax.inject.Inject


class MoviesActivity : ArcTouchActivity() {

    @Inject
    lateinit var configViewModelFactory: ConfigViewModelFactory

    @Inject
    lateinit var genresViewModelFactory: GenreViewModelFactory

    @Inject
    lateinit var moviesViewModelFactory: MoviesViewModelFactory

    private lateinit var configViewModel: ConfigViewModel
    private lateinit var genreViewModel: GenresViewModel
    private lateinit var moviesViewModel: MoviesViewModel

    private lateinit var dataBinding: ActivityMoviesBinding

    private var adapter: MoviesAdapter? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_movies, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                navigateToSearch()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setUp(savedInstanceState: Bundle?) {
        super.setUp(savedInstanceState)
        dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_movies, null, false)
        setContentView(dataBinding.root)
        configViewModel = ViewModelProviders.of(this, configViewModelFactory).get(ConfigViewModel::class.java)
        genreViewModel = ViewModelProviders.of(this, genresViewModelFactory).get(GenresViewModel::class.java)
        moviesViewModel = ViewModelProviders.of(this, moviesViewModelFactory).get(MoviesViewModel::class.java)
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)
        setSupportActionBar(toolbar)
        movies_recycler.setHasFixedSize(true)
        movies_recycler.layoutManager = LinearLayoutManager(this)
        adapter = MoviesAdapter()
        adapter?.clickSubject?.subscribe {
            moviesViewModel.movies.value?.data?.movies?.get(it)?.let {
                navigateToMovie(it)
            }
        }
        adapter?.replace(mutableListOf<Movie>())
        movies_recycler.adapter = adapter
        val scrollListener = object : EndlessScrollListener() {
            override fun onLoadMore() {
                val currentPage = moviesViewModel.movies.value?.data?.page ?: 1
                val totalPage = moviesViewModel.movies.value?.data?.totalPages ?: 1
                if (currentPage < totalPage) {
                    adapter?.loading = true
                    moviesViewModel.nextPage()
                }
            }
        }
        movies_recycler.addOnScrollListener(scrollListener)
    }

    override fun bind(savedInstanceState: Bundle?) {
        super.bind(savedInstanceState)
        configViewModel.config.observe(this, Observer<Config> {
            it?.let { setMoviesPicConfig(it) }
        })
        genreViewModel.genres.observe(this, Observer<Resource<List<Genre>>> {
            if (it?.status == Resource.Status.SUCCESS) {
                setMoviesGenres(it.data!!)
            }
        })
        moviesViewModel.movies.observe(this, Observer<MoviesResource> {
            dataBinding.resource = it
            adapter?.loading = it?.status == Resource.Status.LOADING
            if (it?.status == Resource.Status.SUCCESS) {
                if (it.isEmpty) setEmptyMovies() else setMovies(it.data!!.movies)
            } else if (it?.status == Resource.Status.ERROR) {
                if (!it.isEmpty) {
                    setNextPageError(it.message!!)
                }
            }
        })
        if (configViewModel.config.value == null) {
            configViewModel.fetchConfig()
        }
        if (genreViewModel.genres.value == null) {
            genreViewModel.fetchGenres()
        }
        if (moviesViewModel.movies.value == null) {
            moviesViewModel.firstPage()
        }
    }

    private fun navigateToMovie(movie: Movie) {
        startActivity(MovieActivity.getIntent(this, movie))
    }

    private fun navigateToSearch() {
        startActivity(SearchMovieActivity.getIntent(this))
    }

    private fun setNextPageError(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setMovies(movies: MutableList<Movie>) {
        adapter?.replace(movies)
    }

    private fun setEmptyMovies() {
        error_text.setText(R.string.empty_movies)
    }

    private fun setMoviesGenres(genres: List<Genre>) {
        adapter?.genres = genres
    }

    private fun setMoviesPicConfig(config: Config) {
        adapter?.config = config
    }
}