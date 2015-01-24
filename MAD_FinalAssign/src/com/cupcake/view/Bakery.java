package com.cupcake.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cupcake.util.ExitAppSafety;
import com.example.mad_finalassign.R;

public class Bakery extends Activity
    {
	@Override
	protected void onCreate(Bundle savedInstanceState)
	    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bakery);
		ExitAppSafety.activities.add(this);
	    }

	public void cakeListBtnClicked(View view)
	    {
		Intent intent = new Intent();
		intent.setClass(Bakery.this, CakeAssortment.class);
		Bakery.this.startActivity(intent);
	    }

	public void cakeRecordsBtnClicked(View view)
	    {
		Log.v("mad", "cr");
		Intent intent = new Intent(this, CakeRecords.class);
		this.startActivity(intent);
	    }

	public void cakeFavBtnClicked(View view)
	    {
		Log.v("mad", "fav cakes clicked");
		Intent intent = new Intent();
		intent.setClass(Bakery.this, FavouriteCakes.class);
		Bakery.this.startActivity(intent);
	    }	
    }
