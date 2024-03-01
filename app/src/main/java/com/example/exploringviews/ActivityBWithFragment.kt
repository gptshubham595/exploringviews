package com.example.exploringviews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.exploringviews.databinding.ActivityBWithFragmentBinding
import com.example.exploringviews.fragments.CountryFragment
import com.example.exploringviews.fragments.FragmentB1
import com.example.exploringviews.fragments.FragmentB2
import com.example.exploringviews.fragments.FragmentB3

class ActivityBWithFragment :
    BaseActivity<ActivityBWithFragmentBinding, BaseViewModel>(),
    CountryFragment.OnCountryClickListener {
    @Suppress("ktlint:standard:property-naming")
    private val TAG = ActivityBWithFragment::class.java.simpleName
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        fragmentManager = supportFragmentManager

        binding.tvFragment.text =
            "Fragment count in back stack: ${fragmentManager.backStackEntryCount}"

//        addDefaultFragments()
        onClickListeners()
    }

    override fun getViewModel(): Class<BaseViewModel> {
        return BaseViewModel::class.java
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_b_with_fragment
    }

    private fun onClickListeners() {
        binding.btnAdd.setOnClickListener {
            val fragment =
                when (fragmentManager.backStackEntryCount % 4) {
                    0 -> FragmentB1.newInstance()
                    1 -> FragmentB2.newInstance()
                    2 -> FragmentB3.newInstance()
                    3 -> {
                        CountryFragment.newInstance().apply {
                            setCountryClickListener(this@ActivityBWithFragment)
                        }
                    }

                    else -> FragmentB1.newInstance()
                }
            fragmentManager.beginTransaction().apply {
                setReorderingAllowed(true)
                if (fragmentManager.backStackEntryCount % 4 == 3) {
                    replace(
                        binding.fragmentContainerView.id,
                        fragment,
                        fragment::class.java.simpleName,
                    )
                    addToBackStack("REPLACE ${fragment::class.java.simpleName}")
                } else {
                    add(binding.fragmentContainerView.id, fragment, fragment::class.java.simpleName)
                    addToBackStack("ADD ${fragment::class.java.simpleName}")
                }
//                add(binding.fragmentContainerView.id, fragment, fragment::class.java.simpleName)

                commit()
            }
        }

        binding.btnB1.setOnClickListener {
            fragmentManager.beginTransaction().apply {
                add(binding.fragmentContainerView.id, FragmentB1.newInstance())
                addToBackStack("FragmentB1")
                commit()
            }
        }
        binding.btnB2.setOnClickListener {
            fragmentManager.beginTransaction().apply {
                replace(binding.fragmentContainerView.id, FragmentB2.newInstance())
                commit()
            }
        }
        binding.btnB3.setOnClickListener {
            fragmentManager.beginTransaction().apply {
                replace(binding.fragmentContainerView.id, FragmentB3.newInstance())
                commit()
            }
        }

        fragmentManager.addOnBackStackChangedListener {
            Log.d(TAG, "BackStackCount: ${fragmentManager.backStackEntryCount}")
            binding.tvFragment.text =
                "Fragment count in back stack: ${fragmentManager.backStackEntryCount}"
            var str = ""
            for (i in fragmentManager.backStackEntryCount - 1 downTo 0) {
                val entry = fragmentManager.getBackStackEntryAt(i)
                str += "${entry.name}\n"
            }
            Log.d(TAG, "BackStackEntry: $str")
        }
    }

    private fun addDefaultFragments() {
        // add fragments B1, B2, B3 here
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(binding.fragmentContainerView.id, FragmentB1.newInstance())
            commit()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
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

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount > 0) {
//            fragmentManager.beginTransaction().apply {
//                val fragment = fragmentManager.findFragmentById(binding.fragmentContainerView.id)
//                fragment?.let {
//                    remove(it)
//                    addToBackStack("Remove ${fragment.javaClass.simpleName}")
//                    commit()
//                }
//            }
            var flag = false
            fragmentManager.findFragmentById(binding.fragmentContainerView.id)?.let {
                if (it.childFragmentManager.backStackEntryCount > 0) {
                    it.childFragmentManager.popBackStack()
                    flag = true
                }
            }
            if (!flag) {
                fragmentManager.popBackStack()
            }
        } else {
            super.onBackPressed()
        }
        Log.d(TAG, "onBackPressed")
    }

    override fun onCountryClick(country: String) {
        Log.d(TAG, "Selected country: $country")
    }
}
