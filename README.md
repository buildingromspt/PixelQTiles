PixelQTiles for Android
===============================

An app that readds functionality to Pixel devices.

The app allows the following:
 - Adds a shortcut to lock the screen from the app drawer (and homescreen if you move the shortcut there) and a quicksettings tile for that while allowing unlock with fingerprint or face unlock (needs Secure.Settings permission - see below).
 - Adds a quicksettings tile that directly controls WiFi (needs location permission but not precise location).
 - Adds a quicksettings tile that directly controls NFC.
 - Adds a quicksettings tile that directly controls Bluetooth (needs nearby devices location).

Usage
-----
1. Install the app. An APK might be available in 
https://github.com/ruimrb/PixelQTiles/releases/


2. Give the app Secure.Settings permission through ADB.
Locking the screen while retaining biometric unlock is only possible with an acessibility service. Giving the app this permission allows it to enable the acessibility service, lock the screen and imediately disable the acessibility service saving battery and improving smoothness.
The code is "adb shell pm grant com.buildingromspt.lockandnfc android.permission.WRITE_SECURE_SETTINGS".
If you dont know how to use ADB here is a good link https://www.xda-developers.com/install-adb-windows-macos-linux/


3. Pull down the Quick Settings pane, click the pencil icon and choose any of the quicksetting tiles provided.
