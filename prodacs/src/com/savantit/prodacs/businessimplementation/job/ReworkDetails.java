/*
 * Created on Nov 18, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.job;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ReworkDetails implements Serializable
{
	private int rwk_Id;
	private int rwk_Category_Id;
	private String rwk_Category;
	private String rwk_Rsn;
	private Date rwk_DateStamp;
	private int rwk_IsValid;
	private Date rwk_Category_DateStamp;
	private int rwk_Category_IsValid;
	
	public ReworkDetails()
	{
		rwk_Id = 0;
		rwk_Category_Id = 0;
		rwk_Category = "";
		rwk_Rsn = "";
		rwk_DateStamp = null;
		rwk_IsValid = 0;
		rwk_Category_DateStamp = null;
		rwk_Category_IsValid = 0;
	}
	/**
	 * @return Returns the rwk_Category.
	 */
	public String getRwk_Category() {
		return rwk_Category;
	}
	/**
	 * @param rwk_Category The rwk_Category to set.
	 */
	public void setRwk_Category(String rwk_Category) {
		this.rwk_Category = rwk_Category;
	}
	/**
	 * @return Returns the rwk_Category_DateStamp.
	 */
	public Date getRwk_Category_DateStamp() {
		return rwk_Category_DateStamp;
	}
	/**
	 * @param rwk_Category_DateStamp The rwk_Category_DateStamp to set.
	 */
	public void setRwk_Category_DateStamp(Date rwk_Category_DateStamp) {
		this.rwk_Category_DateStamp = rwk_Category_DateStamp;
	}
	/**
	 * @return Returns the rwk_Category_Id.
	 */
	public int getRwk_Category_Id() {
		return rwk_Category_Id;
	}
	/**
	 * @param rwk_Category_Id The rwk_Category_Id to set.
	 */
	public void setRwk_Category_Id(int rwk_Category_Id) {
		this.rwk_Category_Id = rwk_Category_Id;
	}
	/**
	 * @return Returns the rwk_Category_IsValid.
	 */
	public int getRwk_Category_IsValid() {
		return rwk_Category_IsValid;
	}
	/**
	 * @param rwk_Category_IsValid The rwk_Category_IsValid to set.
	 */
	public void setRwk_Category_IsValid(int rwk_Category_IsValid) {
		this.rwk_Category_IsValid = rwk_Category_IsValid;
	}
	/**
	 * @return Returns the rwk_DateStamp.
	 */
	public Date getRwk_DateStamp() {
		return rwk_DateStamp;
	}
	/**
	 * @param rwk_DateStamp The rwk_DateStamp to set.
	 */
	public void setRwk_DateStamp(Date rwk_DateStamp) {
		this.rwk_DateStamp = rwk_DateStamp;
	}
	/**
	 * @return Returns the rwk_Id.
	 */
	public int getRwk_Id() {
		return rwk_Id;
	}
	/**
	 * @param rwk_Id The rwk_Id to set.
	 */
	public void setRwk_Id(int rwk_Id) {
		this.rwk_Id = rwk_Id;
	}
	/**
	 * @return Returns the rwk_IsValid.
	 */
	public int getRwk_IsValid() {
		return rwk_IsValid;
	}
	/**
	 * @param rwk_IsValid The rwk_IsValid to set.
	 */
	public void setRwk_IsValid(int rwk_IsValid) {
		this.rwk_IsValid = rwk_IsValid;
	}
	/**
	 * @return Returns the rwk_Rsn.
	 */
	public String getRwk_Rsn() {
		return rwk_Rsn;
	}
	/**
	 * @param rwk_Rsn The rwk_Rsn to set.
	 */
	public void setRwk_Rsn(String rwk_Rsn) {
		this.rwk_Rsn = rwk_Rsn;
	}
}
/***
$Log: ReworkDetails.java,v $
Revision 1.2  2004/11/20 12:17:49  sduraisamy
Serializable implemented

Revision 1.1  2004/11/20 10:34:31  sduraisamy
Initial commit of ReworkDetails,ReworkDetailsManager,ReworkException

***/