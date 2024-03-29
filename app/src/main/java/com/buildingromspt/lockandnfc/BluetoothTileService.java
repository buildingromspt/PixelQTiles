package com.buildingromspt.lockandnfc;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Handler;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;





public class BluetoothTileService extends TileService {
    public BluetoothAdapter getmBluetoothAdapter() {
        return mBluetoothAdapter;
    }
    Handler handler = new Handler();
    @Override
    public void onStartListening() {
        super.onStartListening();
        final Tile tile = getQsTile();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
        if (mBluetoothAdapter == null) {

            // Device does not support Bluetooth

        } else if (!mBluetoothAdapter.isEnabled()) {
            tile.setIcon(Icon.createWithResource(getApplicationContext(), R.drawable.bluetoothdesligado));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                tile.setLabel(getString(R.string.bluetooth_tile_label));
                tile.setSubtitle(getString(R.string.bt_tile_off));
            } else {
                tile.setLabel(getString(R.string.bt_tile_off));
            }
            tile.setState(Tile.STATE_INACTIVE);
            // Bluetooth is not enabled :)
/*        }else if (ligar == 1) {
            tile.setIcon(Icon.createWithResource(this, R.drawable.bluetoothligado));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                tile.setLabel(getString(R.string.bluetooth_tile_label));
                tile.setSubtitle(getString(R.string.bt_tile_search));
            } else {
                tile.setLabel(getString(R.string.bt_tile_search));
            }
            tile.setState(Tile.STATE_ACTIVE);*/

/*        } else if (mBluetoothAdapter.isEnabled()) {
            tile.setIcon(Icon.createWithResource(this, R.drawable.bluetoothligado));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                tile.setLabel(getString(R.string.bluetooth_tile_label));
                tile.setSubtitle(getString(R.string.bt_tile_search));
            } else {
                tile.setLabel(getString(R.string.bt_tile_search));
            }
            tile.setState(Tile.STATE_ACTIVE);*/
        } else if (mBluetoothAdapter.isEnabled()) {
        tile.setIcon(Icon.createWithResource(getApplicationContext(), R.drawable.bluetoothon));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tile.setLabel(getString(R.string.bluetooth_tile_label));
            tile.setSubtitle(getString(R.string.bt_tile_on));
        } else {
            tile.setLabel(getString(R.string.bt_tile_on));
        }
        tile.setState(Tile.STATE_ACTIVE);
    }
        tile.updateTile();
        handler.postDelayed(this, 1000);
            }
        };

//Start
        handler.postDelayed(runnable, 1000);
        }




    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    @SuppressLint("MissingPermission")
    @Override
    public void onClick() {
        super.onClick();
        if (!mBluetoothAdapter.isEnabled()) {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            mBluetoothAdapter.enable();
        } else{
            mBluetoothAdapter.disable();
        }
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
        }
}