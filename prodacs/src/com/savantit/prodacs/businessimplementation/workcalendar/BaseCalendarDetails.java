/*
 * Created on Nov 25, 2004
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
public class BaseCalendarDetails implements Serializable  
{
	private int bcId;
	private String bcName;
	private int bcStartDay;
	private int bcEndDay;
	private Date bcDateStamp;
	private int bcIsValid;
	private Vector vec_ShiftDetails;
	
	/**
	 * @return Returns the bcEndDay.
	 */
	public int getBcEndDay() {
		return bcEndDay;
	}
	/**
	 * @param bcEndDay The bcEndDay to set.
	 */
	public void setBcEndDay(int bcEndDay) {
		this.bcEndDay = bcEndDay;
	}
	/**
	 * @return Returns the bcName.
	 */
	public String getBcName() {
		return bcName;
	}
	/**
	 * @param bcName The bcName to set.
	 */
	public void setBcName(String bcName) {
		this.bcName = bcName;
	}
	/**
	 * @return Returns the bcStartDay.
	 */
	public int getBcStartDay() {
		return bcStartDay;
	}
	/**
	 * @param bcStartDay The bcStartDay to set.
	 */
	public void setBcStartDay(int bcStartDay) {
		this.bcStartDay = bcStartDay;
	}
	/**
	 * @return Returns the vec_ShiftDetails.
	 */
	public Vector getVec_ShiftDetails() {
		return vec_ShiftDetails;
	}
	/**
	 * @param vec_ShiftDetails The vec_ShiftDetails to set.
	 */
	public void setVec_ShiftDetails(Vector vec_ShiftDetails) {
		this.vec_ShiftDetails = vec_ShiftDetails;
	}
	/**
	 * @return Returns the bcDateStamp.
	 */
	public Date getBcDateStamp() {
		return bcDateStamp;
	}
	/**
	 * @param bcDateStamp The bcDateStamp to set.
	 */
	public void setBcDateStamp(Date bcDateStamp) {
		this.bcDateStamp = bcDateStamp;
	}
	/**
	 * @return Returns the bcIsValid.
	 */
	public int getBcIsValid() {
		return bcIsValid;
	}
	/**
	 * @param bcIsValid The bcIsValid to set.
	 */
	public void setBcIsValid(int bcIsValid) {
		this.bcIsValid = bcIsValid;
	}
	/**
	 * @return Returns the bcId.
	 */
	public int getBcId() {
		return bcId;
	}
	/**
	 * @param bcId The bcId to set.
	 */
	public void setBcId(int bcId) {
		this.bcId = bcId;
	}
}
/***
$Log: BaseCalendarDetails.java,v $
Revision 1.3  2004/12/09 05:51:33  kduraisamy
Log added.

***/