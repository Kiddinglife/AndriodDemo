/*
 * this class will hold all the constants used in this projects
 * */
package com.cupcake.util;

import java.util.UUID;

import android.os.ParcelUuid;

public interface Constants
    {
	// all are used when downloading the cakes.xml form remote server
	public static final int DOWNLOAD_AND_PARSE_XML = 1;
	public static final int GIVE_ME_WAREHOUSE = 2;

	// all are attribute value in cakes.xml which will help the parsing
	public static final int ID = 1;
	public static final int NAME = 2;
	public static final int PRICE = 3;
	public static final int ICON = 4;
	public static final int SIZE = 5;
	public static final int BOTTOMPRICE = 6;
	public static final int JAM_ID = 7;
	public static final int FRUIT_ID = 8;
	public static final int TOPPING_ID = 9;
	public static final int CANDLE_ID = 10;
	public static final int BOX_ID = 11;
	public static final int CAKE = 12;
	public static final int JAM = 13;
	public static final int FRUIT = 14;
	public static final int TOPPING = 15;
	public static final int CANDLE = 16;
	public static final int BOX = 17;

	// all are about broadcast
	public static final String BROADCAST_GET_WARE_HOUSE = "1";
	public static final String BROADCAST_INTENT_KEY = "2";
	public static final String GET_ACC = "GET_ACC";
	public static final String BROADCAST_GET_ACC = "BROADCAST_GET_ACC";

	// all about selected cake
	public static final String SelectedCake = "3";

	// all about payment and deliveryway activity
	public static final String ORDER = "4";
	public static final int VALID_DATE = 18;
	public static final int DATE_DIALOG_ID = 19;

	// progress bar id in bakery activity
	public static final int PROGRESS_DIALOG = 20;
	public final static int STATE_DONE = 21;
	public final static int STATE_RUNNING = 22;

	// endpayment activity
	public final static int CANCEL_PRO_BAR = 23;
	public final static int SHOW_DIALOG = 24;

	// order column names
	public static final String TABLE_NAME = "mytable";
	public static final String DB_NAME = "mydb.db";
	public final static int DATABASE_VERSION = 25;
	public static final String CAKE_NAME = "CAKE_NAME";
	public static final String CAKE_ICON_URL = "CAKE_ICON_URL";
	public static final String CAKE_SIZE = "CAKE_SIZE";
	public static final String CAKE_FRUIT_NAME = "CAKE_FRUIT_NAME";
	public static final String CAKE_FRUIT_PRICE = "CAKE_FRUIT_PRICE";
	public static final String CAKE_JAM_NAME = "CAKE_JAM_NAME";
	public static final String CAKE_JAM_PRICE = "CAKE_JAM_PRICE";
	public static final String CAKE_TOPPING_NAME = "CAKE_TOPPING_NAME";
	public static final String CAKE_TOPPING_PRICE = "CAKE_TOPPING_PRICE";
	public static final String CAKE_PRICE = "CAKE_PRICE";
	public static final String CAKE_ACC_PRICE = "CAKE_ACC_PRICE";
	public static final String CAKE_FOR_NAME = "CAKE_FOR_NAME";
	public static final String BUY_DATE = "BUY_DATE";
	public static final String CAKE_FOR_NAME_ADDR = "CAKE_FOR_NAME_ADDR";
	public static final String TAKE_AWAY = "take away";
	public static final String FV = "FV";
	public static final String FV_YES = "FV_YES";
	public static final String FV_NO = "FV_NO";

	// acc activity constants
	public static final int CANDLE_LIST = 26;
	public static final int BOX_LIST = 27;
	public static final int IMG_DIALOG = 28;
	public static final String _CANDLE = "CANDLE";
	public static final String _BOX = "BOX";
	public static final int ACC_UPDATE = 29;

	// diy cake activity
	public static final int DIY_UPDATE = 30;
	public static final String CSIZE = "CSIZE";
	public static final String CJAM = "CJAM";
	public static final String CFRUIT = "CFRUIT";
	public static final String CTOPPING = "CTOPPING";
	public static final String DIY = "DIY";

	// CAKE RECORDS ACTIVITY
	public static final int COOKED = 31;
	public static final int ONE_SECOND = 1000;
	public static final int LISTENNING_CAKE_COOKED = 32;
	public static final String STATUS = "status";
	public static final int UPDATE_VIEW = 33;

	// CONSTANTS USED IN BLUTOOTH
	// Constants that indicate the current connection state
	public static final int NONE = 34; // initial state
	public static final int LISTENNING = 35; // the app acts as server, listening for incoming connections
	public static final int CONNECTING = 36; // the app acts as client, trying to connect to the server app
	public static final int CONNECTED = 37; // there has been a connected socket built already
	public static final int MSG_CURRENT_STATE = 38;
	public static final int MSG_DEVICE_NAME = 39;
	public static final int RECEIVED_DATA = 40;
	public static final int SENDING_DATA = 41;
	public static final String DEVICE_NAME = "DEVICE_NAME";
	public static final String DEVICE_MAC_ADDR = "DEVICE_MAC_ADDR";
	public static final int REQUEST_CONNECT_DEVICE = 42;
	public static final int REQUEST_ENABLE_BLUETOOTH = 43;
	public static final int RETURNED_RESULT_GOOD = 44;
	// Name for the SDP record when creating server socket
	public static final String MY_BLUETOOTH_SERVER = "MY_BLUETOOTH_SERVER";
	// Unique UUID for this application
	public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	public static final UUID MY_UUID_2 = ParcelUuid.fromString("0000112f-0000-1000-8000-00805f9b34fb").getUuid();
	public static final int CONNECTED_DEVICE_NAME = 45;
    }