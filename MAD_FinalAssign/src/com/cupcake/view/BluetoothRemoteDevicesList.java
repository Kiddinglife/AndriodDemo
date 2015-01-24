package com.cupcake.view;

import java.util.Set;

import com.cupcake.util.Constants;
import com.example.mad_finalassign.R;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BluetoothRemoteDevicesList extends ListActivity
    {
	private BluetoothAdapter bluetoothAdapter;
	private ArrayAdapter<String> devicesArrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	    {
		super.onCreate(savedInstanceState);
		// Setup the window
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		// Set result CANCELED incase the user backs out
		setResult(Activity.RESULT_CANCELED);
		setContentView(R.layout.found_devices);
		devicesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
		setListAdapter(devicesArrayAdapter);
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// Register for broadcasts when a device is discovered
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);

		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);
	    }

	private void startDiscovery()
	    {
		Log.v("mad", "discovering...");
		// Indicate scanning in the title
		setProgressBarIndeterminateVisibility(true);
		setTitle(R.string.scan);
		// Request discover from BluetoothAdapter
		bluetoothAdapter.startDiscovery();
	    }

	@Override
	public void onResume()
	    {
		super.onResume();
		Log.v("mad", " devices list on resume");
		if (!bluetoothAdapter.enable())
		    bluetoothAdapter.enable();
		if (!bluetoothAdapter.isDiscovering())
		    startDiscovery();
	    }

	@Override
	public void onPause()
	    {
		super.onPause();
		Log.v("mad", " devices list on psause");
		bluetoothAdapter.cancelDiscovery();
	    }

	// The BroadcastReceiver that listens for discovered devices and
	// changes the title when discovery is finished
	private final BroadcastReceiver mReceiver = new BroadcastReceiver()
	    {
		@Override
		public void onReceive(Context context, Intent intent)
		    {
			String action = intent.getAction();
			Log.v("mad", "on receive in device list");
			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action))
			    {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				devicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
				Log.v("mad", device.getName() + "\n" + device.getAddress());
			    }
			else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
			    {
				setProgressBarIndeterminateVisibility(false);
				if (devicesArrayAdapter.getCount() == 0)
				    {
					String noDevices = getResources().getText(R.string.none_found).toString();
					devicesArrayAdapter.add(noDevices);
				    }
			    }
		    }
	    };

	@Override
	protected void onDestroy()
	    {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mReceiver);
	    }

	// the click event listenner
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	    {
		// Cancel discovery because it's costly and we're about to connect
		bluetoothAdapter.cancelDiscovery();

		// Get the device MAC address, which is the last 17 chars in the View
		String info = ((TextView) v).getText().toString();
		String address = info.substring(info.length() - 17);

		// Create the result Intent and include the MAC address
		Intent intent = new Intent();
		intent.putExtra(Constants.DEVICE_MAC_ADDR, address);

		// Set result and finish this Activity
		setResult(Constants.RETURNED_RESULT_GOOD, intent);
		finish();
	    }
    }
