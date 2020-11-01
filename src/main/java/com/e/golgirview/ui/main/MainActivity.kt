package com.e.golgirview.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.RequestManager
import com.e.golgirview.R
import com.e.golgirview.ui.DataStateChangeListener
import com.e.tbreview.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity  : BaseActivity(), DataStateChangeListener {

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showMainFragment()
    }

    fun showMainFragment() {
        if (supportFragmentManager.fragments.size == 0) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment_container,
                    LauncherFragment(),
                    "LauncherFragment"
                )
                .commit()
        }
    }


    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showProgressBar(isVisible: Boolean) {
        if (isVisible) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.INVISIBLE
        }
    }


    override fun expandAppBar() {
    }

    override fun displayProgressBar(boolean: Boolean) {
        if (boolean) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.INVISIBLE

        }
    }
}