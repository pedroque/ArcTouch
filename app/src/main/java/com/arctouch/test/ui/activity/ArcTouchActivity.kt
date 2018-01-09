package com.arctouch.test.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.arctouch.test.di.Injectable
import dagger.android.AndroidInjection

abstract class ArcTouchActivity : AppCompatActivity(), Injectable {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setUp(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        onViewCreated(savedInstanceState)
        bind(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    internal open fun setUp(savedInstanceState: Bundle?) {

    }

    internal open fun bind(savedInstanceState: Bundle?) {

    }

    internal open fun onViewCreated(savedInstanceState: Bundle?) {

    }
}