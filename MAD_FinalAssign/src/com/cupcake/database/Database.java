package com.cupcake.database;

import java.util.ArrayList;

import com.cupcake.model.Cake;
import com.cupcake.model.Fruit;
import com.cupcake.model.Jam;
import com.cupcake.model.Order;
import com.cupcake.model.Topping;
import com.cupcake.util.Constants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Create database and also the SQL queries to get the order
 */
public class Database extends SQLiteOpenHelper
    {
	// this will hold all the orders
	private final static ArrayList<Order> orders = new ArrayList<Order>();
	// this will hold all the favourite cakes
	private final static ArrayList<Cake> cakes = new ArrayList<Cake>();

	public Database(Context context)
	    {
		super(context, Constants.DB_NAME, null, Constants.DATABASE_VERSION);
	    }

	/**
	 * Create the database when the first time the database is created
	 */
	@Override
	public void onCreate(SQLiteDatabase database)
	    {
		try
		    {
			String sql = "create table if not exists " + Constants.TABLE_NAME + "(" + "_id integer primary key autoincrement," + Constants.CAKE_ACC_PRICE + " DOUBLE,"
				+ Constants.CAKE_FOR_NAME + " TEXT," + Constants.CAKE_FOR_NAME_ADDR + " TEXT," + Constants.CAKE_NAME + " TEXT," + Constants.CAKE_PRICE + " DOUBLE,"
				+ Constants.BUY_DATE + " TEXT," + Constants.STATUS + " TEXT," + Constants.FV + " TEXT," + Constants.CAKE_SIZE + " TEXT," + Constants.CAKE_JAM_NAME + " TEXT,"
				+ Constants.CAKE_JAM_PRICE + " TEXT," + Constants.CAKE_FRUIT_NAME + " TEXT," + Constants.CAKE_FRUIT_PRICE + " TEXT," + Constants.CAKE_TOPPING_NAME + " TEXT,"
				+ Constants.CAKE_TOPPING_PRICE + " TEXT," + Constants.CAKE_ICON_URL + " TEXT" + ")";
			Log.v("mad", sql);
			database.execSQL(sql);
		    } catch (Exception e)
		    {
			Log.v("mad", e.getMessage());
		    }
	    }

	/**
	 * Insert a order
	 */
	public void addOrder(Order order)
	    {
		SQLiteDatabase database = getWritableDatabase();
		onCreate(database);
		// use content values to hold all the data that are inserted
		Cake cake = order.getCake();
		ContentValues vals = new ContentValues();
		vals.put(Constants.CAKE_ACC_PRICE, cake.getAcc());
		vals.put(Constants.CAKE_FOR_NAME, order.getName());
		if (order.getAddr() == null)
		    vals.put(Constants.CAKE_FOR_NAME_ADDR, Constants.TAKE_AWAY);
		else
		    vals.put(Constants.CAKE_FOR_NAME_ADDR, order.getAddr());
		vals.put(Constants.CAKE_NAME, cake.getName());
		vals.put(Constants.CAKE_PRICE, cake.getTotalPrice());
		vals.put(Constants.BUY_DATE, order.getDate());
		vals.put(Constants.STATUS, order.getCake().getStatus());
		vals.put(Constants.FV, Constants.FV_NO);
		vals.put(Constants.CAKE_SIZE, order.getCake().getSize());
		vals.put(Constants.CAKE_JAM_NAME, order.getCake().getJam().getName());
		vals.put(Constants.CAKE_JAM_PRICE, order.getCake().getJam().getPrice() + "");
		vals.put(Constants.CAKE_FRUIT_NAME, order.getCake().getJam().getName());
		vals.put(Constants.CAKE_FRUIT_PRICE, order.getCake().getFruit().getPrice() + "");
		vals.put(Constants.CAKE_TOPPING_NAME, order.getCake().getTopping().getName());
		vals.put(Constants.CAKE_TOPPING_PRICE, order.getCake().getTopping().getPrice() + "");
		vals.put(Constants.CAKE_ICON_URL, order.getCake().getIcon());
		Log.v("mad", "content " + vals.toString());
		try
		    {
			// insert into database
			database.insert(Constants.TABLE_NAME, null, vals);
		    } catch (Exception e)
		    {
			Log.v("mad", e.getMessage());
		    }
		database.close();
	    }

	/**
	 * Get all orders
	 * 
	 * @return arraylist of orders
	 */
	public ArrayList<Order> getAllOrders()
	    {
		// clear up the old data stored in the array list
		orders.clear();
		// get dtabase instance
		SQLiteDatabase database = getWritableDatabase();
		// get cursor to get the value using column names
		Cursor cursor = database.query(Constants.TABLE_NAME, null, null, null, null, null, null);
		// Log.v("mad", "cursor"+cursor.toString());
		if (cursor.moveToFirst())
		    {
			do
			    {
				// get all the values
				int _id = cursor.getInt(cursor.getColumnIndex("_id"));
				double accPrice = cursor.getDouble(1);
				String personName = cursor.getString(2);
				String addr = cursor.getString(3);
				String cakeName = cursor.getString(4);
				double cakePrice = cursor.getDouble(5);
				String date = cursor.getString(6);
				String status = cursor.getString(7);
				Cake cake = new Cake();
				cake.setName(cakeName);
				cake.setTotalPrice(cakePrice);
				cake.setAcc(accPrice);
				cake.setStatus(status);
				Order order = new Order(personName, null, addr, null, date, cake);
				order.setId(_id + "");
				// Log.v("mad", "order"+cursor.toString());
				orders.add(order);
			    } while (cursor.moveToNext());
		    }
		cursor.close();
		database.close();
		return orders;
	    }

	/**
	 * Get all favourite cakes
	 * 
	 * @return arraylist of cakes
	 */
	public ArrayList<Cake> getAllFavouriteCakes()
	    {
		// clear up the old data stored in the array list
		cakes.clear();
		// get dtabase instance
		SQLiteDatabase database = getWritableDatabase();
		// get cursor to get the value using column names
		Cursor cursor = database.query(Constants.TABLE_NAME, null, null, null, null, null, null);
		// Log.v("mad", "cursor"+cursor.toString());
		if (cursor.moveToFirst())
		    {
			do
			    {
				String fv = cursor.getString(cursor.getColumnIndex(Constants.FV));
				if (fv.equalsIgnoreCase(Constants.FV_YES))
				    {
					// get all the values
					double accPrice = cursor.getDouble(1);
					String cakeName = cursor.getString(4);
					double cakePrice = cursor.getDouble(5);
					String cSize = cursor.getString(9);
					String cJamName = cursor.getString(10);
					String cJamPrice = cursor.getString(11);
					String cFruitName = cursor.getString(12);
					String cFruitPrice = cursor.getString(13);
					String cToppingName = cursor.getString(14);
					String cToppingPrice = cursor.getString(15);
					String icon = cursor.getString(16);
					// fill in the instance of cake and order
					Cake cake = new Cake();
					cake.setName(cakeName);
					cake.setTotalPrice(cakePrice);
					cake.setAcc(accPrice);
					cake.setSize(cSize);
					cake.setIcon(icon);
					cake.setJam(new Jam(cJamName, Double.parseDouble(cJamPrice), null));
					cake.setFruit(new Fruit(cFruitName, Double.parseDouble(cFruitPrice), null));
					cake.setTopping(new Topping(cToppingName, Double.parseDouble(cToppingPrice), null));
					cakes.add(cake);
				    }
			    } while (cursor.moveToNext());
		    }
		cursor.close();
		database.close();
		return cakes;
	    }

	/**
	 * Delete all orders in the database
	 */
	public void removeAllOrders()
	    {
		orders.clear();
		SQLiteDatabase database = getWritableDatabase();
		database.delete(Constants.TABLE_NAME, null, null);
		database.close();
	    }

	/**
	 * Delete one order
	 */
	public void removeOrder(String id)
	    {
		orders.clear();
		SQLiteDatabase database = getWritableDatabase();
		database.delete(Constants.TABLE_NAME, "_id=?", new String[] { id });
		database.close();
	    }

	/**
	 * update cake status cooked or uncooked
	 */
	public void updateCakeStatus(String id)
	    {
		SQLiteDatabase database = getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(Constants.STATUS, "cooked");
		database.update(Constants.TABLE_NAME, cv, "_id=?", new String[] { id });
		database.close();
		// Log.v("mad", "corrext update status");
	    }

	/**
	 * update cake favourite status fv yes or fv no
	 */
	public void updateCakeFv(String fv, String id)
	    {
		SQLiteDatabase database = getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(Constants.FV, fv);
		database.update(Constants.TABLE_NAME, cv, "_id=?", new String[] { id });
		database.close();
		Log.v("mad", "corrext update fv");
	    }

	/**
	 * Deals with database upgrades
	 */
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	    {
		database.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
		onCreate(database);
	    }
    }
