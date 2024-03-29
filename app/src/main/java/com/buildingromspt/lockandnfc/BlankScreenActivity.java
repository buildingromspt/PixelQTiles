package com.buildingromspt.lockandnfc;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;


public class BlankScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Settings.Secure.putString(getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, "com.buildingromspt.lockandnfc/com.buildingromspt.lockandnfc.LockAccessibilityService");
        Settings.Secure.putString(
                getContentResolver(),
                Settings.Secure.ACCESSIBILITY_ENABLED, "1");
        super.onCreate(savedInstanceState);
        finish();
    }
    }