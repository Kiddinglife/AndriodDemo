/**
 * this class is used to load image asyc
 */
package com.cupcake.logic;

import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AsyncImageLoader implements AsynImgLoader
    {
	// img cache object
	// key is the img url£¬value is SoftReference object that points to a reference object
	private Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();

	// implement img load
	@SuppressLint("HandlerLeak")
	public Drawable loadDrawable(final String imageUrl, final ImageCallback callback)
	    {
		// check cache to see if the img has been existed in it
		if (imageCache.containsKey(imageUrl))
		    {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			if (softReference.get() != null)
			    {
				return softReference.get();
			    }
		    }

		// handler to serve update the imageview in the front
		final Handler handler = new Handler()
		    {
			@Override
			public void handleMessage(Message msg)
			    {
				callback.imageLoaded((Drawable) msg.obj);
			    }
		    };

		// load img in a new thread
		new Thread()
		    {
			public void run()
			    {
				Drawable drawable = loadImageFromUrl(imageUrl);
				// put new image into the cache
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				// tell handler image has been done and let handler to update the image view
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			    };
		    }.start();
		return null; // this step should not happen
	    }

	// download image using url
	synchronized private Drawable loadImageFromUrl(String imageUrl)
	    {
		try
		    {
			// create a drawale instance using the image stream
			return Drawable.createFromStream(new URL(imageUrl).openStream(), "src");
		    } catch (Exception e)
		    {
			throw new RuntimeException(e);
		    }
	    }

	// callback interface
	public interface ImageCallback
	    {
		public void imageLoaded(Drawable imageDrawable);
	    }

	/**
	 * @param url
	 * : image download url
	 * @param imageView
	 */
	@Override
	public void loadImage(final String url, ImageView imageView)
	    {
		// if there was this image in the cache, the callback method will not be called
		CallbackImpl callbackImpl = new CallbackImpl(imageView);
		Drawable cacheImage = loadDrawable(url, callbackImpl);
		if (cacheImage != null)
		    imageView.setImageDrawable(cacheImage);
	    }

	@Override
	public void loadImageBtn(String url, ImageButton imageBtn)
	    {
		// if there was this image in the cache, the callback method will not be called
		CallbackImpl callbackImpl = new CallbackImpl(imageBtn);
		Drawable cacheImage = loadDrawable(url, callbackImpl);
		if (cacheImage != null)
		    imageBtn.setImageDrawable(cacheImage);
	    }

    }
