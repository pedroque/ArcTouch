package com.arctouch.test.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.Toast
import com.arctouch.test.R
import com.arctouch.test.data.model.Config
import com.arctouch.test.data.model.Genre
import com.arctouch.test.databinding.ActivityMoviesBinding
import com.arctouch.test.vm.*
import javax.inject.Inject


class MoviesActivity : ArcTouchActivity() {

    @Inject
    lateinit var configViewModelFactory: ConfigViewModelFactory

    @Inject
    lateinit var genresViewModelFactory: GenreViewModelFactory

    private lateinit var configViewModel: ConfigViewModel
    private lateinit var genreViewModel: GenresViewModel

    private lateinit var dataBinding: ActivityMoviesBinding

    override fun setUp(savedInstanceState: Bundle?) {
        super.setUp(savedInstanceState)
        dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_movies, null, false)
        setContentView(dataBinding.root)
        configViewModel = ViewModelProviders.of(this, configViewModelFactory).get(ConfigViewModel::class.java)
        genreViewModel = ViewModelProviders.of(this, genresViewModelFactory).get(GenresViewModel::class.java)
    }

    override fun bind(savedInstanceState: Bundle?) {
        super.bind(savedInstanceState)
        configViewModel.config.observe(this, Observer<Config> {
            it?.let { setMoviesPicConfig(it) }
        })
        genreViewModel.genres.observe(this, Observer<Resource<List<Genre>>> {
            dataBinding.resource = it
            if (it?.status == Resource.Status.SUCCESS) {
                setMoviesGenres(it.data!!)
            }
        })
        if (configViewModel.config.value == null) {
            configViewModel.fetchConfig()
        }
        if (genreViewModel.genres.value == null) {
            genreViewModel.fetchGenres()
        }
    }

    private fun setMoviesGenres(genres: List<Genre>) {
        Toast.makeText(this, "we have %d genres".format(genres.size), Toast.LENGTH_SHORT).show()
    }

    private fun setMoviesPicConfig(config: Config) {
        Toast.makeText(this, "config url " + config.baseUrl, Toast.LENGTH_SHORT).show()
    }
}