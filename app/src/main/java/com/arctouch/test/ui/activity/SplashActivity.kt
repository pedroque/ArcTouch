package com.arctouch.test.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.widget.Toast
import com.arctouch.test.R
import com.arctouch.test.extensions.friendlyMessage
import com.arctouch.test.vm.ConfigViewModel
import com.arctouch.test.vm.ConfigViewModelFactory
import javax.inject.Inject


class SplashActivity : ArcTouchActivity() {

    @Inject
    lateinit var configViewModelFactory: ConfigViewModelFactory

    private lateinit var configViewModel: ConfigViewModel

    override fun setUp(savedInstanceState: Bundle?) {
        super.setUp(savedInstanceState)
        setContentView(R.layout.activity_splash)
        configViewModel = ViewModelProviders.of(this, configViewModelFactory).get(ConfigViewModel::class.java)
    }

    override fun bind(savedInstanceState: Bundle?) {
        super.bind(savedInstanceState)
        configViewModel.getConfig().subscribe({
            navigateToMovies()
        }, {
            setError(it.friendlyMessage)
        })
    }

    private fun setError(@StringRes friendlyMessage: Int) {
        Toast.makeText(this, friendlyMessage, Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun navigateToMovies() {
        Toast.makeText(this, "navigate to movies", Toast.LENGTH_SHORT).show()
        finish()
    }
}