package com.example.exploringviews.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.exploringviews.R
import com.example.exploringviews.databinding.FragmentCountriesBinding

class CountryFragment : BaseFragment<FragmentCountriesBinding, BaseFragmentViewModel>() {
    var country: String? = null

    interface OnCountryClickListener {
        fun onCountryClick(country: String)
    }

    private var listener: OnCountryClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d(TAG, "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_countries
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

        if (savedInstanceState != null) {
            binding.fragmentContainerView.visibility =
                if (savedInstanceState.getBoolean(
                        "KEY_COUNTRY_VISIBILITY",
                    )
                ) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            country = savedInstanceState.getString("KEY_COUNTRY")
        }

        initUi()

        Log.d(TAG, "onViewCreated $savedInstanceState")
    }

    fun setCountryClickListener(listener: OnCountryClickListener) {
        this.listener = listener
    }

    private fun initUi() {
        val countryAdapter =
            ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.countries_array),
            )
        binding.listViewCountries.adapter = countryAdapter
        binding.listViewCountries.setOnItemClickListener { _, _, position, _ ->
            country = countryAdapter.getItem(position)
            country?.let {
                Log.d(TAG, "Selected country: $country")
                listener?.onCountryClick(it)
                val fragmentManager = childFragmentManager
                fragmentManager.beginTransaction().apply {
                    add(
                        binding.fragmentContainerView.id,
                        CountryDescFragment.newInstance().apply {
                            arguments =
                                Bundle().apply {
                                    putString("KEY_COUNTRY", country)
                                }
                        },
                    )
                    addToBackStack(null)
                    binding.fragmentContainerView.visibility = View.VISIBLE
                    commit()
                }
            }
        }
        childFragmentManager.addOnBackStackChangedListener {
            if (childFragmentManager.backStackEntryCount == 0) {
                binding.fragmentContainerView.visibility = View.GONE
            }
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
        fun newInstance() = CountryFragment()

        val TAG = CountryFragment::class.java.simpleName
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d(FragmentB2.TAG, "onViewStateRestored")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(
            "KEY_COUNTRY_VISIBILITY",
            binding.fragmentContainerView.visibility == View.VISIBLE,
        )
        outState.putString("KEY_COUNTRY", country)
        Log.d(FragmentB2.TAG, "onSaveInstanceState")
    }
}
