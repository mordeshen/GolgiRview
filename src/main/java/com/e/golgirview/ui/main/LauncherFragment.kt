package com.e.golgirview.ui.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androchef.happytimer.countdowntimer.HappyTimer
import com.e.golgirview.R
import com.e.golgirview.ui.DataStateChangeListener
import com.e.golgirview.ui.main.state.MainStateEvent
import kotlinx.android.synthetic.main.fragment_launcher.*
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LauncherFragment : BaseMainFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launcher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        initUi()

    }


    private fun setupTimer() {

        //Initialize Your Timer with seconds
        launcherCircularCountDownView.initTimer(2)

        //set OnTickListener for getting updates on time. [Optional]
        launcherCircularCountDownView.setOnTickListener(object : HappyTimer.OnTickListener {

            //OnTick
            override fun onTick(completedSeconds: Int, remainingSeconds: Int) {

            }

            //OnTimeUp
            override fun onTimeUp() {
                findNavController().navigate(R.id.action_launcherFragment_to_loginFragment)
            }
        })

        launcherCircularCountDownView.startTimer()

    }

    override fun onResume() {
        super.onResume()
        initUi()
    }

    private fun initUi() {
        setupTimer()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        launcherCircularCountDownView.stopTimer()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateHandler = context as DataStateChangeListener
        } catch (e: ClassCastException) {
            println("$context must implement DataStateListener")
        }

    }

}