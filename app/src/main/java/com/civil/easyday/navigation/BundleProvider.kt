package com.civil.easyday.navigation

import android.os.Bundle

abstract class BundleProvider {

    private val bundle = Bundle()

    abstract fun onAddArgs(bundle: Bundle?): Bundle

    fun get(): Bundle {
        return onAddArgs(bundle)
    }
}