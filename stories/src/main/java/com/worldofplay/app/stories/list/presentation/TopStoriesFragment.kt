package com.worldofplay.app.stories.list.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.worldofplay.app.stories.R
import com.worldofplay.app.stories.list.presentation.adapter.TopStoriesAdapter
import com.worldofplay.app.stories.list.viewmodel.TopStoriesViewModel
import com.worldofplay.core.di.Injectable
import com.worldofplay.core.di.injectViewModel
import kotlinx.android.synthetic.main.topstories_fragment.*
import javax.inject.Inject


class TopStoriesFragment : Fragment(), Injectable {

    lateinit var topStoriesViewModel: TopStoriesViewModel
    lateinit var topStoriesAdapter: TopStoriesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.topstories_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topStoriesViewModel = injectViewModel(viewModelFactory)

        initUI()

        observeTopStoriesvalue()

        observeProgress()

        observeError(view)

        topStoriesViewModel.getTopStories()
    }

    private fun initUI() {
        activity?.let {
            topStoriesAdapter = TopStoriesAdapter(ArrayList<String>(), it)
            topStoriesAdapter?.let {
                topStoriesList.adapter = it
            }
        }
    }

    private fun setData(topStories: ArrayList<String>) {
        topStoriesAdapter.items = topStories
        topStoriesAdapter.notifyDataSetChanged()
    }

    private fun observeError(view: View) {
        topStoriesViewModel.errorMessageData.observe(viewLifecycleOwner, Observer {
            Snackbar.make(view, getString(R.string.somehing_wentwrong), Snackbar.LENGTH_SHORT)
                .show()
        })
    }

    private fun observeProgress() {
        topStoriesViewModel.loadingData.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    topStoriesList.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                false -> {
                    topStoriesList.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun observeTopStoriesvalue() {
        topStoriesViewModel.successData.observe(viewLifecycleOwner, Observer {
            Log.i("Anil", " Top Stories:" + it.toString())
            setData(it)
        })
    }
}