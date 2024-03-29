package com.buildingromspt.lockandnfc.services;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.nfc.NfcAdapter;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import androidx.annotation.RequiresPermission;

import com.buildingromspt.lockandnfc.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NfcTileService extends BaseTileService {
    private Icon nfcTileIcon;
    private String nfcTileName;

    @Override
    public void onCreate() {
        super.onCreate();
        nfcTileIcon = Icon.createWithResource(this, R.drawable.ic_nfc_tile);
        nfcTileName = getString(R.string.nfc_tile_label);
    }

    /**
     * Called when the user adds this tile to Quick Settings.
     * <p/>
     * Note that this is not guaranteed to be called between {@link #onCreate()}
     * and {@link #onStartListening()}, it will only be called when the tile is added
     * and not on subsequent binds.
     */
    public void onTileAdded() {
        final Tile tile = getQsTile();
        final Boolean status = getNfcStatus();
        setTileStatus(tile, status);
        tile.updateTile();
    }

    /**
     * Called when the user removes this tile from Quick Settings.
     */
    public void onTileRemoved() {
    }

    /**
     * Called when this tile moves into a listening state.
     * <p/>
     * When this tile is in a listening state it is expected to keep the
     * UI up to date.  Any listeners or callbacks needed to keep this tile
     * up to date should be registered here and unregistered in {@link #onStopListening()}.
     *
     * @see #getQsTile()
     * @see Tile#updateTile()
     */
    public void onStartListening() {
        final Tile tile = getQsTile();
        final Boolean status = getNfcStatus();
        tile.setLabel(nfcTileName);
        tile.setIcon(nfcTileIcon);
        setTileStatus(tile, status);
        tile.updateTile();
    }

    /**
     * Called when this tile moves out of the listening state.
     */
    public void onStopListening() {
    }

    /**
     * Called when the user clicks on this tile.
     */
    public void onClick() {
        runIfUnlocked(() -> {
            final Tile tile = getQsTile();
            Boolean nfcStatus = getNfcStatus();
            if (nfcStatus == null) {
                startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
                return;
            }
            if (setNfcStatus(!nfcStatus)) {
                setTileStatus(tile, !nfcStatus);
                tile.updateTile();
            }
        });
    }

    private void setTileStatus(final Tile tile, final Boolean status) {
        if (status == null) {
            tile.setState(Tile.STATE_UNAVAILABLE);
        } else if (status) {
            tile.setState(Tile.STATE_ACTIVE);
        } else {
            tile.setState(Tile.STATE_INACTIVE);
        }
    }

    private Boolean getNfcStatus() {
        final NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) return null;

        return nfcAdapter.isEnabled();
    }

    @RequiresPermission(Manifest.permission.WRITE_SECURE_SETTINGS)
    private boolean setNfcStatus(boolean value) {
        final NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) return false;
        Boolean success = null;
        try {
            Class<?> NfcManagerClass;
            NfcManagerClass = Class.forName(nfcAdapter.getClass().getName());
            Method setNfcEnabled;

            setNfcEnabled = NfcManagerClass.getDeclaredMethod(value ? "enable" : "disable");
            final boolean oldAccess = setNfcEnabled.isAccessible();
            setNfcEnabled.setAccessible(true);
            success = (Boolean) setNfcEnabled.invoke(nfcAdapter);
            setNfcEnabled.setAccessible(oldAccess);
            return success;
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return false;
    }
}