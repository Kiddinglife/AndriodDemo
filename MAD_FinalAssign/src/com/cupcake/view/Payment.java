package com.cupcake.view;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cupcake.model.Order;
import com.cupcake.util.Constants;
import com.cupcake.util.ExitAppSafety;
import com.example.mad_finalassign.R;

public class Payment extends Activity
    {
	private Order order;
	private EditText validDateText;
	private Button validDateBtn;
	private RadioGroup group;
	private EditText signature;
	private EditText cvv;
	private int mYear;
	private int mMonth;
	private int mDay;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		ExitAppSafety.activities.add(this);
		ini();
		// Log.v("mad", order.toString());

	    }

	private void ini()
	    {
		order = (Order) getIntent().getSerializableExtra(Constants.ORDER);
		validDateText = (EditText) findViewById(R.id.valid_date_value);
		signature = (EditText) findViewById(R.id.card_sig_value);
		cvv = (EditText) findViewById(R.id.cvv_value);
		validDateBtn = (Button) findViewById(R.id.valid_date_button);
		group = (RadioGroup) findViewById(R.id.payment_choice_group);
	    }

	public void dateBtnClicked(View view)
	    {
		Message msg = new Message();
		msg.what = Constants.VALID_DATE;
		Payment.this.dateandtimeHandler.sendMessage(msg);
	    }

	public void confirmBtnClicked(View view)
	    {
		RadioButton cardTypeButton = (RadioButton) findViewById(group.getCheckedRadioButtonId());
		String cardType = cardTypeButton.getText().toString();
		String validDate = validDateText.getText().toString();
		String sig = signature.getText().toString();
		String cvvv = cvv.getText().toString();
		// setup order using values here
		if (isValid(cardType) && isValid(validDate) && isValid(sig) && isValid(cvvv))
		    {
			// sent it to CinfirmationPayment activity
			Intent intent = new Intent();
			intent.setClass(Payment.this, EndPayment.class);
			Bundle mBundle = new Bundle();
			mBundle.putSerializable(Constants.ORDER, order);
			intent.putExtras(mBundle);
			Payment.this.startActivity(intent);
			//Payment.this.finish();
		    }
		else
		    {
			Toast.makeText(Payment.this, "Please fill in the bars !", Toast.LENGTH_SHORT).show();
		    }

		// Log.v("mad", "confir pay");
	    }

	private boolean isValid(String str)
	    {
		if (str == null || str.equals(""))
		    return false;
		return true;
	    }

	@Override
	protected Dialog onCreateDialog(int id)
	    {
		switch (id)
		    {
		    case Constants.DATE_DIALOG_ID:
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(this, mDateSetListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		    }

		return null;
	    }

	/**
	 * date widget event
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
	    {

		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
		    {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDateDisplay();
		    }
	    };

	/**
	 * update date time
	 */
	private void updateDateDisplay()
	    {
		validDateText.setText(new StringBuilder().append(mYear).append("-").append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-").append((mDay < 10) ? "0" + mDay : mDay));
	    }

	/**
	 * the handler to be used handle the date update with UI
	 */
	@SuppressLint("HandlerLeak")
	Handler dateandtimeHandler = new Handler()
	    {

		@SuppressWarnings("deprecation")
		@Override
		public void handleMessage(Message msg)
		    {
			if (msg.what == Constants.VALID_DATE)
			    {
				showDialog(Constants.DATE_DIALOG_ID);
			    }
		    }
	    };
    }
