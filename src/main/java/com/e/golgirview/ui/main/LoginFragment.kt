package com.e.golgirview.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.e.golgirview.R
import com.e.golgirview.ui.DataStateChangeListener
import com.e.golgirview.ui.main.state.LoginFields
import com.e.golgirview.ui.main.state.MainStateEvent
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseMainFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        initUi()

        btn_login.setOnClickListener {
            login()

        }
    }

    private fun navToQuestion() {
        findNavController().navigate(R.id.action_loginFragment_to_questionFragment)
    }

    override fun onResume() {
        super.onResume()
        initUi()
    }

    private fun initUi() {
        subscribeObservers()
        triggerGetItemsEvent()
    }




    fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.loginFields?.let { loginFields ->
                loginFields.login_name?.let { et_user_name.setText(it) }
            }
        })
    }

    private fun login() {
        if(et_user_name.text.isNotEmpty()){
            setData()
            navToQuestion()
        }else{
            et_user_name.error = "please enter your name"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        setData()
    }

    private fun setData(){
        viewModel.setLoginFields(
            LoginFields(
                et_user_name.text.toString()
            )
        )
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