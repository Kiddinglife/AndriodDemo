package com.cupcake.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cupcake.bluetooth.BlueToothManager;
import com.cupcake.database.Database;
import com.cupcake.logic.FavouriteCakeAdapter;
import com.cupcake.model.Cake;
import com.cupcake.model.Order;
import com.cupcake.util.Constants;
import com.example.mad_finalassign.R;

public class FavouriteCakes extends ListActivity
    {
	// order list
	private List<Cake> list;
	private TextView title;
	private Button sendBtn;
	private Button cancelBtn;
	private Database myDatabaseHelper;
	private BluetoothAdapter bluetoothAdapter;
	// adapter
	private FavouriteCakeAdapter adapter;
	private BlueToothManager blueToothManager;
	private int state;
	private List<Cake> sendingCakes = new ArrayList<Cake>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourite_cakes);
		sendBtn = (Button) findViewById(R.id.send_cakes_button);
		cancelBtn =  (Button) findViewById(R.id.cancel_conn_button);
		this.getListView().setDividerHeight(3);
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		myDatabaseHelper = new Database(this);
		blueToothManager = new BlueToothManager(this, handler);
		state = 0;
		loadOrders();
	    }

	// the habdler recives the data from blutooth manager and update the activity UI
	private final Handler handler = new Handler()
	    {
		@Override
		public void handleMessage(Message msg)
		    {
			Log.v("mad", "handler in fv cakes");
			switch (msg.what)
			    {
			    // upfate the connection state in UI
			    case Constants.MSG_CURRENT_STATE:
				setProgressBarIndeterminateVisibility(true);
				switch (msg.arg1)
				    {
				    case Constants.CONNECTED:
					Log.v("mad", "connected!");
					setProgressBarIndeterminateVisibility(false);
					setTitle(getString(R.string.connected) + " " + (String) msg.obj);
					sendBtn.setVisibility(View.VISIBLE);
					cancelBtn.setVisibility(View.VISIBLE);
					break;
				    case Constants.CONNECTING:
					Log.v("mad", "connecting....!");
					setTitle(getString(R.string.connecting) + " " + (String) msg.obj);
					break;
				    case Constants.LISTENNING:
					Log.v("mad", "LISTENNING....!");
					setTitle(R.string.listen);
					break;
				    case Constants.NONE:
					Log.v("mad", "Disconnected");
					setTitle(getString(R.string.disconnected));
					break;
				    }
				break;
			    case Constants.RECEIVED_DATA:
				Log.v("mad", "data received!");
				@SuppressWarnings("unchecked")
				List<Cake> receivedCakes = (List<Cake>) msg.obj;
				Order order = new Order();
				if (list == null || list.size() == 0)
				    list = new ArrayList<Cake>();
				if (receivedCakes.size() > 0)
				    {
					for (Cake cake : receivedCakes)
					    {
						list.add(cake);
						order.setCake(cake);
						// myDatabaseHelper.addOrder(order);
					    }
					adapter = new FavouriteCakeAdapter(FavouriteCakes.this, list);
					setListAdapter(adapter);
				    }
				// loadOrders();
				// construct a string from the valid bytes in the buffer
				Log.v("mad", "received data is" + receivedCakes.toString());
				break;
			    }
		    }
	    };

	@Override
	public void onStart()
	    {
		super.onStart();
		if (!bluetoothAdapter.isEnabled())
		    {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, Constants.REQUEST_ENABLE_BLUETOOTH);
		    }
	    }

	@Override
	public synchronized void onResume()
	    {
		super.onResume();
		Log.v("mad", "on resume in fv cakes");
		if (state == 1)
		    {
			if (blueToothManager.getConnState() == Constants.NONE)
			    blueToothManager.ListenningAsServer();
		    }
	    }

	/**
	 * draw all the rowa using the data from datbase
	 */
	private void loadOrders()
	    {
		// initilize the list instannce
		list = myDatabaseHelper.getAllFavouriteCakes();
		// Log.v("mad", "size in loadorders is" + list.size());
		// if o show empty msg to user
		if (list.size() == 0)
		    {
			list = new ArrayList<Cake>();
			adapter = new FavouriteCakeAdapter(this, list);
			title = (TextView) findViewById(R.id.fv_cakes__title);
			title.setText("empty");
		    }
		else
		    {
			// else draw the list
			adapter = new FavouriteCakeAdapter(this, list);
			setListAdapter(adapter);
		    }
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	    {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.share_cakes_menu, menu);
		return super.onCreateOptionsMenu(menu);
	    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	    {
		switch (item.getItemId())
		    {
		    case R.id.share_cakes:
			Intent serverIntent = new Intent(this, BluetoothRemoteDevicesList.class);
			startActivityForResult(serverIntent, Constants.REQUEST_CONNECT_DEVICE);
			break;
		    case R.id.setup_bluetooth:
			state = 1;
			Log.v("mad", "state is" + state);
			enableDiscoveryBluetooth();
			break;
		    }
		return super.onOptionsItemSelected(item);
	    }

	public void onActivityResult(int requestCode, int resultCode, Intent data)
	    {
		switch (requestCode)
		    {
		    case Constants.REQUEST_CONNECT_DEVICE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Constants.RETURNED_RESULT_GOOD)
			    {
				Log.v("mad", "onActivityResult() conn device");
				// Get the device MAC address
				String address = data.getExtras().getString(Constants.DEVICE_MAC_ADDR);
				// Get the BLuetoothDevice object
				BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
				// Attempt to connect to the device
				Log.v("mad", "connected device is" + device.toString());
				blueToothManager.connectAsClient(device);
			    }
			break;
		    }
	    }

	private void enableDiscoveryBluetooth()
	    {
		Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 500);
		startActivity(discoveryIntent);
	    }

	// the click event listenner
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	    {
		Toast.makeText(this, "Selected", Toast.LENGTH_SHORT).show();
		sendingCakes.add(list.get(position));
	    }

	public void sendCakesBtnClicked(View view)
	    {
		Log.v("mad", "send cakes " + sendingCakes.size());
		Toast.makeText(this, "Sent Already", Toast.LENGTH_SHORT).show();
		if (sendingCakes != null && sendingCakes.size() > 0)
		    blueToothManager.sendObj((Serializable) sendingCakes);
		sendingCakes.clear();
	    }
	
	public void CancelConnectionBtnClicked(View view)
	    {
		Log.v("mad", "cancel connection ");
		 blueToothManager.freeAll();
		 blueToothManager.setConnState(Constants.NONE, null);
	    }
	

	@Override
	protected void onDestroy()
	    {
		Log.v("mad", "fv onDestroy");
		super.onDestroy();
		blueToothManager.freeAll();
	    }

    }
