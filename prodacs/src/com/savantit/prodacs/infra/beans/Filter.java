/*
 * Created on Oct 30, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.infra.beans;

import java.io.Serializable;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Filter implements Serializable 
{
	private String fieldName;
	private String fieldValue;
	private String specialFunction;
	
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}
	
	public String getFieldName()
	{
		return this.fieldName;
	}
	
	public void setFieldValue(String fieldValue)
	{
		this.fieldValue = fieldValue;
	}
	
	public String getFieldValue()
	{
		return this.fieldValue;
	}
	
	public void setSpecialFunction(String specialFunction)
	{
		this.specialFunction = specialFunction;
	}
	
	public String getSpecialFunction()
	{
		return this.specialFunction;
	}
}

/***
$Log: Filter.java,v $
Revision 1.2  2004/11/06 07:43:14  sduraisamy
Log inclusion for Filter,DBConnection,DBNull,SQLMaster,DateUtil,Debug and LoggerOutput

***/