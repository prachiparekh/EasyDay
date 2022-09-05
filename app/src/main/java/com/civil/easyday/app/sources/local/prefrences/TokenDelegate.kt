package com.civil.easyday.app.sources.local.prefrences

import com.pixplicity.easyprefs.library.Prefs
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class TokenDelegate : ReadWriteProperty<AppPreferencesDelegates, String> {

    companion object {
        const val PREF_KEY_TOKEN = "token"
    }

    override fun getValue(thisRef: AppPreferencesDelegates, property: KProperty<*>): String =
        Prefs.getString(PREF_KEY_TOKEN,null)


    override fun setValue(
        thisRef: AppPreferencesDelegates,
        property: KProperty<*>,
        value: String
    ) {
        Prefs.putString(PREF_KEY_TOKEN, value)
    }
}
