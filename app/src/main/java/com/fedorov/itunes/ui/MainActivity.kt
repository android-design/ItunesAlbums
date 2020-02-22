package com.fedorov.itunes.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.fedorov.itunes.R
import com.fedorov.itunes.ui.fragment.SearchAlbumsFragment
import com.fedorov.itunes.ui.fragment.SearchAlbumsFragmentDirections
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity(),
    SearchAlbumsFragment.OnAlbumSelectedListener {

    private val navController by lazy { findNavController(R.id.host_fragment) }
    private val navHost by lazy { supportFragmentManager.findFragmentById(R.id.host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        initAppBar()
        initSearchView()
    }

    private fun initAppBar() {
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.search_albums) {
                search_view_appbar.visibility = View.VISIBLE
            } else {
                search_view_appbar.visibility = View.INVISIBLE
            }
        }
    }

    private fun initSearchView() {
        search_view_appbar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                navHost?.let { navFragment ->
                    navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                        (fragment as SearchAlbumsFragment?)?.getAlbums(query)
                    }
                }

                hideSoftKeyboard()
                search_view_appbar.clearFocus()

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
    }

    override fun openAlbumInfo(collectionId: Int) {
        val directions = SearchAlbumsFragmentDirections.navigateToAlbumInfo(collectionId)
        navController.navigate(directions)
    }

    fun hideSoftKeyboard() {
        currentFocus?.let {
            val inputMethodManager = getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                it.windowToken,
                0
            )
        }
    }
}