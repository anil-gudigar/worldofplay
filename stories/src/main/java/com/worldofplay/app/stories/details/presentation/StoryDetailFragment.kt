package com.worldofplay.app.stories.details.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.worldofplay.app.stories.R
import kotlinx.android.synthetic.main.fragment_story_detail.*

private const val ARG_PARAM = "url"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StoryDetailFragment : Fragment() {
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_PARAM)
        }
    }

    override fun onResume() {
        super.onResume()
        val activity = activity as AppCompatActivity?
        setHasOptionsMenu(true)
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data.text = url
        try {
            val webView = webview
            webView.loadUrl(url)
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(web: WebView, url: String) {
                    web.loadUrl("javascript:(function(){ document.body.style.paddingBottom = '100px'})();")
                    progressBar?.let {
                        progressBar.visibility = View.GONE
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, getString(R.string.somehing_wentwrong), Toast.LENGTH_SHORT).show()
            progressBar?.let {
                progressBar.visibility = View.GONE
            }
        }
    }
}
