package com.e.golgirview.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.e.golgirview.R
import com.e.golgirview.ui.DataStateChangeListener
import com.e.golgirview.ui.main.state.MainStateEvent
import kotlinx.android.synthetic.main.fragment_summary.*

class SummaryFragment : BaseMainFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

       initUi()
    }

    private fun initUi() {
        btn_game_over.setOnClickListener {
            activity?.finish()
        }
        btn_new_game.setOnClickListener {
            findNavController().navigate(R.id.action_summaryFragment_to_questionFragment)
        }

        subscribeObservers()
        triggerGetItemsEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            Log.e(TAG, "subscribeObservers: $dataState")
            // Handle Loading and Message
            dataStateHandler.onDataStateChange(dataState)

            // handle Data<T>
            dataState?.data?.let { data ->
                data?.data.let { event ->
                    event?.getContentIfNotHandled().let {
                        it?.loginFields?.let {
//                            it.setItemData(it)
                        }
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewstate->
            viewstate?.let {
                it.loginFields?.let {
                    tv_summary.text = "${it.login_name} well done! \n would you lik to play?"
                }
            }
        })
    }


    fun triggerGetItemsEvent() {
        viewModel.setStateEvent(MainStateEvent.GetItemEvent())
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