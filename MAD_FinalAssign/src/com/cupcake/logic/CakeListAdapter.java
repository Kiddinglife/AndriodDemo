package com.cupcake.logic;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cupcake.model.Cake;
import com.cupcake.util.ViewsContainer;
import com.example.mad_finalassign.R;

public class CakeListAdapter extends BaseAdapter
    {
	private Context context;
	private List<Cake> list;
	// used to load images and this is interface from assynloadimg component
	private AsynImgLoader asynImgLoader;

	public CakeListAdapter(Context context, List<Cake> list)
	    {
		this.context = context;
		this.list = list;
		asynImgLoader = new AsyncImageLoader();
	    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	    {
		// get the cake instance from the list using position
		Cake row = list.get(position);	
		ViewsContainer viewContainer = null;
		// check if convert view is null if so, create a new one
		if (convertView == null)
		    {
			convertView = LayoutInflater.from(context).inflate(R.layout.cake_grid_item, parent, false);
			//initilize the view container
			viewContainer = new ViewsContainer();
			// set image view using aschro loading
			viewContainer.cakePic = (ImageView) convertView.findViewById(R.id.image);
			// set text view
			viewContainer.cakeName = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(viewContainer);
		    }
		else
		    {
			//get view container from the tag
			viewContainer = (ViewsContainer) convertView.getTag();
		    }
		
		//setup cake name
		viewContainer.cakeName.setText(row.getName());
		//setup cake pic
		asynImgLoader.loadImage(row.getIcon() + ".jpg", viewContainer.cakePic);
		
		return convertView;
	    }

	/**
	 * @param url
	 * : image download url
	 * @param imageView
	 */
	/*
	 * private void loadImage(final String url, ImageView imageView) { // if there was this image in the cache, the callback method will not be called CallbackImpl callbackImpl = new CallbackImpl(imageView); Drawable cacheImage = asyncImageLoader.loadDrawable(url, callbackImpl); if (cacheImage
	 * != null) imageView.setImageDrawable(cacheImage); }
	 */

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
