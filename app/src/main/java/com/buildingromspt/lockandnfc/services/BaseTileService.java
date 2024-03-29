package com.buildingromspt.lockandnfc.services;

import android.service.quicksettings.TileService;

public abstract class BaseTileService extends TileService {
    protected void runIfUnlocked(final Runnable runnable) {
        if (isLocked()) {
            unlockAndRun(runnable);
        } else {
            runnable.run();
        }
    }
}