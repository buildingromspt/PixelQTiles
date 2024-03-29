package com.buildingromspt.lockandnfc;

import static android.provider.Settings.Global.AIRPLANE_MODE_ON;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;


public class WifiTileService extends TileService implements WifiStateListener {
    WifiStateReceiver wifiStateReceiver;

    @Override
    public void onStartListening() {
        super.onStartListening();
        if (wifiStateReceiver != null) {
            return;
        }
        wifiStateReceiver = WifiStateReceiver.createAndRegister(this, this);
        // Get current wifi state
        wifiStateReceiver.onReceive(this, null);
    }

    @Override
    public void onWifiStateChanged(WifiState state, String ssid) {
        final Tile tile = getQsTile();
        switch (state) {
            case DISABLED:
                tile.setIcon(Icon.createWithResource(this, R.drawable.disabled));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    tile.setLabel(getString(R.string.wifi_tile_label));
                    tile.setSubtitle(getString(R.string.wifi_tile_off));
                } else {
                    tile.setLabel(getString(R.string.wifi_tile_off));
                }
                tile.setState(Tile.STATE_INACTIVE);
                break;
            case ENABLED:
                tile.setIcon(Icon.createWithResource(this, R.drawable.enabled));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    tile.setLabel(getString(R.string.wifi_tile_label));
                    tile.setSubtitle(getString(R.string.wifi_tile_on));
                } else {
                    tile.setLabel(getString(R.string.wifi_tile_on));
                }
                tile.setState(Tile.STATE_ACTIVE);
                break;
            case CONNECTED:
                tile.setIcon(Icon.createWithResource(this, R.drawable.connected));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    tile.setLabel(getString(R.string.wifi_tile_label));
                    tile.setSubtitle(ssid != null ? ssid : (getString(R.string.wifi_tile_connected)));
                } else {
                    tile.setLabel(ssid != null ? ssid : (getString(R.string.wifi_tile_connected)));
                }
                tile.setState(Tile.STATE_ACTIVE);
                break;
        }
        tile.updateTile();
    }

    @Override
    public void onClick() {
        super.onClick();
        final WifiManager wifiMan = getSystemService(WifiManager.class);
        wifiMan.setWifiEnabled(!wifiMan.isWifiEnabled());
    }



    @Override
    public void onStopListening() {
        super.onStopListening();
        if (wifiStateReceiver != null) {
            unregisterReceiver(wifiStateReceiver);
            wifiStateReceiver = null;
        }
    }


    }