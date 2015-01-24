package com.cupcake.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;

/**
 * PETER DONT DO IT LIKE THIS!!!
 * 
 * Where did you copy this from? Bad example!!
 * 
 * @author peter
 * 
 */
public class ExitAppSafety extends Application
    {

	public static List<Activity> activities = new ArrayList<Activity>();

	public static void finishAll()
	    {
		for (Activity activity : activities)
		    {
			activity.finish();
		    }
	    }
    }
