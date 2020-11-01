package com.e.golgirview.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.androchef.happytimer.countdowntimer.CircularCountDownView
import com.androchef.happytimer.countdowntimer.HappyTimer
import com.e.golgirview.R
import com.e.golgirview.model.ItemModel
import com.e.golgirview.ui.DataState
import com.e.golgirview.ui.DataStateChangeListener
import com.e.golgirview.ui.displaySuccessDialog
import com.e.golgirview.ui.main.state.MainStateEvent
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.android.synthetic.main.fragment_question.view.*

class QuestionFragment : BaseMainFragment() {

    var answer : String = ""
    var counter: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.setItemData(ItemModel(0,"",""))
       initUi()
    }



    private fun initUi() {
        tv_question.text = ""
        tv_question.requestFocus()

        btn_give_up.setOnClickListener {
            showErrorDialog()
        }

        btn_send_que.setOnClickListener {
            validateAnswer()
        }

        subscribeObservers()
        triggerGetItemsEvent()
        setupTimer()
    }



    private fun setupTimer() {

        //Initialize Your Timer with seconds
        circularCountDownView.initTimer(60)

        //set OnTickListener for getting updates on time. [Optional]
        circularCountDownView.setOnTickListener(object : HappyTimer.OnTickListener {

            //OnTick
            override fun onTick(completedSeconds: Int, remainingSeconds: Int) {

            }

            //OnTimeUp
            override fun onTimeUp() {
//               validateAnswer()
//                navToSummaryFragment()
                showTimeUpDialog()
            }
        })

        circularCountDownView.timerType = HappyTimer.Type.COUNT_DOWN

        //Start Timer
        circularCountDownView.startTimer()
    }


    private fun validateAnswer(){
        val userAnswer = listOf(et_answer.text.toString().toLowerCase())
        Log.e(TAG, "validateAnswer: $userAnswer" )

        userAnswer.apply {
            filterNot { it.endsWith("es") }
            filterNot { it.endsWith("s") }
            filterNot { it.endsWith("ed") }
            filterNot { it.endsWith("ing") }
        }
        Log.e(TAG, "validateAnswer after endswith: $userAnswer" )

        userAnswer.let {
            it.filterNot { it == "for" }
            it.filterNot { it == "the" }
            it.filterNot { it == "a" }
            it.filterNot { it == "an" }
            it.filterNot { it == "and" }
            it.filterNot { it == "or" }
            it.filterNot { it == "nor" }
            it.filterNot { it == "but" }
            it.filterNot { it == "so" }
            it.filterNot { it == "are" }
            it.filterNot { it == "of" }
        }
        Log.e(TAG, "validateAnswer after filter not: $userAnswer" )


        andTheAnswerIs(userAnswer)
    }

    private fun andTheAnswerIs(userAnswer: List<String>) {
        circularCountDownView.pauseTimer()
        var temp:String = ""
        for (i in userAnswer){
            temp += " $i"
        }
        if (temp.trim().equals((viewModel.getItemData().answer.toLowerCase()).trim()) ){
            showSuccessDialog()
            Log.e(TAG, "andTheAnswerIs: ${viewModel.getItemData().answer.toLowerCase()} temp is $temp" )
        }else{
            showErrorDialog()
            Log.e(TAG, "andTheAnswerIs: ${viewModel.getItemData().answer.toLowerCase()} temp is $temp\" " )

        }

    }

    private fun showSuccessDialog(){
        MaterialDialog(requireContext())
            .show {
                title(R.string.text_success)
                message(text = " ${viewModel.getUserName()} that's right! well done:)")
                positiveButton(R.string.text_ok)
            }.positiveButton {
                triggerGetItemsEvent()
                tv_score.text = "${20 * (++counter)}"
                et_answer.setText("")
                it.dismiss()
                circularCountDownView.resumeTimer()
            }.cancelOnTouchOutside(false)
    }

    private fun showErrorDialog() {
        MaterialDialog(requireContext())
            .show {
                title(R.string.text_error)
                message(text = "${viewModel.getUserName()} not bad... try again!")
                positiveButton(R.string.text_ok)
            }.positiveButton {
                triggerGetItemsEvent()
                it.dismiss()
                et_answer.setText("")
                circularCountDownView.resumeTimer()
            }.cancelOnTouchOutside(false)

    }


    private fun showTimeUpDialog() {
        MaterialDialog(requireContext())
            .show {
                title(R.string.text_times_up)
                message(text = "${viewModel.getUserName()} times up.")
                positiveButton(R.string.text_ok)
            }.positiveButton {
                et_answer.setText("")
                navToSummaryFragment()
            }.cancelOnTouchOutside(false)

    }

    private fun navToSummaryFragment() {
//        if (findNavController().currentDestination?.equals(R.id.questionFragment)!!){
            findNavController().navigate(R.id.action_questionFragment_to_summaryFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //Stop Timer
        circularCountDownView.stopTimer()
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
                        it?.itemModel?.let {
                            viewModel.setItemData(it)

                        }
                    }
                }
            }

        })
        viewModel.viewState.observe(viewLifecycleOwner, Observer {viewstate->
            viewstate.itemModel?.let {item->
                tv_question.text = item.question
                Toast.makeText(context,"${item.answer}",Toast.LENGTH_SHORT).show()

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