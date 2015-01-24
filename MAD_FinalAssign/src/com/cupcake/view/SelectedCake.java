package com.cupcake.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cupcake.logic.AsynImgLoader;
import com.cupcake.logic.AsyncImageLoader;
import com.cupcake.model.Cake;
import com.cupcake.util.Constants;
import com.cupcake.util.ExitAppSafety;
import com.example.mad_finalassign.R;

public class SelectedCake extends Activity
    {
	// pojo of cake to hold everything
	private Cake selectedCake;
	// the component to load image
	private AsynImgLoader asynImgLoader;
	// all are the views related to this activity
	private TextView cakeName;
	private TextView cakeJame;
	private TextView cakeFruit;
	private TextView cakeTopping;
	private TextView cakeSize;
	private ImageView cakeImg;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selected_cake);
		ExitAppSafety.activities.add(this);
		init();
	    }

	private void init()
	    {
		asynImgLoader = new AsyncImageLoader();

		// initialize the cake instance using the value sent from cake list via intent
		selectedCake = (Cake) getIntent().getSerializableExtra(Constants.SelectedCake);

		// setup cake
		cakeName = (TextView) findViewById(R.id.cake_name_value);
		cakeName.setText(selectedCake.getName());
		cakeFruit = (TextView) findViewById(R.id.cake_fruit_value);
		cakeFruit.setText(selectedCake.getFruit().getName());
		cakeTopping = (TextView) findViewById(R.id.cake_topping_value);
		cakeTopping.setText(selectedCake.getTopping().getName());
		cakeJame = (TextView) findViewById(R.id.cake_jam_value);
		cakeJame.setText(selectedCake.getJam().getName());
		cakeSize = (TextView) findViewById(R.id.cake_size_value);
		cakeSize.setText(selectedCake.getSize());
		cakeImg = (ImageView) findViewById(R.id.image);
		asynImgLoader.loadImage(selectedCake.getIcon() + ".jpg", cakeImg);

		setTitle(selectedCake.getName());
		// Log.v("mad", cake.toString());
	    }

	public void buyCakeButtClicked(View view)
	    {
		// go to deliveryway activity
		Intent intent = new Intent();
		intent.setClass(SelectedCake.this, DeliveryWay.class);
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(Constants.SelectedCake, selectedCake);
		intent.putExtras(mBundle);
		SelectedCake.this.startActivity(intent);
		//SelectedCake.this.finish();
	    }

	public void diyCakeButtClicked(View view)
	    {
		//Log.v("mad", "diy button clucked");
		// start acc activity
		Intent intent = new Intent();
		intent.setClass(this, DiyCake.class);
		this.startActivityForResult(intent, 0);
	    }

	/**
	 * this is the call back fnction to receive the updated data from diy activity
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	    {
		switch (resultCode)
		    {
		    case Constants.DIY_UPDATE:
			//update old values using new values 
			//and also update the instance values in selected cake instance
			Bundle b = data.getExtras();
			String csize = b.getString(Constants.CSIZE);
			cakeSize.setText(csize);
			selectedCake.setSize(csize);
			String cjam = b.getString(Constants.CJAM);
			cakeJame.setText(cjam);
			selectedCake.getJam().setName(cjam);
			String cfruit = b.getString(Constants.CFRUIT);
			cakeFruit.setText(cfruit);
			selectedCake.getFruit().setName(cfruit);
			String ctopping = b.getString(Constants.CTOPPING);
			cakeTopping.setText(ctopping);
			selectedCake.getTopping().setName(ctopping);
			break;
		    default:
			Log.v("mad", "error");
			break;
		    }
	    }
    }
