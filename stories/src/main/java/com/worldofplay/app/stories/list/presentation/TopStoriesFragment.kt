package com.worldofplay.app.stories.list.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.worldofplay.app.stories.R
import com.worldofplay.app.stories.details.domain.StoriesResponse
import com.worldofplay.app.stories.details.viewmodel.StoriesViewModel
import com.worldofplay.app.stories.list.presentation.adapter.TopStoriesAdapter
import com.worldofplay.app.stories.list.presentation.callbacks.ItemListener
import com.worldofplay.app.stories.list.presentation.callbacks.PaginationListener
import com.worldofplay.app.stories.list.presentation.callbacks.PaginationListener.Companion.PAGE_SIZE
import com.worldofplay.app.stories.list.viewmodel.TopStoriesViewModel
import com.worldofplay.core.di.Injectable
import com.worldofplay.core.di.injectViewModel
import kotlinx.android.synthetic.main.topstories_fragment.*
import javax.inject.Inject


class TopStoriesFragment : Fragment(), Injectable, ItemListener {

    lateinit var topStoriesViewModel: TopStoriesViewModel
    lateinit var storiesViewModel: StoriesViewModel
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
        storiesViewModel = injectViewModel(viewModelFactory)

        initUI()

        observeTopStoriesvalue()

        observeStoriesvalue()

        observeProgress()

        observeError(view)

        if (topStoriesViewModel.itemCount == 0) {
            topStoriesViewModel.getTopStories(viewLifecycleOwner)
        }else{
            topStoriesViewModel.successData.value?.let { updateStories(it) }
        }
    }

    private fun initUI() {
        activity?.let {
            val layoutManager = LinearLayoutManager(it)
            val dividerItemDecoration = DividerItemDecoration(
                topStoriesList.getContext(),
                layoutManager.orientation
            )
            topStoriesList.addItemDecoration(dividerItemDecoration)
            topStoriesList.layoutManager = layoutManager
            topStoriesList.run {
                topStoriesList.layoutManager = layoutManager
                topStoriesList.addOnScrollListener(object : PaginationListener(layoutManager) {
                    override fun loadMoreItems() {
                        topStoriesViewModel.isLoading = true
                        topStoriesViewModel.currentPage++
                        loadNextPage()
                    }

                    override fun isLastPage(): Boolean {
                        return topStoriesViewModel.isLastPage
                    }

                    override fun isLoading(): Boolean {
                        return topStoriesViewModel.isLoading
                    }
                })
            }
            topStoriesAdapter = TopStoriesAdapter(ArrayList<StoriesResponse>(), it, this)
            topStoriesAdapter?.let {
                topStoriesList.adapter = it
            }
        }
    }

    fun loadNextPage() {
        topStoriesViewModel.getNextPageList()?.let {
            updateStories(it)
        }
    }

    private fun setTopStoriesData(topStories: ArrayList<String>) {
        updateStories(topStories.subList(0, PAGE_SIZE))
    }

    private fun updateStories(topStories: MutableList<String>) {
        storiesViewModel.getAllStories(viewLifecycleOwner,topStories.subList(0, PAGE_SIZE))
    }

    private fun observeError(view: View) {
        topStoriesViewModel.errorMessageData.observe(viewLifecycleOwner, Observer {
            Snackbar.make(view, getString(R.string.somehing_wentwrong), Snackbar.LENGTH_SHORT)
                .show()
        })
        storiesViewModel.errorMessageData.observe(viewLifecycleOwner, Observer {
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
                    loadMoreProgressBar.visibility = View.GONE
                }
                false -> {
                    topStoriesList.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    loadMoreProgressBar.visibility = View.GONE
                }
            }
        })
        storiesViewModel.loadingData.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    if (topStoriesAdapter.items.size == 0) {
                        topStoriesList.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    } else {
                        loadMoreProgressBar.visibility = View.VISIBLE
                    }
                }
                false -> {
                    if (topStoriesAdapter.items.size == 0) {
                        topStoriesList.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    } else {
                        loadMoreProgressBar.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun observeTopStoriesvalue() {
        topStoriesViewModel.successData.observe(viewLifecycleOwner, Observer {
            Log.i("Anil", " observeTopStoriesvalue :" + it.toString())
            setTopStoriesData(it)
        })
    }

    private fun observeStoriesvalue() {
        storiesViewModel.successData.observe(viewLifecycleOwner, Observer {
            Log.i("Anil", "observeStoriesvalue :" + it.toString())
            setDataUI(it)
        })
    }

    private fun setDataUI(it: StoriesResponse) {
        topStoriesViewModel.isLoading = false
        topStoriesAdapter.items.add(it)
        topStoriesAdapter.notifyDataSetChanged()
    }

    override fun onClicked(storiesResponse: StoriesResponse) {
        val bundle = Bundle().apply {
            putString("url", storiesResponse.url)
        }
        findNavController().navigate(R.id.navigation_stories, bundle)
    }

}