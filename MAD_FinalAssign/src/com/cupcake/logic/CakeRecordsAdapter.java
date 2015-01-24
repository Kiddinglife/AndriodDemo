package com.cupcake.logic;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cupcake.model.Order;
import com.cupcake.util.ViewsContainer;
import com.example.mad_finalassign.R;

public class CakeRecordsAdapter extends BaseAdapter
    {
	private Context context;
	private List<Order> list;

	public CakeRecordsAdapter(Context context, List<Order> list)
	    {
		this.context = context;
		this.list = list;
	    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	    {
		// get the candle instance from the list using position
		Order row = list.get(position);
		ViewsContainer viewContainer = null;
		// check if convert view is null if so, create a new one
		if (convertView == null)
		    {
			convertView = LayoutInflater.from(context).inflate(R.layout.cake_records_item, parent, false);
			// initilize the view container
			viewContainer = new ViewsContainer();
			// set text view
			viewContainer.orderedCakeName = (TextView) convertView.findViewById(R.id.cake_name);
			viewContainer.orderedCakePrice = (TextView) convertView.findViewById(R.id.cake_total_price);
			// viewContainer.cakeStatus = (TextView) convertView.findViewById(R.id.cake_status);
			viewContainer.date = (TextView) convertView.findViewById(R.id.date);
			// viewContainer.dateTitle = (TextView) convertView.findViewById(R.id.date_title);
			viewContainer.addr = (TextView) convertView.findViewById(R.id.to_who_addr);
			viewContainer.accPrice = (TextView) convertView.findViewById(R.id.cake_acc_price);
			viewContainer.cakeStatus = (TextView) convertView.findViewById(R.id.cake_status);
			convertView.setTag(viewContainer);
		    }
		else
		    {
			// get view container from the tag
			viewContainer = (ViewsContainer) convertView.getTag();
		    }

		// setup values
		viewContainer.orderedCakeName.setText(row.getCake().getName());
		viewContainer.orderedCakePrice.setText(row.getCake().returnTotalPrice() + "");
		viewContainer.date.setText(row.getDate());
		viewContainer.addr.setText(row.getAddr());
		viewContainer.accPrice.setText(Double.toString( row.getCake().returnAcc()) );
		viewContainer.cakeStatus.setText(row.getCake().getStatus());
		return convertView;
	    }

	@Override
	public int getCount()
	    {
		return list.size();
	    }

	@Override
	public Object getItem(int location)
	    {
		return list.get(location);
	    }

	@Override
	public long getItemId(int location)
	    {
		return location;
	    }
    }
