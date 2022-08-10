package com.civil.easyday.navigation

import android.content.Intent
import androidx.annotation.StringRes

sealed class UiEvent {

    class Navigation(val navAttribs: NavAttribs) : UiEvent()

    object NavBack : UiEvent()

    object GoToMain : UiEvent()

    class OpenIntent(val intent: Intent, val finishSourceActivity: Boolean = false) : UiEvent()

    class ShowToast(@StringRes val messageResId: Int) : UiEvent()

    class ShowToastMsg(val message: String) : UiEvent()

    class ShowPopup(val message: String, val title: String? = null) : UiEvent()

    object HideKeyboard : UiEvent()
    object ShowLoading : UiEvent()
    object HideLoading : UiEvent()

}