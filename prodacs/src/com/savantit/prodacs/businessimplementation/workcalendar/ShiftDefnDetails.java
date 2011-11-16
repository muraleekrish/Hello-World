/*
 * Created on Nov 24, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.workcalendar;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ShiftDefnDetails implements Serializable
{
	private int shiftId;
	private String shiftName;
	private String shiftDesc;
	private Date shiftDateStamp;
	private int shiftIsValid;
	
	/**
	 * @return Returns the shiftDesc.
	 */
	public String getShiftDesc() {
		return shiftDesc;
	}
	/**
	 * @param shiftDesc The shiftDesc to set.
	 */
	public void setShiftDesc(String shiftDesc) {
		this.shiftDesc = shiftDesc;
	}
	/**
	 * @return Returns the shiftName.
	 */
	public String getShiftName() {
		return shiftName;
	}
	/**
	 * @param shiftName The shiftName to set.
	 */
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	/**
	 * @return Returns the shiftDateStamp.
	 */
	public Date getShiftDateStamp() {
		return shiftDateStamp;
	}
	/**
	 * @param shiftDateStamp The shiftDateStamp to set.
	 */
	public void setShiftDateStamp(Date shiftDateStamp) {
		this.shiftDateStamp = shiftDateStamp;
	}
	/**
	 * @return Returns the shiftId.
	 */
	public int getShiftId() {
		return shiftId;
	}
	/**
	 * @param shiftId The shiftId to set.
	 */
	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
	}
	/**
	 * @return Returns the shiftIsValid.
	 */
	public int getShiftIsValid() {
		return shiftIsValid;
	}
	/**
	 * @param shiftIsValid The shiftIsValid to set.
	 */
	public void setShiftIsValid(int shiftIsValid) {
		this.shiftIsValid = shiftIsValid;
	}
}
/***
$Log: ShiftDefnDetails.java,v $
Revision 1.1  2004/11/24 15:44:49  kduraisamy
initial commit

***/

