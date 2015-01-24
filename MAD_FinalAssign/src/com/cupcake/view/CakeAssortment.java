package com.cupcake.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.cupcake.logic.CakeListAdapter;
import com.cupcake.logic.MyService;
import com.cupcake.model.Cake;
import com.cupcake.model.WareHouse;
import com.cupcake.util.Constants;
import com.cupcake.util.ExitAppSafety;
import com.example.mad_finalassign.R;

@SuppressLint("HandlerLeak")
public class CakeAssortment extends Activity
    {
	private WareHouse wareHouse;
	private GridView gridView;
	private List<Cake> list;
	private ProgressDialog progressDialog;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cake_grid);
		ExitAppSafety.activities.add(this);
		gridView = (GridView) findViewById(R.id.cake_list);

		showDialog(Constants.PROGRESS_DIALOG);
		registerItemCliked();

		// register broadcast receiver to receive the WareHouse instanvce from MyService
		IntentFilter downlaodActionFilter = new IntentFilter(Constants.BROADCAST_GET_WARE_HOUSE);
		registerReceiver(getWareHouse, downlaodActionFilter);

		// get cakelist
		OrderToService(Constants.DOWNLOAD_AND_PARSE_XML);
		// get warehouse instance from service and initilize it in cale list activity
		OrderToService(Constants.GIVE_ME_WAREHOUSE);
	    }

	private void registerItemCliked()
	    {
		gridView.setOnItemClickListener(new OnItemClickListener()
		    {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			    {
				// Toast.makeText(CakeGrid.this, "pic" + arg2, Toast.LENGTH_SHORT).show();
				// get the selected cake instance from list
				Cake cake = list.get(arg2);
				Intent intent = new Intent();
				intent.setClass(CakeAssortment.this, SelectedCake.class);
				Bundle mBundle = new Bundle();
				mBundle.putSerializable(Constants.SelectedCake, cake);
				intent.putExtras(mBundle);
				CakeAssortment.this.startActivity(intent);
				//finish();
			    }
		    });
	    }

	/**
	 * this methos is used to control the player by sending the order to player using intent
	 */
	private void OrderToService(int action)
	    {
		Intent intent = new Intent();
		intent.putExtra(getString(R.string.msg), action);
		intent.setClass(CakeAssortment.this, MyService.class);

		/* need to register the service in feset.xml£º<service></service> */
		CakeAssortment.this.startService(intent);
	    }

	// register receiver instance
	private BroadcastReceiver getWareHouse = new BroadcastReceiver()
	    {
		@Override
		public void onReceive(Context context, Intent intent)
		    {
			// take out the wareHouse instance in the intent bundle
			// and inilize the wareHouse instance
			wareHouse = (WareHouse) intent.getSerializableExtra(Constants.BROADCAST_INTENT_KEY);
			cancelProgressBar();
			drawList();
		    }
	    };

	private void drawList()
	    {
		// set a new list
		list = new ArrayList<Cake>();
		// loop the cake map and fill in the empty list
		Map<String, Cake> map = wareHouse.getCakes();
		Collection<Cake> c = map.values();
		for (Iterator<Cake> it = c.iterator(); it.hasNext();)
		    {
			list.add(it.next());
		    }
		// Log.v("mad", "list cake size is" + list.size());
		CakeListAdapter cakeListAdapter = new CakeListAdapter(this, list);
		gridView.setAdapter(cakeListAdapter);
	    }

	private void cancelProgressBar()
	    {

		Message msg = handler.obtainMessage();
		Bundle b = new Bundle();
		b.putInt("total", 100);
		msg.setData(b);
		handler.sendMessage(msg);
	    }

	// Define the Handler that receives messages from the thread and update the progress
	@SuppressLint("HandlerLeak")
	final Handler handler = new Handler()
	    {
		@SuppressWarnings("deprecation")
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg)
		    {
			int total = msg.getData().getInt("total");
			progressDialog.setProgress(total);
			if (total == 100)
			    {
				dismissDialog(Constants.PROGRESS_DIALOG);
			    }
		    }
	    };

	protected Dialog onCreateDialog(int id)
	    {
		switch (id)
		    {
		    case Constants.PROGRESS_DIALOG:
			progressDialog = new ProgressDialog(CakeAssortment.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Loading...");
			return progressDialog;
		    default:
			return null;
		    }
	    }

	@Override
	protected void onDestroy()
	    {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(getWareHouse);
	    }
    }
