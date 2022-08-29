package com.civil.easyday.screens.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.civil.easyday.dialogs.DialogUtils.Companion.showErrorInfo
import com.civil.easyday.navigation.NavAttribs
import com.civil.easyday.navigation.UiEvent
import com.civil.easyday.utils.DeviceUtils
import java.lang.reflect.ParameterizedType


abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    lateinit var viewModel: VM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())

        viewModel = ViewModelProvider(this)[getViewModelClass()]

        viewModel.uiEventStream.observe(this) { it -> processUiEvent(it) }

        setupUi()
        setupObservers()
        viewModel.onActivityCreated()

    }


    abstract fun getContentView(): Int
    abstract fun setupUi()
    abstract fun setupObservers()


    private fun getViewModelClass(): Class<VM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<VM>
    }

    fun processUiEvent(uiEvent: UiEvent) {

        when (uiEvent) {

            is UiEvent.Navigation -> {
                onNavigationEvent(uiEvent.navAttribs)
            }
            is UiEvent.NavBack -> {
                onBackPressed()
            }

            is UiEvent.GoToMain -> {
//                supportFragmentManager.popBackStack(DashboardFragment.TAG, 0)
            }

            is UiEvent.OpenIntent -> {
                startActivity(uiEvent.intent)
                if (uiEvent.finishSourceActivity) {
                    finish()
                }
            }
            is UiEvent.HideKeyboard -> {
                DeviceUtils.hideKeyboard(this)
            }
            is UiEvent.ShowToast -> {
                showToast(getString(uiEvent.messageResId))
            }
            is UiEvent.ShowToastMsg -> {
                showToast(uiEvent.message)
            }

            else -> {}
        }
    }

    private fun showToast(message: String) {
        showErrorInfo(this, message)
    }

    private fun onNavigationEvent(navAttribs: NavAttribs) {
        navAttribs.screen?.let {
            DeviceUtils.hideKeyboard(this)
            handleNavigationEvent(navAttribs)
        }
    }

    private fun handleNavigationEvent(navAttribs: NavAttribs) {


    }


    override fun onBackPressed() {
        DeviceUtils.hideKeyboard(this)

        supportFragmentManager.popBackStack()
    }


}