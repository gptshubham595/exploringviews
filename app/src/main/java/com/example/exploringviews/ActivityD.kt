package com.example.exploringviews

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.exploringviews.BaseViewModel.Companion.instanceCounterD
import com.example.exploringviews.databinding.ActivityDBinding

class ActivityD : BaseActivity<ActivityDBinding, BaseViewModel>() {
    @Suppress("ktlint:standard:property-naming")
    private val TAG = ActivityD::class.java.simpleName

    private var currentInstanceValue = 0

    init {
        instanceCounterD++
        currentInstanceValue = instanceCounterD
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
//        goToActivityB()

        binding.textViewInstanceValue.append(",Current instance: $currentInstanceValue")

        initListener()
    }

    private fun initListener() {
        binding.buttonStartActivityA.setOnClickListener {
            goToActivity<ActivityA>()
        }
        binding.buttonStartActivityB.setOnClickListener {
            goToActivity<ActivityB>()
        }
        binding.buttonStartActivityC.setOnClickListener {
            goToActivity<ActivityC>()
        }
        binding.buttonStartActivityBWithFragment.setOnClickListener {
            goToActivity<ActivityBWithFragment>()
        }
        binding.buttonStartActivityD.setOnClickListener {
            goToActivity<ActivityD>()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_d
    }

    override fun getViewModel(): Class<BaseViewModel> {
        return BaseViewModel::class.java
    }

    private fun goToActivity(activity: Class<*>) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    // add logs for each lifecycle method

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        Log.i(TAG, "Instances: $currentInstanceValue")
        binding.textViewTaskInfo.text = getAppTaskState()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }
}
