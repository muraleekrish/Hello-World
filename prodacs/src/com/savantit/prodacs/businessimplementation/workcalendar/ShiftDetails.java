/*
 * Created on Nov 25, 2004
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
public class ShiftDetails implements Serializable
{
	private int day;
	private int shiftId;
	private String shiftName;
	private String shiftDesc;
	private String startTime;
	private String startTimeDay;
	private String endTime;
	private String endTimeDay;
	private int predsrShiftId;
	private String predsrShiftName;
	private Date shiftDateStamp;
	private int shiftIsValid;
	
	/**
	 * @return Returns the day.
	 */
	public int getDay() {
		return day;
	}
	/**
	 * @param day The day to set.
	 */
	public void setDay(int day) {
		this.day = day;
	}
	/**
	 * @return Returns the endTime.
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime The endTime to set.
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * @return Returns the startTime.
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime The startTime to set.
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
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
	 * @return Returns the predsrShiftId.
	 */
	public int getPredsrShiftId() {
		return predsrShiftId;
	}
	/**
	 * @param predsrShiftId The predsrShiftId to set.
	 */
	public void setPredsrShiftId(int predsrShiftId) {
		this.predsrShiftId = predsrShiftId;
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
	 * @return Returns the predsrShiftName.
	 */
	public String getPredsrShiftName() {
		return predsrShiftName;
	}
	/**
	 * @param predsrShiftName The predsrShiftName to set.
	 */
	public void setPredsrShiftName(String predsrShiftName) {
		this.predsrShiftName = predsrShiftName;
	}
    /**
     * @return Returns the endTimeDay.
     */
    public String getEndTimeDay()
    {
        return endTimeDay;
    }
    /**
     * @param endTimeDay The endTimeDay to set.
     */
    public void setEndTimeDay(String endTimeDay)
    {
        this.endTimeDay = endTimeDay;
    }
    /**
     * @return Returns the startTimeDay.
     */
    public String getStartTimeDay()
    {
        return startTimeDay;
    }
    /**
     * @param startTimeDay The startTimeDay to set.
     */
    public void setStartTimeDay(String startTimeDay)
    {
        this.startTimeDay = startTimeDay;
    }
}
/***
$Log: ShiftDetails.java,v $
Revision 1.6  2005/03/19 06:04:25  kduraisamy
field base_cal_var_starttime_day,endtime_day added.

Revision 1.5  2004/12/09 05:51:33  kduraisamy
Log added.

***/