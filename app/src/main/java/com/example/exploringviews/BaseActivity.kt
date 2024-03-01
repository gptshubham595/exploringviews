package com.example.exploringviews

import android.app.ActivityManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {
    lateinit var binding: B
    lateinit var viewModel: VM

    protected var activityManager: ActivityManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application),
            )[getViewModel()]

        if (activityManager == null) {
            activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager?
        }

        // Inflate the layout and set it as the content view
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        setContentView(binding.root)

        // Set the lifecycle owner for the binding
        binding.lifecycleOwner = this

        // Initialize the view model
        viewModel.init()
    }

    protected fun getNumberOfTasks(): Int {
        return activityManager!!.appTasks.size
    }

    protected fun getAppTaskState(): String {
        val stringBuilder = StringBuilder()
        val totalNumberOfTasks =
            activityManager?.getRunningTasks(10)?.size // Returns total number of tasks - stacks
        stringBuilder.append("\nTotal Number of Tasks: $totalNumberOfTasks\n\n")

        val taskInfo: MutableList<ActivityManager.RunningTaskInfo>? =
            activityManager?.getRunningTasks(10) // returns List of RunningTaskInfo - corresponding to tasks - stacks

        taskInfo?.forEachIndexed { infoIdx, runningTaskInfo ->
            stringBuilder.append(
                "Task level: $infoIdx\n",
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                stringBuilder.append(
                    "Task Id: ${runningTaskInfo.taskId} Number of Activities : ${runningTaskInfo.numActivities} \n",
                )
            } // Number of Activities in task - stack

            // Display the root Activity of task-stack
            stringBuilder.append(
                "TopActivity: ${
                    runningTaskInfo.topActivity!!.className.toString
                        ().replace(
                            "com.example.exploringviews",
                            "",
                        )
                }\n",
            )

            // Display the top Activity of task-stack
            stringBuilder.append(
                "BaseActivity: ${
                    runningTaskInfo.baseActivity!!.className.toString
                        ().replace(
                            "com.example.exploringviews",
                            "",
                        )
                } \n\n\n",
            )
        }
        return stringBuilder.toString()
    }

    // Abstract method to return the layout ID
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): Class<VM>

    inline fun <reified T : AppCompatActivity> goToActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }

    inline fun <reified T : AppCompatActivity> goToActivityWithFlags(flags: Int) {
        val intent = Intent(this, T::class.java)
        intent.flags = flags
        startActivity(intent)
    }

    // Optional method to initialize the view model
    open fun ViewModel.init() {}
}
