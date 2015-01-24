package com.cupcake.view;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cupcake.util.Constants;
import com.example.mad_finalassign.R;

public class DiyCake extends Activity
    {
	private Spinner csize;
	private Spinner cjam;
	private Spinner cfruit;
	private Spinner ctopping;
	// hold the selected values
	private String csizeVal = "none";
	private String cjamVal = "none";
	private String cfruitVal = "none";
	private String ctoppingVal = "none";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diy);
		// init all the spinners and their listeners
		initWidgets();
	    }

	/**
	 * this function is used when user press back button on the phone the intent can be also sent
	 */
	@Override
	public void onBackPressed()
	    {
		mySendResult();
		super.onBackPressed();
	    }

	private void initWidgets()
	    {
		csize = (Spinner) findViewById(R.id.csize_spinner);
		initSpinner(csize, R.array.csize_arr, new CsizeSelectedListener());
		cjam = (Spinner) findViewById(R.id.cjam_spinner);
		initSpinner(cjam, R.array.cjam_arr, new CjamSelectedListener());
		cfruit = (Spinner) findViewById(R.id.cfruit_spinner);
		initSpinner(cfruit, R.array.cfruit_arr, new CfruitSelectedListener());
		ctopping = (Spinner) findViewById(R.id.ctopping_spinner);
		initSpinner(ctopping, R.array.ctopping_arr, new CtoppingSelectedListener());
	    }

	private void initSpinner(Spinner spin, int arr_xml, OnItemSelectedListener MyOnItemSelectedListener)
	    {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arr_xml, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// setup adapter
		spin.setAdapter(adapter);
		// setup listenner for each spinner
		spin.setOnItemSelectedListener(MyOnItemSelectedListener);
	    }

	public class CsizeSelectedListener implements OnItemSelectedListener
	    {
		@Override
		public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3)
		    {
			csizeVal = parent.getItemAtPosition(pos).toString();
		    }

		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		    {
			// nothing to do
		    }
	    }

	public class CjamSelectedListener implements OnItemSelectedListener
	    {
		@Override
		public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3)
		    {
			cjamVal = parent.getItemAtPosition(pos).toString();
		    }

		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		    {
			// nothing to do
		    }
	    }

	public class CfruitSelectedListener implements OnItemSelectedListener
	    {
		@Override
		public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3)
		    {
			cfruitVal = parent.getItemAtPosition(pos).toString();
		    }

		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		    {
			// nothing to do
		    }
	    }

	public class CtoppingSelectedListener implements OnItemSelectedListener
	    {
		@Override
		public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3)
		    {
			ctoppingVal = parent.getItemAtPosition(pos).toString();
		    }

		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		    {
			// nothing to do
		    }
	    }
	
	private void mySendResult()
	    {
		Intent intent = new Intent();
		intent.setClass(this, SelectedCake.class);
		Bundle mBundle = new Bundle();
		mBundle.putString(Constants.CSIZE, csizeVal);
		mBundle.putString(Constants.CJAM, cjamVal);
		mBundle.putString(Constants.CFRUIT, cfruitVal);
		mBundle.putString(Constants.CTOPPING, ctoppingVal);
		intent.putExtras(mBundle);
		this.setResult(Constants.DIY_UPDATE, intent);
		finish();
	    }
    }
