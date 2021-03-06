package com.vynokurov.mainMovie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.vynokurov.movie.data.MovieEntity
import com.vynokurov.movie.ui.FragmentMoviesList
import com.vynokurov.movieDetail.FragmentMovieDetails

class MainMovieActivity : AppCompatActivity() {
    private val detailFragment by lazy { FragmentMovieDetails().apply { setClickListener(backPressedCustom) }}
    private val rootFragment by lazy {  FragmentMoviesList().apply { setClickListener(transactionListener) }}

    private val transactionListener = object : FragmentMoviesList.TransactionsFragmentClicks{
        override fun addFragment(movie: MovieEntity) {
            replaceFragment(movie, detailFragment, FRAGMENT_DETAIL)
        }
    }

    private val backPressedCustom = object :  FragmentMovieDetails.BackPressedClick {
        override fun backClick() {
            onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.general_movie_layout)
        rootFragment.apply {
            supportFragmentManager.beginTransaction()
                .add(R.id.mainContainer, rootFragment, FRAGMENT_LIST)
                .commit()
        }
    }

    private fun replaceFragment(movie: MovieEntity, fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.mainContainer, fragment, tag)
                addToBackStack(tag)
                commit()
            }
    }

    companion object {
        const val FRAGMENT_LIST = "fragment_list"
        const val FRAGMENT_DETAIL = "fragment_detail"

        @JvmStatic
        fun newIntent(context: Context): Intent =
            Intent(context, MainMovieActivity::class.java)
    }
}