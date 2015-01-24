/**
 * this class will parse the xml and store the result in the list
 */
package com.cupcake.logic;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.cupcake.model.Box;
import com.cupcake.model.Cake;
import com.cupcake.model.Candle;
import com.cupcake.model.Fruit;
import com.cupcake.model.Jam;
import com.cupcake.model.Topping;
import com.cupcake.model.WareHouse;
import com.cupcake.util.Constants;

public class XmlParser extends DefaultHandler
    {
	// tempory instances to hold the current beans
	private Cake cake;
	private Candle candle;
	private Fruit fruit;
	private Jam jam;
	private Topping topping;
	private Box box;
	private WareHouse wareHouse;

	// hold the current attribute tag
	private int attrName = 0;
	// hold the current bean tag
	private int beanName = 0;

	/**
	 * @return return the warehouse object with all the info filled in using the xml file
	 */
	public WareHouse getWareHouse()
	    {
		return wareHouse;
	    }

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException
	    {
		super.characters(ch, start, length);
		String str = new String(ch, start, length);
		
		// firstly calculating based on current bean tag
		switch (beanName)
		    {
		    case Constants.JAM:
			// then assig the value to the paticular member in that object based on attribute tags
			switch (attrName)
			    {
			    case Constants.ID:
				jam.setId(str);
				attrName = 0;
				//Log.v("mad", jam.getId());
				break;
			    case Constants.NAME:
				jam.setName(str);
				attrName = 0;
				//Log.v("mad", jam.getName());
				break;
			    case Constants.PRICE:
				jam.setPrice(Double.parseDouble(str));
				attrName = 0;
				//Log.v("mad", jam.getPrice() + "");
				break;
			    }
			break;

		    case Constants.TOPPING:
			switch (attrName)
			    {
			    case Constants.ID:
				topping.setId(str);
				attrName = 0;
				//Log.v("mad", topping.getId());
				break;
			    case Constants.NAME:
				topping.setName(str);
				attrName = 0;
				//Log.v("mad", topping.getName());
				break;
			    case Constants.PRICE:
				topping.setPrice(Double.parseDouble(str));
				attrName = 0;
				//Log.v("mad", topping.getPrice() + "");
				break;
			    }
			break;

		    case Constants.FRUIT:
			switch (attrName)
			    {
			    case Constants.ID:
				fruit.setId(str);
				attrName = 0;
				//Log.v("mad", fruit.getId());
				break;
			    case Constants.NAME:
				fruit.setName(str);
				attrName = 0;
				//Log.v("mad", fruit.getName());
				break;
			    case Constants.PRICE:
				fruit.setPrice(Double.parseDouble(str));
				attrName = 0;
				//Log.v("mad", fruit.getPrice() + "");
				break;
			    }
			break;

		    case Constants.CANDLE:
			switch (attrName)
			    {
			    case Constants.ID:
				candle.setId(str);
				attrName = 0;
				//Log.v("mad", candle.getId());
				break;
			    case Constants.NAME:
				candle.setName(str);
				attrName = 0;
				//Log.v("mad", candle.getName());
				break;
			    case Constants.PRICE:
				candle.setPrice(Double.parseDouble(str));
				attrName = 0;
				//Log.v("mad", candle.getPrice() + "");
				break;
			    case Constants.ICON:
				candle.setIcon(str);
				attrName = 0;
				//Log.v("mad", candle.getIcon());
				break;
			    }
			break;

		    case Constants.BOX:
			switch (attrName)
			    {
			    case Constants.ID:
				box.setId(str);
				attrName = 0;
				//Log.v("mad", box.getId());
				break;
			    case Constants.NAME:
				box.setName(str);
				attrName = 0;
				//Log.v("mad", box.getName());
				break;
			    case Constants.PRICE:
				box.setPrice(Double.parseDouble(str));
				attrName = 0;
				//Log.v("mad", box.getPrice()+"");
				break;
			    case Constants.ICON:
				box.setIcon(str);
				attrName = 0;
				//Log.v("mad", box.getIcon());
				break;
			    }
			break;

		    case Constants.CAKE:
			switch (attrName)
			    {
			    case Constants.ID:
				cake.setId(str);
				attrName = 0;
				//Log.v("mad", cake.getId());
				break;
			    case Constants.NAME:
				cake.setName(str);
				//Log.v("mad", cake.getName());
				attrName = 0;
				break;
			    case Constants.SIZE:
				cake.setSize(str);
				//Log.v("mad", cake.getSize());
				attrName = 0;
				break;
			    case Constants.BOTTOMPRICE:
				cake.setBottomPrice(Double.parseDouble(str));
				attrName = 0;
				//Log.v("mad", cake.getBottomPrice()+"");
				break;
			    case Constants.ICON:
				cake.setIcon(str);
				attrName = 0;
				//Log.v("mad", cake.getIcon());
				break;
			    case Constants.JAM_ID:
				Jam mjam = wareHouse.getJams().get(str);
				attrName = 0;
				cake.setJam(mjam);
				//Log.v("mad", cake.getJam().getId());
				break;
			    case Constants.FRUIT_ID:
				Fruit mfruit = wareHouse.getFruits().get(str);
				attrName = 0;
				cake.setFruit(mfruit);
				//Log.v("mad", cake.getFruit().getId());
				break;
			    case Constants.CANDLE_ID:
				Candle mcandle = wareHouse.getCandles().get(str);
				attrName = 0;
				cake.setCandle(mcandle);
				//Log.v("mad", cake.getCandle().getId());
				break;
			    case Constants.BOX_ID:
				Box mbox = wareHouse.getBoxes().get(str);
				attrName = 0;
				cake.setBox(mbox);
				//Log.v("mad", cake.getBox().getId());
				break;
			    case Constants.TOPPING_ID:
				Topping mtopping = wareHouse.getToppings().get(str);
				attrName = 0;
				cake.setTopping(mtopping);
				//Log.v("mad", cake.getTopping().getId());
				break;
			    }
			break;

		    default:
			return;
		    }
	    }

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	    {
		super.endElement(uri, localName, qName);
		//Log.v("mad", "end local name is " + localName);
		// everytime the bean tag ends, add the againest current bean to the againest list
		if (localName.equalsIgnoreCase("jam"))
		    {
			wareHouse.getJams().put(jam.getId(), jam);
		    }
		else if (localName.equalsIgnoreCase("topping"))
		    {
			wareHouse.getToppings().put(topping.getId(), topping);
		    }
		else if (localName.equalsIgnoreCase("fruit"))
		    {
			wareHouse.getFruits().put(fruit.getId(), fruit);
		    }
		else if (localName.equalsIgnoreCase("candle"))
		    {
			wareHouse.getCandles().put(candle.getId(), candle);
		    }
		else if (localName.equalsIgnoreCase("box"))
		    {
			wareHouse.getBoxes().put(box.getId(), box);
		    }
		else if (localName.equalsIgnoreCase("cake"))
		    {
			wareHouse.getCakes().put(cake.getId(), cake);
		    }
		else if(localName.equalsIgnoreCase("res"))
		    {
			wareHouse.setEmpty(false);
			//Log.v("mad", "set wareHOUSE IS FALSE");
		    }
	    }

	@Override
	public void startDocument() throws SAXException
	    {
		super.startDocument();
		// initialize the ware house instance
		wareHouse = new WareHouse();
	    }

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	    {
		super.startElement(uri, localName, qName, attributes);
		//everytime a new bean tag is presenting, create a new instance of the againest object
		//if the tag is only a attribute tag, then only update the attribute tag value
		if (localName.equalsIgnoreCase("jam"))
		    {
			jam = new Jam();
			//update the bean tag value based on  the againest instance
			beanName = Constants.JAM;
			// Log.v("mad", "local name is " + localName);
		    }
		else if (localName.equalsIgnoreCase("topping"))
		    {
			topping = new Topping();
			beanName = Constants.TOPPING;
			// Log.v("mad", "local name is " + localName);
		    }
		else if (localName.equalsIgnoreCase("fruit"))
		    {
			fruit = new Fruit();
			beanName = Constants.FRUIT;
			// Log.v("mad", "local name is " + localName);
		    }
		else if (localName.equalsIgnoreCase("candle"))
		    {
			candle = new Candle();
			beanName = Constants.CANDLE;
		    }
		else if (localName.equalsIgnoreCase("box"))
		    {
			box = new Box();
			beanName = Constants.BOX;
			// Log.v("mad", "local name is " + localName);
		    }
		else if (localName.equalsIgnoreCase("cake"))
		    {
			cake = new Cake();
			beanName = Constants.CAKE;
			// Log.v("mad", "local name is " + localName);
		    }
		else if (localName.equalsIgnoreCase("name"))
		    {
			//update only attribute value all are same beneath
			attrName = Constants.NAME;
		    }
		else if (localName.equalsIgnoreCase("id"))
		    {
			attrName = Constants.ID;
		    }
		else if (localName.equalsIgnoreCase("price"))
		    {
			attrName = Constants.PRICE;
		    }
		else if (localName.equalsIgnoreCase("icon"))
		    {
			attrName = Constants.ICON;
		    }
		else if (localName.equalsIgnoreCase("size"))
		    {
			attrName = Constants.SIZE;
		    }
		else if (localName.equalsIgnoreCase("bottomprice"))
		    {
			attrName = Constants.BOTTOMPRICE;
		    }
		else if (localName.equalsIgnoreCase("jam.id"))
		    {
			attrName = Constants.JAM_ID;
		    }
		else if (localName.equalsIgnoreCase("fruit.id"))
		    {
			attrName = Constants.FRUIT_ID;
		    }
		else if (localName.equalsIgnoreCase("topping.id"))
		    {
			attrName = Constants.TOPPING_ID;
		    }
		else if (localName.equalsIgnoreCase("candle.id"))
		    {
			attrName = Constants.CANDLE_ID;
		    }
		else if (localName.equalsIgnoreCase("box.id"))
		    {
			attrName = Constants.BOX_ID;
		    }
		else
		    {
			//reset the attribute value to initial value of 0
			attrName = 0;
		    }
		//Log.v("mad", "start local name is " + localName);
	    }
    }
