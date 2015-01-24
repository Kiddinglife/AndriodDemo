/**
 * this class represents the jam member that will be part of cake bean
 */
package com.cupcake.model;

import java.io.Serializable;

public class Jam implements Serializable
    {

	private static final long serialVersionUID = 2755199553773610205L;
	// name attribute is againest the name tag in cakes.xml
	private String name;
	// price attribute is againest the price tag in cakes.xml
	private double price;
	// price attribute is againest the id tag in cakes.xm
	private String id;

	public Jam(String name, double price, String id)
	    {
		super();
		this.name = name;
		this.price = price;
		this.id = id;
	    }

	public Jam()
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

	public double getPrice()
	    {
		return price;
	    }

	public void setPrice(double price)
	    {
		this.price = price;
	    }

	@Override
	public String toString()
	    {
		return "Jam [name=" + name + ", price=" + price + "]";
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
