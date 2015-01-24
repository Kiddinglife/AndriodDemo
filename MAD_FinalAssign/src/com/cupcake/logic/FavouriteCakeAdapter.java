package com.cupcake.logic;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cupcake.model.Cake;
import com.cupcake.model.Order;
import com.cupcake.util.ViewsContainer;
import com.example.mad_finalassign.R;

public class FavouriteCakeAdapter extends BaseAdapter
    {
	private Context context;
	private List<Cake> list;

	public FavouriteCakeAdapter(Context context, List<Cake> list)
	    {
		this.context = context;
		this.list = list;
	    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	    {
		// get the candle instance from the list using position
		Cake row = list.get(position);
		ViewsContainer viewContainer = null;
		// check if convert view is null if so, create a new one
		if (convertView == null)
		    {
			convertView = LayoutInflater.from(context).inflate(R.layout.favourite_cakes_item, parent, false);
			// initilize the view container
			viewContainer = new ViewsContainer();
			// set text view
			viewContainer.fvCakeName = (TextView) convertView.findViewById(R.id.fv_cake_name);
			viewContainer.fvCakeJam = (TextView) convertView.findViewById(R.id.fv_jam_type);
			// viewContainer.cakeStatus = (TextView) convertView.findViewById(R.id.cake_status);
			viewContainer.fvCakeAccPrice = (TextView) convertView.findViewById(R.id.fv_cake_acc_price);
			// viewContainer.dateTitle = (TextView) convertView.findViewById(R.id.date_title);
			viewContainer.fvCakePrice = (TextView) convertView.findViewById(R.id.fv_cake_price);
			viewContainer.fvCakeFruit = (TextView) convertView.findViewById(R.id.fv_fruit_type);
			viewContainer.fvCakeTopping = (TextView) convertView.findViewById(R.id.fv_topping_type);
			viewContainer.fvCakeSzie = (TextView) convertView.findViewById(R.id.fv_cake_size);
			convertView.setTag(viewContainer);
		    }
		else
		    {
			// get view container from the tag
			viewContainer = (ViewsContainer) convertView.getTag();
		    }

		// setup values
		viewContainer.fvCakeName.setText(row.getName());
		viewContainer.fvCakeJam.setText(row.getJam().getName());
		viewContainer.fvCakeAccPrice.setText(row.returnAcc()+"");
		viewContainer.fvCakePrice.setText(row.returnTotalPrice()+"");
		viewContainer.fvCakeFruit.setText(row.getFruit().getName());
		viewContainer.fvCakeTopping.setText(row.getTopping().getName());
		viewContainer.fvCakeSzie.setText(row.getSize());
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
	
	public List<Cake> getList()
	    {
		return list;
	    }
    }
