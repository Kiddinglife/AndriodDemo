package com.cupcake.logic;

import android.widget.ImageButton;
import android.widget.ImageView;

public interface AsynImgLoader
    {
	public void loadImage(final String url, ImageView imageView);
	public void loadImageBtn(final String url, ImageButton imageBtn);
    }
