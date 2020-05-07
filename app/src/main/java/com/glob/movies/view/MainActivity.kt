package com.glob.movies.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.glob.movies.R

class MainActivity : AppCompatActivity(), MainMenuFragment.OnMovieActionListener, View.OnClickListener,
    DetailFragment.OnMovieDetailListener {

    private val INITIAL_PAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainMenuFragment = MainMenuFragment.newInstance(INITIAL_PAGE)
        showFragment(mainMenuFragment, false)
    }

    override fun onMovieSelected(id: String) {
        val detailFragment = DetailFragment.newInstance(id)
        showFragment(detailFragment, true)
    }

    override fun onSectionSelected(title: String) {
        actionBar?.title = title
    }

    private fun showFragment(fragment: Fragment, addToBackstack: Boolean) {
        val fragmentTransaction = supportFragmentManager
            .beginTransaction()
            .add(R.id.mainContainer, fragment, fragment.tag)
            .setCustomAnimations(R.anim.slide_up, R.anim.slide_down)
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(fragment.tag)
        }
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        Log.d("TAG", "size of backstack ${supportFragmentManager.backStackEntryCount}")
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        }
        defaultToolbar()
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        goHomeBtn()
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            goHomeBtn()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goHomeBtn() {
        onBackPressed()
        defaultToolbar()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            android.R.id.home -> {
                onBackPressed()
                defaultToolbar()
            }
        }
    }

    private fun defaultToolbar() {
        supportActionBar?.setTitle("Movies")
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(false)
    }

    override fun onMovieShowed(title: String) {
        actionBar?.setTitle(title)
    }

}
