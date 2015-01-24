package com.cupcake.view;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.cupcake.database.Database;
import com.cupcake.model.Box;
import com.cupcake.model.Cake;
import com.cupcake.model.Candle;
import com.cupcake.model.Order;
import com.cupcake.util.Constants;
import com.cupcake.util.ExitAppSafety;
import com.example.mad_finalassign.R;

public class EndPayment extends Activity
    {
	private Order order;
	private Cake cake;
	private TextView name;
	private TextView size;
	private TextView jam;
	private TextView fruit;
	private TextView topping;
	private TextView cakePrice;
	private TextView accPrice;
	private TextView cakeBox;
	private TextView cakeCandle;
	private TableRow boxRow;
	private TableRow candleRow;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_pay);
		ExitAppSafety.activities.add(this);
		initWidgets();
		assignWidgets();
		// String str = "";
		// Log.v("mad", order.toString());
	    }

	private void initWidgets()
	    {
		cakeBox = (TextView) findViewById(R.id.cake_box);
		cakeCandle = (TextView) findViewById(R.id.cake_candle);
		name = (TextView) findViewById(R.id.cname);
		size = (TextView) findViewById(R.id.csize);
		jam = (TextView) findViewById(R.id.cjam);
		fruit = (TextView) findViewById(R.id.cfruit);
		topping = (TextView) findViewById(R.id.ctopping);
		cakePrice = (TextView) findViewById(R.id.cake_price);
		accPrice = (TextView) findViewById(R.id.cake_acc);

		boxRow = (TableRow) findViewById(R.id.cake_box_row);
		candleRow = (TableRow) findViewById(R.id.cake_candle_row);

		order = (Order) getIntent().getSerializableExtra(Constants.ORDER);
		cake = order.getCake();
	    }

	private void assignWidgets()
	    {
		name.setText(cake.getName());
		size.setText(cake.getSize());
		jam.setText(cake.getJam().getName());
		fruit.setText(cake.getFruit().getName());
		topping.setText(cake.getTopping().getName());
		cakePrice.setText(cake.getTotalPrice() + "");
		accPrice.setText(cake.getAcc() + "");
	    }

	public void orderConfirmBtnClicked(View view)
	    {
		Log.v("mad", "con");
		showDialog(Constants.PROGRESS_DIALOG);
		new Thread()
		    {
			@Override
			public void run()
			    {
				try
				    {
					Thread.sleep(2000);
					// store this order in database		
					Database db = new Database(getApplicationContext());
					db.addOrder(order);
					Message msg = new Message();
					msg.what = Constants.CANCEL_PRO_BAR;
					EndPayment.this.handler.sendMessage(msg);
				    } catch (InterruptedException e)
				    {
					// TODO Auto-generated catch block
					e.printStackTrace();
				    }
			    }
		    }.start();

	    }

	protected Dialog onCreateDialog(int id)
	    {
		switch (id)
		    {
		    case Constants.PROGRESS_DIALOG:
			progressDialog = new ProgressDialog(EndPayment.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Loading...");
			return progressDialog;
		    case Constants.SHOW_DIALOG:
			//Log.v("mad", "dialog");
			drawCompletePayDialog();
			break;
		    default:
			break;
		    }
		return null;
	    }

	private void drawCompletePayDialog()
	    {
		AlertDialog.Builder builder = new Builder(EndPayment.this);
		builder.setMessage(getString(R.string.success_pay_msg));

		builder.setTitle("Payment Complete");
		builder.setNegativeButton(getString(R.string.back_bakery), new OnClickListener()
		    {

			@Override
			public void onClick(DialogInterface dialog, int which)
			    {
				dialog.dismiss();
				Intent intent = new Intent();
				intent.setClass(EndPayment.this, Bakery.class);
				startActivity(intent);
				ExitAppSafety.finishAll();
			    }
		    });

		builder.create().show();
	    }

	// Define the Handler that receives messages from the thread and update the progress
	@SuppressLint("HandlerLeak")
	final Handler handler = new Handler()
	    {
		@SuppressWarnings("deprecation")
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg)
		    {
			switch (msg.what)
			    {
			    case Constants.CANCEL_PRO_BAR:
				dismissDialog(Constants.PROGRESS_DIALOG);
				showDialog(Constants.SHOW_DIALOG);
				break;
			    default:
				break;
			    }
		    }
	    };

	public void accClicked(View view)
	    {
		// start acc activity
		Intent intent = new Intent();
		intent.setClass(EndPayment.this, Accessories.class);
		EndPayment.this.startActivityForResult(intent, 0);
	    }

	/**
	 * this is the call back fnction to receive the selected candle and box instances from acc activity also it will receives the updated data from diy activity
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	    {
		switch (resultCode)
		    {
		    case Constants.ACC_UPDATE:
			Bundle b = data.getExtras();
			@SuppressWarnings("unchecked")
			List<Candle> candles = (List<Candle>) b.getSerializable(Constants._CANDLE);
			int candleSize = candles.size();
			if (candleSize > 0)
			    {
				cake.setCandle(candles.get(0));
				String candle_str = "";
				for (Candle candle : candles)
				    {
					candle_str += candle.getName();
				    }
				candleRow.setVisibility(View.VISIBLE);
				cakeCandle.setText(candle_str);
			    }

			@SuppressWarnings("unchecked")
			List<Box> boxes = (List<Box>) b.getSerializable(Constants._BOX);
			int boxSize = boxes.size();
			if (boxSize > 0)
			    {
				cake.setBox(boxes.get(0));
				String box_str = "";
				for (Box box : boxes)
				    {
					box_str += box.getName();
				    }
				boxRow.setVisibility(View.VISIBLE);
				cakeBox.setText(box_str);
			    }
			// update prices
			cakePrice.setText(cake.getTotalPrice() + "");
			accPrice.setText(cake.getAcc() + "");
			findViewById(R.id.diy_no).setVisibility(View.GONE);
			findViewById(R.id.diy_yes).setVisibility(View.VISIBLE);
			// Log.v("mad", "result: "+candles.toString() +"\n" + boxes.toString());
			break;
		    default:
			break;
		    }
	    }
    }
