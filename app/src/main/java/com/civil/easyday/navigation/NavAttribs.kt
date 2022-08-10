package com.civil.easyday.navigation

import android.os.Bundle

class NavAttribs(screen: Screen, bp: BundleProvider? = null, addToBackStack: Boolean = true) {

    var screen: Screen? = screen
        private set
    var args: Bundle? = null
        private set
    var addToBackStack: Boolean = addToBackStack
        private set

    init {
        bp?.let { this.args = it.get() }
    }
}