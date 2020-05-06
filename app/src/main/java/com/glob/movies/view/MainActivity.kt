package com.glob.movies.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.glob.movies.R

class MainActivity : AppCompatActivity(), MainMenuFragment.OnMovieActionListener {

    private val INITIAL_PAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainMenuFragment = MainMenuFragment.newInstance(INITIAL_PAGE)
        showFragment(mainMenuFragment)
    }

    override fun onMovieSelected(id: String) {
        val detailFragment = DetailFragment.newInstance(id)
        showFragment(detailFragment)
    }

    override fun onSectionSelected(title: String) {
        actionBar?.title = title
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(R.id.mainContainer, fragment, fragment.tag)
            .commit()
    }

}
