package com.fedorov.itunes.ui.base

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.progressbar_main.*
import com.fedorov.itunes.R
import com.fedorov.itunes.ui.ItunesData
import com.fedorov.itunes.ui.Status

abstract class BaseActivity<T> : AppCompatActivity() {

    abstract val layoutRes: Int
    abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutRes)
        initAppBar()
        observeNewEvents()
    }

    open fun initAppBar() {
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    private fun observeNewEvents() {
        viewModel.eventData.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> viewOnLoading()
                Status.SUCCESS -> viewOnSuccess(it.data)
                Status.ERROR -> viewOnError(it.error)
            }
        })
    }

    open fun viewOnLoading() {
        showProgressBar()
    }

    open fun viewOnSuccess(data: List<ItunesData>?) {
        hideProgressBar()
        data?.let {
            if (it.isEmpty()) {
                showToast(getString(R.string.msg_nothing_found))
            }
        }
    }

    open fun viewOnError(error: Exception?) {
        hideProgressBar()
        error?.let {
            it.message?.let { errorMessage -> showToast(errorMessage) }
        }
    }

    private fun showProgressBar() {
        progressBarGroup?.let {
            it.visibility = View.VISIBLE
        }
    }

    private fun hideProgressBar() {
        progressBarGroup?.let {
            it.visibility = View.INVISIBLE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    fun hideSoftKeyboard() {
        if (currentFocus == null) return
        val inputMethodManager = getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            currentFocus!!.windowToken,
            0
        )
    }
}