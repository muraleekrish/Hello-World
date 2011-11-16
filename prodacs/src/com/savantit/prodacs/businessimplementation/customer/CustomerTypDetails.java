/*
 * Created on Nov 9, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.customer;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CustomerTypDetails implements Serializable
{
	private int cust_Typ_Id;
	private String  cust_Typ_Name;
	private String cust_Typ_Desc;
	private Date cust_Typ_DateStamp;
	private int cust_Typ_IsValid;
	
	public CustomerTypDetails()
	{
		cust_Typ_Id=0;
		cust_Typ_Name="";
		cust_Typ_Desc="";
		cust_Typ_DateStamp = null;
		
	}
	
    /**
     * @return Returns the cust_Typ_DateStamp.
     */
    public Date getCust_Typ_DateStamp()
    {
        return cust_Typ_DateStamp;
    }
    /**
     * @param cust_Typ_DateStamp The cust_Typ_DateStamp to set.
     */
    public void setCust_Typ_DateStamp(Date cust_Typ_DateStamp)
    {
        this.cust_Typ_DateStamp = cust_Typ_DateStamp;
    }
    /**
     * @return Returns the cust_Typ_IsValid.
     */
    public int getCust_Typ_IsValid()
    {
        return cust_Typ_IsValid;
    }
    /**
     * @param cust_Typ_IsValid The cust_Typ_IsValid to set.
     */
    public void setCust_Typ_IsValid(int cust_Typ_IsValid)
    {
        this.cust_Typ_IsValid = cust_Typ_IsValid;
    }
	/**
	 * @return Returns the cust_Typ_Desc.
	 */
	public String getCust_Typ_Desc() {
		return cust_Typ_Desc;
	}
	/**
	 * @param cust_Typ_Desc The cust_Typ_Desc to set.
	 */
	public void setCust_Typ_Desc(String cust_Typ_Desc) {
		this.cust_Typ_Desc = cust_Typ_Desc;
	}
	/**
	 * @return Returns the cust_Typ_Id.
	 */
	public int getCust_Typ_Id() {
		return cust_Typ_Id;
	}
	/**
	 * @param cust_Typ_Id The cust_Typ_Id to set.
	 */
	public void setCust_Typ_Id(int cust_Typ_Id) {
		this.cust_Typ_Id = cust_Typ_Id;
	}
	/**
	 * @return Returns the cust_Typ_Name.
	 */
	public String getCust_Typ_Name() {
		return cust_Typ_Name;
	}
	/**
	 * @param cust_Typ_Name The cust_Typ_Name to set.
	 */
	public void setCust_Typ_Name(String cust_Typ_Name) {
		this.cust_Typ_Name = cust_Typ_Name;
	}
}
/***
$Log: CustomerTypDetails.java,v $
Revision 1.2  2005/03/05 06:33:20  kduraisamy
cust typ details filter added.

Revision 1.1  2004/11/09 07:41:36  kduraisamy
Entity Object CustomerTypDetails added. addCustomerTypeDetails method added in to CustomerDetailsManager.

***/