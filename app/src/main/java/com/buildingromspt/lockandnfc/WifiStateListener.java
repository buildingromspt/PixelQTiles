package com.buildingromspt.lockandnfc;

public interface WifiStateListener {
    void onWifiStateChanged(WifiState state, String ssid);
}
