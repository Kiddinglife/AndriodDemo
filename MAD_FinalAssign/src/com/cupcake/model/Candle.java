/**
 * this class represents the candle member that will be part of cake bean
 */
package com.cupcake.model;

import java.io.Serializable;

public class Candle implements Serializable
    {

	private static final long serialVersionUID = -544863416909794613L;
	// name attribute is againest the name tag in cakes.xml
	private String name;
	// price attribute is againest the price tag in cakes.xml
	private double price;
	// icon attribute is againest the price tag in cakes.xml
	private String icon;
	// price attribute is againest the id tag in cakes.xm
	private String id;

	public Candle()
	    {
		// TODO Auto-generated constructor stub
	    }

	public Candle(String name, double price, String icon, String id)
	    {
		super();
		this.name = name;
		this.price = price;
		this.icon = icon;
		this.id = id;
	    }

	public String getName()
	    {
		return name;
	    }

	public void setName(String name)
	    {
		this.name = name;
	    }

	public double getPrice()
	    {
		return price;
	    }

	public void setPrice(double price)
	    {
		this.price = price;
	    }

	public String getIcon()
	    {
		return icon;
	    }

	public void setIcon(String icon)
	    {
		this.icon = icon;
	    }

	@Override
	public String toString()
	    {
		return "Candle [name=" + name + ", price=" + price + ", icon=" + icon + "]";
	    }

	public String getId()
	    {
		return id;
	    }

	public void setId(String id)
	    {
		this.id = id;
	    }

    }
