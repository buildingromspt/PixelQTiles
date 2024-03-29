package com.buildingromspt.lockandnfc;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class LockAccessibilityService extends AccessibilityService {
    @Override
    public void onServiceConnected() {
        performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN);
        disableSelf();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

    }

    @Override
    public void onInterrupt() {

    }

}
