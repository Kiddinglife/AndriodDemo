package com.cupcake.logic;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cupcake.model.Box;
import com.cupcake.model.Cake;
import com.cupcake.model.Candle;
import com.cupcake.util.ViewsContainer;
import com.cupcake.view.Accessories;
import com.example.mad_finalassign.R;

public class CandleAdapter extends BaseAdapter
    {
	private Context context;
	private List<Candle> list;
	private Candle chosenCandle;
	private List<Candle> candleSelected;
	// used to load images and this is interface from assynloadimg component
	private AsynImgLoader asynImgLoader;

	public CandleAdapter(Context context, List<Candle> list, List<Candle> candleSelected)
	    {
		this.context = context;
		this.list = list;
		this.candleSelected = candleSelected;
		asynImgLoader = new AsyncImageLoader();
	    }

	@Override
	public View getView( int position, View convertView, ViewGroup parent)
	    {
		// get the candle instance from the list using position
		Candle row = list.get(position);
		ViewsContainer viewContainer = null;
		final int slectedId = position;
		// check if convert view is null if so, create a new one
		if (convertView == null)
		    {
			convertView = LayoutInflater.from(context).inflate(R.layout.acc_candle_list_item, parent, false);
			// initilize the view container
			viewContainer = new ViewsContainer();
			// set image view using aschro loading
			viewContainer.candlePic = (ImageView) convertView.findViewById(R.id.small_candle_image);
			// set text view
			viewContainer.candleName = (TextView) convertView.findViewById(R.id.candle_name);
			viewContainer.candlePrice = (TextView) convertView.findViewById(R.id.candle_price);
			viewContainer.candleCheck = (CheckBox) convertView.findViewById(R.id.candel_check_box);
			convertView.setTag(viewContainer);
		    }
		else
		    {
			// get view container from the tag
			viewContainer = (ViewsContainer) convertView.getTag();
		    }

		// setup candle name
		viewContainer.candleName.setText(row.getName());
		viewContainer.candlePrice.setText("$" + row.getPrice());
		// setup candle check box listener instance
		//viewContainer.candleCheck.setTag(position);
		viewContainer.candleCheck.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener()
		    {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			    {
				if (isChecked)
				    {
					Toast.makeText(context, slectedId+"", Toast.LENGTH_SHORT).show();
					candleSelected.add(list.get(slectedId));
				    }
				else
				    {
					Toast.makeText(context, slectedId+"", Toast.LENGTH_SHORT).show();
					candleSelected.remove(list.get(slectedId));
				    }
			    }
		    });
		// setup cndle pic
		viewContainer.candlePic.setTag(position);
		asynImgLoader.loadImage(row.getIcon() + ".jpg", viewContainer.candlePic);

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
