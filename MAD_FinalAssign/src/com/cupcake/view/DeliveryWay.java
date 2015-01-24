package com.cupcake.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import com.cupcake.model.Cake;
import com.cupcake.model.Order;
import com.cupcake.util.Constants;
import com.cupcake.util.ExitAppSafety;
import com.example.mad_finalassign.R;

public class DeliveryWay extends Activity
    {
	private RadioGroup group;
	private EditText addr;
	private EditText name;
	private EditText pcode;
	private EditText phoneNum;
	private TableLayout form;
	private Order order;
	private Cake selectedCake;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delivery_way);
		ExitAppSafety.activities.add(this);
		// initilize cale instance
		selectedCake = (Cake) getIntent().getSerializableExtra(Constants.SelectedCake);
		// initialize all the widgets instances in this activity
		initWidgets();

		// setup listener for radio group
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		    {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			    {
				switch (checkedId)
				    {
				    case R.id.take_away_rbtn:
					form.setVisibility(View.GONE);
					cleanUp();
					// Log.v("mad", "takeway clicked");
					break;
				    case R.id.ship_rbtn:
					form.setVisibility(View.VISIBLE);
					// Log.v("mad", "takeway clicked");
					break;
				    default:
					break;
				    }
			    }
		    });
	    }

	private void initWidgets()
	    {
		group = (RadioGroup) findViewById(R.id.delivery_choice_group);
		addr = (EditText) findViewById(R.id.addr_value);
		name = (EditText) findViewById(R.id.receiver_name_value);
		pcode = (EditText) findViewById(R.id.pcode_value);
		phoneNum = (EditText) findViewById(R.id.contact_num_value);
		form = (TableLayout) findViewById(R.id.delivery_form);
	    }

	private void cleanUp()
	    {
		addr.setText(null);
		name.setText(null);
		pcode.setText(null);
		phoneNum.setText(null);
	    }

	private boolean isValidInputs()
	    {
		// Log.v("mad", "addr " + addr.getText());
		if (addr.getText().toString().equals("") || addr.getText().toString() == null)
		    return false;

		if (name.getText().toString().equals("") || name.getText().toString() == null)
		    return false;

		if (pcode.getText().toString().equals("") || pcode.getText().toString() == null)
		    return false;

		if (phoneNum.getText().toString().equals("") || phoneNum.getText().toString() == null)
		    return false;

		return true;
	    }

	@SuppressLint("SimpleDateFormat")
	public void payNoWbuttonClicked(View view)
	    {
		// Log.v("mad", "clicked");
		// setup time formate
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(new Date());
		// Log.v("mad", date);
		if (group.getCheckedRadioButtonId() == R.id.ship_rbtn)
		    {
			if (isValidInputs())
			    {
				// Log.v("mad", "true");
				order = new Order(name.getText().toString(), phoneNum.getText().toString(), addr.getText().toString(), pcode.getText().toString(), date, selectedCake);
			    }
			else
			    {
				Toast.makeText(DeliveryWay.this, "Please fill in empty bars !", Toast.LENGTH_SHORT).show();
			    }
		    }
		else
		    {
			order = new Order(null, null, null, null, date, selectedCake);
		    }
		//start new activity
		Intent intent = new Intent();
		intent.setClass(DeliveryWay.this, Payment.class);
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(Constants.ORDER, order);
		intent.putExtras(mBundle);
		this.startActivity(intent);
		//this.finish();
	    }
    }
