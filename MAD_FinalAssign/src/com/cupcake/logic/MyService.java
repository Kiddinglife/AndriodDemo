/**
 * this service will handles the http downloading, notification of the finish of cake making.
 */
package com.cupcake.logic;

import java.io.StringReader;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.cupcake.model.WareHouse;
import com.cupcake.util.Constants;
import com.cupcake.util.HttpDownloader;
import com.example.mad_finalassign.R;

public class MyService extends Service
    {
	// this member will hold all the cached data about the cakes.xml file
	private WareHouse wareHouse = null;

	@Override
	public IBinder onBind(Intent intent)
	    {
		return null;
	    }

	@Override
	public void onCreate()
	    {
		super.onCreate();
		// Log.v("mad", "service---onCreate");
		// initialize the http ware house object
		if (wareHouse == null)
		    wareHouse = new WareHouse();
	    }

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	    {
		// Log.v("mad", "service---onStartComm");
		// get the msg sent from calling activity in the app to
		// determine which action to implement
		int msg = intent.getIntExtra(getString(R.string.msg), -1);
		switch (msg)
		    {
		    // download the cakes.xml from server
		    case Constants.DOWNLOAD_AND_PARSE_XML:
			{
			    // Log.v("mad", "receiveed!");
			    // downloading with a new thread to avoid main thread crashes
			    new Thread()
				{
				    public void run()
					{
					    try
						{
						    // get the cakes.xml in the form of string
						    String xmlStr = new HttpDownloader().downloadTextFile(getString(R.string.url));
						    // parse the cakes.xml
						    wareHouse = pareXml(xmlStr);
						    // Log.v("mad", "JAM SIZE is" + wareHouse.getJams().size() + "topping size is " + wareHouse.getToppings().size());
						} catch (Exception e)
						{
						    // catch block
						    e.printStackTrace();
						}
					}
				}.start();
			    break;	
			}

		    // send the warehouse object to the calling activity
		    case Constants.GIVE_ME_WAREHOUSE:
			{
			    // Log.v("mad", "give warehouse");
			    while (wareHouse.isEmpty())
				continue;
			    boardcastWareHouse();
			    break;
			}
		    case -1:
			Log.v("mad","error");
			break;
		    }
		return super.onStartCommand(intent, flags, startId);
	    }

	@Override
	public void onDestroy()
	    {
		super.onDestroy();
	    }

	/**
	 * this function will be used to parse the cakes.xml it will receive ware house as global member and fill it in with the info in cakes.xml
	 */
	private WareHouse pareXml(String resultStr)
	    {
		// inilize the xmlparser object using warehouse as
		// value-argument
		XmlParser parser = new XmlParser();
		try
		    {
			// create SAXParserFactory
			SAXParserFactory factory = SAXParserFactory.newInstance();
			XMLReader reader = factory.newSAXParser().getXMLReader();
			// setuo container
			reader.setContentHandler(parser);
			// parse the xml file as string
			reader.parse(new InputSource(new StringReader(resultStr)));
		    } catch (Exception e)
		    {
			e.printStackTrace();
		    }
		return parser.getWareHouse();
	    }

	/**
	 * this function will be called once the download of cakes.xml from server is done
	 */
	private void boardcastWareHouse()
	    {
		Intent mIntent = new Intent();
		mIntent.setAction(Constants.BROADCAST_GET_WARE_HOUSE);
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(Constants.BROADCAST_INTENT_KEY, wareHouse);
		mIntent.putExtras(mBundle);
		MyService.this.sendBroadcast(mIntent);
	    }
    }
