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
import com.example.mad_finalassign.R;

public class BoxAdapter extends BaseAdapter
    {
	private Context context;
	private List<Box> list;
	//use to record selected ids
	private List<Box> boxSelected;
	// used to load images and this is interface from assynloadimg component
	private AsynImgLoader asynImgLoader;

	public BoxAdapter(Context context, List<Box> list, List<Box> boxSelectedIds)
	    {
		this.context = context;
		this.list = list;
		this.boxSelected = boxSelectedIds;
		asynImgLoader = new AsyncImageLoader();
	    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	    {
		// get the candle instance from the list using position
		Box row = list.get(position);
		ViewsContainer viewContainer = null;
		final int slectedId = position;
		// check if convert view is null if so, create a new one
		if (convertView == null)
		    {
			convertView = LayoutInflater.from(context).inflate(R.layout.acc_box_list_item, parent, false);
			// initilize the view container
			viewContainer = new ViewsContainer();
			// set image view using aschro loading
			viewContainer.boxPic = (ImageView) convertView.findViewById(R.id.small_box_image);
			// set text view
			viewContainer.boxName = (TextView) convertView.findViewById(R.id.box_name);
			viewContainer.boxPrice = (TextView) convertView.findViewById(R.id.box_price);
			viewContainer.boxCheck = (CheckBox) convertView.findViewById(R.id.box_check_box);
			convertView.setTag(viewContainer);
		    }
		else
		    {
			// get view container from the tag
			viewContainer = (ViewsContainer) convertView.getTag();
		    }

		// setup box name
		viewContainer.boxName.setText(row.getName());
		viewContainer.boxPrice.setText("$" + row.getPrice());
		// setup candle check box listener instance
		viewContainer.boxCheck.setTag(position);
		viewContainer.boxCheck.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener()
		    {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			    {
				if (isChecked)
				    {
					Toast.makeText(context, "Selected !", Toast.LENGTH_SHORT).show();
					boxSelected.add(list.get(slectedId));
				    }
				else
				    {
					Toast.makeText(context, "Unselected !", Toast.LENGTH_SHORT).show();
					boxSelected.remove(list.get(slectedId));
				    }
			    }
		    });
		// setup box pic
		viewContainer.boxPic.setTag(position);
		asynImgLoader.loadImage(row.getIcon() + ".jpg", viewContainer.boxPic);
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
