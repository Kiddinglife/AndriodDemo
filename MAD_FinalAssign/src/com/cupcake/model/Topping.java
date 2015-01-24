/**
 *  this class represents the topping member that will be part of cake bean
 */
package com.cupcake.model;

import java.io.Serializable;

public class Topping implements Serializable
    {

	private static final long serialVersionUID = 3467805162442172071L;
	// name attribute is againest the name tag in cakes.xml
	private String name;
	// price attribute is againest the price tag in cakes.xml
	private double price;
	// price attribute is againest the id tag in cakes.xm
	private String id;

	public Topping()
	    {
		// TODO Auto-generated constructor stub
	    }

	public Topping(String name, double price, String id)
	    {
		super();
		this.name = name;
		this.price = price;
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

	@Override
	public String toString()
	    {
		return "Topping [name=" + name + ", price=" + price + "]";
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
