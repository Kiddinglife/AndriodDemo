/**
 * this class represents the cake member that will be part of cake bean
 */
package com.cupcake.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cake implements Serializable
    {

	private static final long serialVersionUID = 8457740680362713728L;
	private String id;
	// name attribute is againest the name tag in cakes.xml
	private String name;
	// size attribute is againest the size tag in cakes.xml
	private String size;
	// icon attribute is againest the niconame tag in cakes.xml
	private String icon;
	// bottom price of this cake that will be calculated based on acceries
	// prices plusing cake price
	private double bottomPrice;
	// total price of this cake
	private double totalPrice;
	// current jam on this cake
	private Jam jam;
	// current fruit on this cake
	private Fruit fruit;
	// current topping on this cake
	private Topping topping;
	// current candle on this cake
	private Candle candle;
	// private List<Candle> candles = new ArrayList<Candle>();
	// current box on this cake
	private Box box;
	// private List<Box> boxes = new ArrayList<Box>();
	private double acc;
	private String status = "uncooked";

	public Cake(String name, String size, String icon, double bottomPrice, double totalPrice, Jam jam, Fruit fruit, Topping topping, Candle candle, Box box, String id)
	    {
		super();
		this.acc = candle.getPrice() + box.getPrice();
		this.id = id;
		this.name = name;
		this.size = size;
		this.icon = icon;
		this.bottomPrice = bottomPrice;
		this.totalPrice = bottomPrice + jam.getPrice() + fruit.getPrice() + topping.getPrice() + this.acc;
		this.jam = jam;
		this.fruit = fruit;
		this.topping = topping;
		this.candle = candle;
		this.box = box;
	    }

	public Cake()
	    {
		super();
	    }

	public String getName()
	    {
		return name;
	    }

	public void setName(String name)
	    {
		this.name = name;
	    }

	public String getSize()
	    {
		return size;
	    }

	public void setSize(String size)
	    {
		this.size = size;
	    }

	public String getIcon()
	    {
		return icon;
	    }

	public void setIcon(String icon)
	    {
		this.icon = icon;
	    }

	public double getBottomPrice()
	    {
		return bottomPrice;
	    }

	public void setBottomPrice(double bottomPrice)
	    {
		this.bottomPrice = bottomPrice;
	    }

	public double getTotalPrice()
	    {
		this.totalPrice = bottomPrice + jam.getPrice() + fruit.getPrice() + topping.getPrice() + getAcc();
		return totalPrice;
	    }

	public double returnTotalPrice()
	    {
		return totalPrice;
	    }

	public void setTotalPrice(double totalPrice)
	    {
		this.totalPrice = totalPrice;
	    }

	public Jam getJam()
	    {
		return jam;
	    }

	public void setJam(Jam jam)
	    {
		this.jam = jam;
	    }

	public Fruit getFruit()
	    {
		return fruit;
	    }

	public void setFruit(Fruit fruit)
	    {
		this.fruit = fruit;
	    }

	public Topping getTopping()
	    {
		return topping;
	    }

	public void setTopping(Topping topping)
	    {
		this.topping = topping;
	    }

	public Candle getCandle()
	    {
		return candle;
	    }

	public void setCandle(Candle candle)
	    {
		this.candle = candle;
	    }

	public Box getBox()
	    {
		return box;
	    }

	public void setBox(Box box)
	    {
		this.box = box;
	    }

	public String getId()
	    {
		return id;
	    }

	public void setId(String id)
	    {
		this.id = id;
	    }

	public double getAcc()
	    {
		this.acc = candle.getPrice() + box.getPrice();
		return acc;
	    }

	public double returnAcc()
	    {
		return acc;
	    }

	public void setAcc(double acc)
	    {
		this.acc = acc;
	    }

	public String getStatus()
	    {
		return status;
	    }

	public void setStatus(String status)
	    {
		this.status = status;
	    }

	@Override
	public String toString()
	    {
		return "Cake [id=" + id + ", name=" + name + ", size=" + size + ", icon=" + icon + ", bottomPrice=" + bottomPrice + ", totalPrice=" + totalPrice + ", jam=" + jam + ", fruit=" + fruit
			+ ", topping=" + topping + ", candle=" + candle + ", box=" + box + ", acc=" + acc + ", status=" + status + "]";
	    }
	
    }
