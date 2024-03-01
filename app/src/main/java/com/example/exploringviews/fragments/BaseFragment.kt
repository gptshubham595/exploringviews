package com.example.exploringviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<B : ViewDataBinding, VM : ViewModel> : Fragment() {
    lateinit var binding: B
    lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application),
            )[getViewModel()]

        // Inflate the layout and set it as the content view
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        // Set the lifecycle owner for the binding
        binding.lifecycleOwner = viewLifecycleOwner

        // Initialize the view model
        viewModel.init()
        return binding.root
    }

    // Abstract method to return the layout ID
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): Class<VM>

    // Optional method to initialize the view model
    open fun ViewModel.init() {}
}
