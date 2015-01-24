package com.cupcake.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.cupcake.logic.AsynImgLoader;
import com.cupcake.logic.AsyncImageLoader;
import com.cupcake.logic.BoxAdapter;
import com.cupcake.logic.CandleAdapter;
import com.cupcake.logic.MyService;
import com.cupcake.model.Box;
import com.cupcake.model.Candle;
import com.cupcake.model.WareHouse;
import com.cupcake.util.Constants;
import com.example.mad_finalassign.R;

public class Accessories extends Activity
    {
	private ListView candles;
	private ListView boxes;
	private List<Candle> candleList;
	private List<Box> boxList;
	private WareHouse wareHouse;
	private AsynImgLoader asynImgLoader;
	private int currentList;
	private String currentUrl;
	public List<Candle> candleSelected = new ArrayList<Candle>();
	public List<Box> boxSelected = new ArrayList<Box>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acc);
		init();
	    }

	@Override
	protected void onDestroy()
	    {
		super.onDestroy();
		// this must be done to avloid exception
		unregisterReceiver(getWareHouse);
	    }

	/**
	 * this function is used when user press back button on the phone
	 * the intent can be also sent
	 */
	@Override
	public void onBackPressed()
	    {
		mySendResult();
		finish();
		super.onBackPressed();
	    }

	private void init()
	    {
		// stick = (CheckBox) findViewById(R.id.stick);
		// stick.setOnCheckedChangeListener(stickListener);
		candles = (ListView) findViewById(R.id.candle_list);
		boxes = (ListView) findViewById(R.id.box_list);
		asynImgLoader = new AsyncImageLoader();
		// register broadcast receiver to receive the WareHouse instanvce from MyService
		IntentFilter downlaodActionFilter = new IntentFilter(Constants.BROADCAST_GET_WARE_HOUSE);
		registerReceiver(getWareHouse, downlaodActionFilter);
		// send bordcast to get warehouse
		Intent intent = new Intent();
		intent.putExtra(getString(R.string.msg), Constants.GIVE_ME_WAREHOUSE);
		intent.setClass(Accessories.this, MyService.class);
		Accessories.this.startService(intent);
	    }

	// register receiver instance
	private BroadcastReceiver getWareHouse = new BroadcastReceiver()
	    {
		@Override
		public void onReceive(Context context, Intent intent)
		    {
			// take out the wareHouse instance in the intent bundle and inilize the wareHouse instance
			wareHouse = (WareHouse) intent.getSerializableExtra(Constants.BROADCAST_INTENT_KEY);
			// Log.v("mad", wareHouse.getCandles().toString());
		    }
	    };

	/**
	 * this function is used to create dialog
	 */
	protected Dialog onCreateDialog(int id)
	    {
		switch (id)
		    {
		    case Constants.IMG_DIALOG:
			Dialog dialog = new Dialog(this);
			// setup the layout file
			dialog.setContentView(R.layout.acc_img_dialog);
			dialog.setTitle("Image Viewer");
			// this is used to cancel the dialog
			dialog.setCanceledOnTouchOutside(true);
			dialog.setCancelable(true);
			// load image on dialog
			ImageView imageView = (ImageView) dialog.findViewById(R.id.dialog_img);
			asynImgLoader.loadImage(currentUrl, imageView);
			return dialog;
		    default:
			return null;
		    }
	    }

	/**
	 * this function will rect when user clicks the image and the bigger size of image will be shown
	 * 
	 * @param v
	 */
	public void imgBtnClicked(View v)
	    {
		// get the position of the clicked row
		v = (ImageView) v;
		int position = (Integer) v.getTag();
		switch (currentList)
		    {
		    case Constants.CANDLE_LIST:
			// Log.v("mad", "candle visiable");
			currentUrl = candleList.get(position).getIcon() + ".jpg";
			Log.v("mad", currentUrl);
			removeDialog(Constants.IMG_DIALOG);
			showDialog(Constants.IMG_DIALOG);
			break;
		    case Constants.BOX_LIST:
			// Log.v("mad", "box visiable");
			currentUrl = boxList.get(position).getIcon() + ".jpg";
			Log.v("mad", currentUrl);
			removeDialog(Constants.IMG_DIALOG);
			showDialog(Constants.IMG_DIALOG);
			break;

		    }
	    }

	/**
	 * this function will show and hide the candle list and box list the list will be hiding when visiable and become visiable when hided
	 * 
	 * @param view
	 */
	public void BtnClicked(View view)
	    {
		switch (view.getId())
		    {
		    case R.id.candle_btn:
			// Log.v("mad", "candle_btn");
			switch (candles.getVisibility())
			    {
			    case View.GONE:
				view.setBackgroundResource(R.drawable.min_btn);
				candles.setVisibility(View.VISIBLE);
				boxes.setVisibility(View.GONE);
				currentList = Constants.CANDLE_LIST;
				drawCandleList();
				break;
			    case View.VISIBLE:
				view.setBackgroundResource(R.drawable.add_btn);
				candles.setVisibility(View.GONE);
				break;
			    }
			break;
		    case R.id.box_btn:
			// Log.v("mad", "box_btn");
			switch (boxes.getVisibility())
			    {
			    case View.GONE:
				view.setBackgroundResource(R.drawable.min_btn);
				boxes.setVisibility(View.VISIBLE);
				candles.setVisibility(View.GONE);
				currentList = Constants.BOX_LIST;
				drawBoxList();
				break;
			    case View.VISIBLE:
				view.setBackgroundResource(R.drawable.add_btn);
				boxes.setVisibility(View.GONE);
				break;
			    }
			break;
		    }
	    }

	/**
	 * this function will draw the candle list
	 */
	private void drawCandleList()
	    {
		// set a new list
		candleList = new ArrayList<Candle>();
		// loop the candle map and fill in the empty list
		Map<String, Candle> map = wareHouse.getCandles();
		Collection<Candle> c = map.values();
		for (Iterator<Candle> it = c.iterator(); it.hasNext();)
		    {
			candleList.add(it.next());
		    }
		// Log.v("mad", "list cake size is" + list.size());
		CandleAdapter candleAdapter = new CandleAdapter(this, candleList, candleSelected);
		candles.setAdapter(candleAdapter);
	    }

	/**
	 * this function will draw the box list
	 */
	private void drawBoxList()
	    {
		// set a new list
		boxList = new ArrayList<Box>();
		// loop the cake map and fill in the empty list
		Map<String, Box> map = wareHouse.getBoxes();
		Collection<Box> c = map.values();
		for (Iterator<Box> it = c.iterator(); it.hasNext();)
		    {
			boxList.add(it.next());
		    }
		// Log.v("mad", "list cake size is" + list.size());
		BoxAdapter boxAdapter = new BoxAdapter(this, boxList, boxSelected);
		boxes.setAdapter(boxAdapter);
	    }

	public void backBtnClicked(View view)
	    {
		// Log.v("mad", candleSelected.toString() + candleSelected.toString());
		mySendResult();
		finish();
	    }

	private void mySendResult()
	    {
		Intent intent = new Intent();
		intent.setClass(Accessories.this, EndPayment.class);
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(Constants._CANDLE, (Serializable) candleSelected);
		mBundle.putSerializable(Constants._BOX, (Serializable) boxSelected);
		intent.putExtras(mBundle);
		Accessories.this.setResult(Constants.ACC_UPDATE, intent);
	    }
    }
