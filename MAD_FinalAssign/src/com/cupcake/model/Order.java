package com.cupcake.model;

import java.io.Serializable;

public class Order implements Serializable
    {

	private static final long serialVersionUID = -6514916805807050182L;
	private String name;
	private String number;
	private String addr;
	private String pcode;
	private Cake cake;
	private String date;
	private String id;

	public Order(String name, String number, String addr, String pcode, String date, Cake cake)
	    {
		super();
		this.name = name;
		this.number = number;
		this.addr = addr;
		this.pcode = pcode;
		this.cake = cake;
		this.date = date;
	    }

	public Order()
	    {
	    }

	public String getName()
	    {
		return name;
	    }

	public void setName(String name)
	    {
		this.name = name;
	    }

	public String getNumber()
	    {
		return number;
	    }

	public void setNumber(String number)
	    {
		this.number = number;
	    }

	public String getAddr()
	    {
		return addr;
	    }

	public void setAddr(String addr)
	    {
		this.addr = addr;
	    }

	public String getPcode()
	    {
		return pcode;
	    }

	public void setPcode(String pcode)
	    {
		this.pcode = pcode;
	    }

	public Cake getCake()
	    {
		return cake;
	    }

	public void setCake(Cake cake)
	    {
		this.cake = cake;
	    }


	public String getDate()
	    {
		return date;
	    }

	public void setDate(String date)
	    {
		this.date = date;
	    }

	public String getId()
	    {
		return id;
	    }

	public void setId(String id)
	    {
		this.id = id;
	    }

	@Override
	public String toString()
	    {
		return "Order [name=" + name + ", number=" + number + ", addr=" + addr + ", pcode=" + pcode + ", cake=" + cake + ", date=" + date + ", id=" + id + "]";
	    }	
    }
