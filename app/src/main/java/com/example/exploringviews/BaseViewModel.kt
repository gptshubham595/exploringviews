package com.example.exploringviews

import androidx.lifecycle.ViewModel

class BaseViewModel : ViewModel() {
    companion object {
        var instanceCounterA = 0
        var instanceCounterB = 0
        var instanceCounterC = 0
        var instanceCounterD = 0
    }
}
