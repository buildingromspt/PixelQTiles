package com.buildingromspt.lockandnfc;

import android.content.Intent;
import android.service.quicksettings.TileService;

public class LockTileService extends TileService {
    @Override
    public void onClick() {
        Intent intent = new Intent(this,BlankScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityAndCollapse(intent);
    }
}