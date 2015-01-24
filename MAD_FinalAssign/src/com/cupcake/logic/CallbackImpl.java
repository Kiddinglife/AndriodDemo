package com.cupcake.logic;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class CallbackImpl implements AsyncImageLoader.ImageCallback
    {
	//image view to hold image drawable instance
	private ImageView imageView;

	public CallbackImpl(ImageView imageView)
	    {
		super();
		this.imageView = imageView;
	    }

	@Override
	public void imageLoaded(Drawable imageDrawable)
	    {
		//setup image when no cache image 
		imageView.setImageDrawable(imageDrawable);
	    }

    }
