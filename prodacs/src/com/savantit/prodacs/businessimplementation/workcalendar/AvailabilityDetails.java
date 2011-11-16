/*
 * Created on Nov 29, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.workcalendar;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AvailabilityDetails implements Serializable 
{
	int baseCalId;
	int availabilityId;
	String baseCalName;
	String availabilityName;
	Date fromDate;
	Date toDate;
	Date availabltyDateStamp;
	boolean availabltyCurrent = false;
	int availabiltyIsValid = 0;
	Vector customAvbltyDetails;
	Vector customNonAvbltyDetails;
	
    /**
     * @return Returns the customNonAvbltyDetails.
     */
    public Vector getCustomNonAvbltyDetails()
    {
        return customNonAvbltyDetails;
    }
    /**
     * @param customNonAvbltyDetails The customNonAvbltyDetails to set.
     */
    public void setCustomNonAvbltyDetails(Vector customNonAvbltyDetails)
    {
        this.customNonAvbltyDetails = customNonAvbltyDetails;
    }
    /**
     * @return Returns the availabltyCurrent.
     */
    public boolean isAvailabltyCurrent()
    {
        return availabltyCurrent;
    }
    /**
     * @param availabltyCurrent The availabltyCurrent to set.
     */
    public void setAvailabltyCurrent(boolean availabltyCurrent)
    {
        this.availabltyCurrent = availabltyCurrent;
    }
	
	/**
	 * @return Returns the availabilityName.
	 */
	public String getAvailabilityName() {
		return availabilityName;
	}
	/**
	 * @param availabilityName The availabilityName to set.
	 */
	public void setAvailabilityName(String availabilityName) {
		this.availabilityName = availabilityName;
	}
	/**
	 * @return Returns the baseCalId.
	 */
	public int getBaseCalId() {
		return baseCalId;
	}
	/**
	 * @param baseCalId The baseCalId to set.
	 */
	public void setBaseCalId(int baseCalId) {
		this.baseCalId = baseCalId;
	}
	
    /**
     * @return Returns the customAvbltyDetails.
     */
    public Vector getCustomAvbltyDetails()
    {
        return customAvbltyDetails;
    }
    /**
     * @param customAvbltyDetails The customAvbltyDetails to set.
     */
    public void setCustomAvbltyDetails(Vector customAvbltyDetails)
    {
        this.customAvbltyDetails = customAvbltyDetails;
    }
    /**
     * @return Returns the nonAvbltyDetails.
     */
    public Vector getNonAvbltyDetails()
    {
        return customNonAvbltyDetails;
    }
    /**
     * @param nonAvbltyDetails The nonAvbltyDetails to set.
     */
    public void setNonAvbltyDetails(Vector nonAvbltyDetails)
    {
        this.customNonAvbltyDetails = nonAvbltyDetails;
    }
	/**
	 * @return Returns the availabilityId.
	 */
	public int getAvailabilityId() {
		return availabilityId;
	}
	/**
	 * @param availabilityId The availabilityId to set.
	 */
	public void setAvailabilityId(int availabilityId) {
		this.availabilityId = availabilityId;
	}
	/**
	 * @return Returns the fromDate.
	 */
	public Date getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return Returns the toDate.
	 */
	public Date getToDate() {
		return toDate;
	}
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return Returns the availabiltyIsValid.
	 */
	public int getAvailabiltyIsValid() {
		return availabiltyIsValid;
	}
	/**
	 * @param availabiltyIsValid The availabiltyIsValid to set.
	 */
	public void setAvailabiltyIsValid(int availabiltyIsValid) {
		this.availabiltyIsValid = availabiltyIsValid;
	}
	/**
	 * @return Returns the availabltyDateStamp.
	 */
	public Date getAvailabltyDateStamp() {
		return availabltyDateStamp;
	}
	/**
	 * @param availabltyDateStamp The availabltyDateStamp to set.
	 */
	public void setAvailabltyDateStamp(Date availabltyDateStamp) {
		this.availabltyDateStamp = availabltyDateStamp;
	}
	/**
	 * @return Returns the baseCalName.
	 */
	public String getBaseCalName() {
		return baseCalName;
	}
	/**
	 * @param baseCalName The baseCalName to set.
	 */
	public void setBaseCalName(String baseCalName) {
		this.baseCalName = baseCalName;
	}
}
/***
$Log: AvailabilityDetails.java,v $
Revision 1.9  2005/04/26 10:08:10  kduraisamy
availability current added.

Revision 1.8  2005/04/20 10:10:10  kduraisamy
avbltyDetails() add method changed.

Revision 1.7  2004/12/09 05:51:33  kduraisamy
Log added.

***/