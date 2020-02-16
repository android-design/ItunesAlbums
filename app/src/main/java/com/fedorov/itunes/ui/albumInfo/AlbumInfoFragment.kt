package com.fedorov.itunes.ui.albumInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fedorov.itunes.R
import com.fedorov.itunes.adapters.ItunesAdapter
import com.fedorov.itunes.ui.ItunesData
import com.fedorov.itunes.ui.Status
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_album_info.*
import kotlinx.android.synthetic.main.progressbar_main.*
import javax.inject.Inject

class AlbumInfoFragment : DaggerFragment() {

    private val args: AlbumInfoFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AlbumInfoViewModel> { viewModelFactory }

    private val mAdapter = ItunesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeNewEvents()

        viewModel.getTracks(args.collectionId)
    }

    private fun initRecyclerView() {
        list_recycler_view.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }
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

    private fun viewOnLoading() {
        showProgressBar()
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

    private fun viewOnError(error: Exception?) {
        hideProgressBar()

        error?.let {
            it.message?.let { errorMessage ->
                Toast.makeText(
                    context,
                    errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Return to previous screen.
        requireActivity().onBackPressed()
    }

    private fun viewOnSuccess(data: List<ItunesData>?) {
        hideProgressBar()
        data?.let {
            mAdapter.setData(it)
        }
    }
}