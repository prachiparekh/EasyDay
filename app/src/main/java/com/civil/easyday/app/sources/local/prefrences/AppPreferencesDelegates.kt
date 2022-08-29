package com.civil.easyday.app.sources.local.prefrences


class AppPreferencesDelegates private constructor() {
    var wasOnboardingSeen by WasOnboardingSeenDelegate()

    companion object {
        private var INSTANCE: AppPreferencesDelegates? = null
        fun get(): AppPreferencesDelegates = INSTANCE ?: AppPreferencesDelegates()
    }

}