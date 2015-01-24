package com.cupcake.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.cupcake.database.Database;
import com.cupcake.logic.AsyncImageLoader;
import com.cupcake.logic.CallbackImpl;
import com.cupcake.logic.MyService;
import com.cupcake.model.Cake;
import com.cupcake.model.Order;
import com.cupcake.util.Constants;
import com.example.mad_finalassign.R;

public class test extends Activity
    {
	//private AsyncImageLoader loader = new AsyncImageLoader();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Database db = new Database(getApplicationContext());
		db.addOrder(new Order());
		Log.v("mad", db.getAllOrders().get(0).toString());
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	    {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	    {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	    }
    }
