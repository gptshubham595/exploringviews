package com.example.exploringviews.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.exploringviews.R
import com.example.exploringviews.databinding.FragmentCountryDescBinding

class CountryDescFragment : BaseFragment<FragmentCountryDescBinding, BaseFragmentViewModel>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d(TAG, "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_country_desc
    }

    override fun getViewModel(): Class<BaseFragmentViewModel> {
        return BaseFragmentViewModel::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val country = it.getString("KEY_COUNTRY")
            when (country) {
                "India" -> binding.tvCountryDesc.text = resources.getString(R.string.india_desc)
                "USA" -> binding.tvCountryDesc.text = resources.getString(R.string.usa_desc)
                "UK" -> binding.tvCountryDesc.text = resources.getString(R.string.uk_desc)
                "Canada" -> binding.tvCountryDesc.text = resources.getString(R.string.canada_desc)
                "Germany" -> binding.tvCountryDesc.text = resources.getString(R.string.germany_desc)
                "France" -> binding.tvCountryDesc.text = resources.getString(R.string.france_desc)
            }
        }
        if (savedInstanceState != null) {
            binding.tvCountryDesc.text = savedInstanceState.getString("key")
        }
        Log.d(TAG, "onViewCreated")
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

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        Log.d(TAG, "onAttach")
    }

    companion object {
        @JvmStatic
        fun newInstance() = CountryDescFragment()

        val TAG = CountryDescFragment::class.java.simpleName
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d(FragmentB2.TAG, "onViewStateRestored")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("key", binding.tvCountryDesc.text.toString())
        Log.d(FragmentB2.TAG, "onSaveInstanceState")
    }
}
