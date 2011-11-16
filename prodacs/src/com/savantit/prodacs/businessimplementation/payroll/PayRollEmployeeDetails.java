/*
 * Created on Jan 19, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.payroll;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PayRollEmployeeDetails implements Serializable
{
	private int empId;
	private String empName;
	private Vector dateShiftEmpHrsDetails;
	private float totDtySlryHrs;
	private float totOtSlryHrs;
	private float totIncntvSlryHrs;
	private float totDtyHrs;
	private float totOtHrs;
	
	public PayRollEmployeeDetails()
	{
	    empId = 0;
		empName = "";
		dateShiftEmpHrsDetails = null;
		totDtySlryHrs = 0;
		totOtSlryHrs = 0;
		totIncntvSlryHrs = 0;
		totDtyHrs = 0;
		totOtHrs = 0;
		    
	}
	
	
	
	/**
	 * @return Returns the dateShiftEmpHrsDetails.
	 */
	public Vector getDateShiftEmpHrsDetails() {
		return dateShiftEmpHrsDetails;
	}
	/**
	 * @param dateShiftEmpHrsDetails The dateShiftEmpHrsDetails to set.
	 */
	public void setDateShiftEmpHrsDetails(Vector dateShiftEmpHrsDetails) {
		this.dateShiftEmpHrsDetails = dateShiftEmpHrsDetails;
	}
	public int getEmpId() {
		return empId;
	}
	/**
	 * @param empId The empId to set.
	 */
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	/**
	 * @return Returns the empName.
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName The empName to set.
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	/**
	 * @return Returns the totDtySlryHrs.
	 */
	public float getTotDtySlryHrs() {
		return totDtySlryHrs;
	}
	/**
	 * @param totDtySlryHrs The totDtySlryHrs to set.
	 */
	public void setTotDtySlryHrs(float totDtySlryHrs) {
		this.totDtySlryHrs = totDtySlryHrs;
	}
	/**
	 * @return Returns the totIncntvSlryHrs.
	 */
	public float getTotIncntvSlryHrs() {
		return totIncntvSlryHrs;
	}
	/**
	 * @param totIncntvSlryHrs The totIncntvSlryHrs to set.
	 */
	public void setTotIncntvSlryHrs(float totIncntvSlryHrs) {
		this.totIncntvSlryHrs = totIncntvSlryHrs;
	}
	/**
	 * @return Returns the totOtSlryHrs.
	 */
	public float getTotOtSlryHrs() {
		return totOtSlryHrs;
	}
	/**
	 * @param totOtSlryHrs The totOtSlryHrs to set.
	 */
	public void setTotOtSlryHrs(float totOtSlryHrs) {
		this.totOtSlryHrs = totOtSlryHrs;
	}
    /**
     * @return Returns the totDtyHrs.
     */
    public float getTotDtyHrs()
    {
        return totDtyHrs;
    }
    /**
     * @param totDtyHrs The totDtyHrs to set.
     */
    public void setTotDtyHrs(float totDtyHrs)
    {
        this.totDtyHrs = totDtyHrs;
    }
    /**
     * @return Returns the totOtHrs.
     */
    public float getTotOtHrs()
    {
        return totOtHrs;
    }
    /**
     * @param totOtHrs The totOtHrs to set.
     */
    public void setTotOtHrs(float totOtHrs)
    {
        this.totOtHrs = totOtHrs;
    }
}
