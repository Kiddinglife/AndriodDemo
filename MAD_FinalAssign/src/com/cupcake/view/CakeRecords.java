package com.cupcake.view;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cupcake.database.Database;
import com.cupcake.logic.CakeRecordsAdapter;
import com.cupcake.model.Order;
import com.cupcake.util.Constants;
import com.cupcake.util.HttpDownloader;
import com.example.mad_finalassign.R;

public class CakeRecords extends ListActivity
    {
	// order list
	private List<Order> list;
	private TextView title;
	private Database myDatabaseHelper;
	// current row id that is clicked by user
	private int clickedPosition;
	// adapter
	private CakeRecordsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cake_records);
		this.getListView().setDividerHeight(3);
		myDatabaseHelper = new Database(this);
		loadOrders();
		checkCakeStatus();
	    }

	/**
	 * this function will create a thread to listen cake status from remote server it will lopp until the eis cooked
	 */
	private void checkCakeStatus()
	    {
		for (Order order : list)
		    {
			if (order.getCake().getStatus().equalsIgnoreCase("uncooked"))
			    new checkCakeCooked(order.getId()).start();
		    }
	    }

	/**
	 * Define the Handler that receives messages from the thread and update the cake records
	 */
	@SuppressLint("HandlerLeak")
	final Handler handler = new Handler()
	    {
		@SuppressWarnings("deprecation")
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg)
		    {
			switch (msg.what)
			    {
			    case Constants.COOKED:
				// Log.v("mad", "cooked ");
				loadOrders();
				break;
			    default:
				break;
			    }
		    }
	    };

	/**
	 * inner class for checking the cake status id is the id in the database
	 */
	class checkCakeCooked extends Thread
	    {

		private String id;

		public checkCakeCooked(String id)
		    {
			super();
			this.id = id;
		    }

		@Override
		public void run()
		    {
			super.run();
			HttpDownloader http = new HttpDownloader();
			String xmlStr = "none";
			// get the cakes.xml in the form of string
			while (!xmlStr.equalsIgnoreCase("cooked"))
			    {
				try
				    {
					// this is used to reduce the frequency to connect to the server
					Thread.sleep(Constants.ONE_SECOND);
				    } catch (InterruptedException e)
				    {
					e.printStackTrace();
				    }

				// connect to the server and send the id value to server
				xmlStr = http.downloadTextFile(getString(R.string.cake_check) + "?id=" + id);
			    }
			// updatecake status in database
			myDatabaseHelper.updateCakeStatus(id);
			// using handler to update the value in list
			Message msg = new Message();
			msg.what = Constants.COOKED;
			handler.sendMessage(msg);
		    }
	    }

	/**
	 * draw all the rowa using the data from datbase
	 */
	private void loadOrders()
	    {
		// initilize the list instannce
		list = myDatabaseHelper.getAllOrders();
		// Log.v("mad", "size in loadorders is" + list.size());
		// if o show empty msg to user
		if (list.size() == 0)
		    {
			title = (TextView) findViewById(R.id.records_title);
			title.setText("empty");
		    }
		else
		    {
			// else draw the list
			adapter = new CakeRecordsAdapter(this, list);
			setListAdapter(adapter);
		    }
	    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	    {
		// inilize the current item clicked by user
		clickedPosition = position;
		// prepare the alert dialog
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(getString(R.string.choice));
		builder.setNegativeButton(getString(R.string.save_as_fav), new OnClickListener()
		    {

			@Override
			public void onClick(DialogInterface dialog, int which)
			    {
				Log.v("mad", "save");
				String _id = list.get(clickedPosition).getId();
				myDatabaseHelper.updateCakeFv(Constants.FV_YES, _id);
			    }
		    });
		builder.setPositiveButton(getString(R.string.delete), new OnClickListener()
		    {

			@Override
			public void onClick(DialogInterface dialog, int which)
			    {
				// delete the representing order in the database
				String _id = list.get(clickedPosition).getId();
				myDatabaseHelper.removeOrder(_id);
				adapter.notifyDataSetChanged();
				loadOrders();
			    }
		    });

		// create and show the dialog
		builder.setCancelable(true);
		builder.create().show();
	    }
    }
