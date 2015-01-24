/**
 * this class will hold all the cakes, candle, box, fruit, jam, topping.it acts like a warehouse. 
 * when user choose different accery, the cake object's attributes will be update by the lists in this stored in this class
 */
package com.cupcake.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class WareHouse implements Serializable
    {

	private static final long serialVersionUID = -8845196546734436997L;
	private boolean isEmpty = true;
	private Map<String, Cake> cakes;
	private Map<String, Box> boxes;
	private Map<String, Candle> candles;
	private Map<String, Fruit> fruits;
	private Map<String, Jam> jams;
	private Map<String, Topping> toppings;

	public WareHouse()
	    {
		super();
		cakes = new HashMap<String, Cake>();
		boxes = new HashMap<String, Box>();
		candles = new HashMap<String, Candle>();
		fruits = new HashMap<String, Fruit>();
		jams = new HashMap<String, Jam>();
		toppings = new HashMap<String, Topping>();
	    }

	public Map<String, Cake> getCakes()
	    {
		return cakes;
	    }

	public void setCakes(Map<String, Cake> cakes)
	    {
		this.cakes = cakes;
	    }

	public Map<String, Box> getBoxes()
	    {
		return boxes;
	    }

	public void setBoxes(Map<String, Box> boxes)
	    {
		this.boxes = boxes;
	    }

	public Map<String, Candle> getCandles()
	    {
		return candles;
	    }

	public void setCandles(Map<String, Candle> candles)
	    {
		this.candles = candles;
	    }

	public Map<String, Fruit> getFruits()
	    {
		return fruits;
	    }

	public void setFruits(Map<String, Fruit> fruits)
	    {
		this.fruits = fruits;
	    }

	public Map<String, Jam> getJams()
	    {
		return jams;
	    }

	public void setJams(Map<String, Jam> jams)
	    {
		this.jams = jams;
	    }

	public Map<String, Topping> getToppings()
	    {
		return toppings;
	    }

	public void setToppings(Map<String, Topping> toppings)
	    {
		this.toppings = toppings;
	    }

	public boolean isEmpty()
	    {
		return isEmpty;
	    }

	public void setEmpty(boolean isEmpty)
	    {
		this.isEmpty = isEmpty;
	    }

	@Override
	public String toString()
	    {
		return "WareHouse [isEmpty=" + isEmpty + ", cakes=" + cakes + ", boxes=" + boxes + ", candles=" + candles + ", fruits=" + fruits + ", jams=" + jams + ", toppings=" + toppings + "]";
	    }

    }
