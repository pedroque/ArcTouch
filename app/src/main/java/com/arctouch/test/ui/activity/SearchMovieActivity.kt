package com.arctouch.test.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.arctouch.test.R
import com.arctouch.test.data.model.Config
import com.arctouch.test.data.model.Genre
import com.arctouch.test.data.model.Movie
import com.arctouch.test.databinding.ActivitySearchMovieBinding
import com.arctouch.test.ui.adapter.MoviesAdapter
import com.arctouch.test.ui.commons.AutoClearedValue
import com.arctouch.test.ui.commons.EndlessScrollListener
import com.arctouch.test.vm.QueryViewModel
import com.arctouch.test.vm.*
import kotlinx.android.synthetic.main.activity_search_movie.*
import javax.inject.Inject


class SearchMovieActivity : ArcTouchActivity() {

    @Inject
    lateinit var configViewModelFactory: ConfigViewModelFactory

    @Inject
    lateinit var genresViewModelFactory: GenreViewModelFactory

    @Inject
    lateinit var moviesViewModelFactory: MoviesViewModelFactory

    private lateinit var configViewModel: ConfigViewModel
    private lateinit var genreViewModel: GenresViewModel
    private lateinit var moviesViewModel: SearchMoviesViewModel
    private lateinit var queryViewModel: AutoClearedValue<QueryViewModel>

    private lateinit var dataBinding: ActivitySearchMovieBinding

    private var adapter: MoviesAdapter? = null
    private var endlessScrollListener: EndlessScrollListener? = null

    override fun setUp(savedInstanceState: Bundle?) {
        super.setUp(savedInstanceState)
        dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_search_movie, null, false)
        setContentView(dataBinding.root)
        configViewModel = ViewModelProviders.of(this, configViewModelFactory).get(ConfigViewModel::class.java)
        genreViewModel = ViewModelProviders.of(this, genresViewModelFactory).get(GenresViewModel::class.java)
        moviesViewModel = ViewModelProviders.of(this, moviesViewModelFactory).get(SearchMoviesViewModel::class.java)
        queryViewModel = AutoClearedValue(this, QueryViewModel(moviesViewModel.query))
        dataBinding.query = queryViewModel.value
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setUpRecycler()
    }

    private fun setUpRecycler() {
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
        endlessScrollListener = object : EndlessScrollListener() {
            override fun onLoadMore() {
                val currentPage = moviesViewModel.movies.value?.data?.page ?: 1
                val totalPage = moviesViewModel.movies.value?.data?.totalPages ?: 1
                if (currentPage < totalPage) {
                    adapter?.loading = true
                    moviesViewModel.nextPage()
                }
            }
        }
        movies_recycler.addOnScrollListener(endlessScrollListener)
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
                when {
                    it.data == null -> setEmptySearch()
                    it.isEmpty -> setEmptyMovies()
                    else -> setMovies(it.data.movies)
                }
            } else if (it?.status == Resource.Status.ERROR) {
                if (!it.isEmpty) {
                    setNextPageError(it.message!!)
                }
            }
        })
        queryViewModel.value?.data?.observe(this, Observer<String> {
            moviesViewModel.query = it!!
            endlessScrollListener?.reset()
        })
        if (configViewModel.config.value == null) {
            configViewModel.fetchConfig()
        }
        if (genreViewModel.genres.value == null) {
            genreViewModel.fetchGenres()
        }
    }

    private fun setEmptySearch() {
        error_text.setText(R.string.search_movie_content)
    }

    private fun navigateToMovie(movie: Movie) {
        startActivity(MovieActivity.getIntent(this, movie))
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

    companion object {
        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, SearchMovieActivity::class.java)
        }
    }
}