package com.arctouch.test.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.arctouch.test.R
import com.arctouch.test.data.model.Config
import com.arctouch.test.data.model.Genre
import com.arctouch.test.data.model.Movie
import com.arctouch.test.databinding.ActivityMovieBinding
import com.arctouch.test.extensions.setTransparentStatusBarEnabled
import com.arctouch.test.vm.*
import kotlinx.android.synthetic.main.activity_movie.*
import javax.inject.Inject


class MovieActivity : ArcTouchActivity() {

    @Inject
    lateinit var configViewModelFactory: ConfigViewModelFactory

    @Inject
    lateinit var genresViewModelFactory: GenreViewModelFactory

    private lateinit var configViewModel: ConfigViewModel
    private lateinit var genreViewModel: GenresViewModel

    private lateinit var dataBinding: ActivityMovieBinding

    override fun setUp(savedInstanceState: Bundle?) {
        super.setUp(savedInstanceState)
        dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_movie, null, false)
        setContentView(dataBinding.root)
        configViewModel = ViewModelProviders.of(this, configViewModelFactory).get(ConfigViewModel::class.java)
        genreViewModel = ViewModelProviders.of(this, genresViewModelFactory).get(GenresViewModel::class.java)
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setTransparentStatusBarEnabled(window)
    }

    override fun bind(savedInstanceState: Bundle?) {
        super.bind(savedInstanceState)

        dataBinding.movie = intent.getParcelableExtra(MOVIE)

        configViewModel.config.observe(this, Observer<Config> {
            dataBinding.config = it
        })
        genreViewModel.genres.observe(this, Observer<Resource<List<Genre>>> {
            dataBinding.genres = it?.data
        })
        if (configViewModel.config.value == null) {
            configViewModel.fetchConfig()
        }
        if (genreViewModel.genres.value == null) {
            genreViewModel.fetchGenres()
        }
    }

    companion object {

        private val MOVIE = "movie"

        @JvmStatic
        fun getIntent(context: Context, movie: Movie): Intent {
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra(MOVIE, movie)
            return intent
        }
    }
}