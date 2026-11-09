package com.nthexperience.desktop;

import android.content.Context;

/** Surface suites exercise a desktop whose optional setup has already been completed or deferred. */
final class EightTestSetup {
    static void afterInitialization(Context context) {
        boolean saved = context.getSharedPreferences(InitializationState.PREFS, 0).edit()
                .clear().putInt("flow_version", 2)
                .putInt("step", InitializationState.Step.DONE.ordinal()).commit();
        if (!saved) throw new AssertionError("Could not prepare completed initialization fixture");
    }
}
