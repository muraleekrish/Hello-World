/*
 * Created on Jan 19, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.payroll;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PayRollDateShiftDetails implements Serializable
{
	private Date prodDate = null;
	private int shiftId = 0;
	private String shiftName = "";
	private Vector employeeEmpHrsDetails = null;
	private float totShiftDtySlryHrs = 0;
	private float totShiftOtSlryHrs = 0;
	private float totShiftIncntvSlryHrs = 0;
	private float totShiftDtyHrs = 0;
	private float totShiftOtHrs = 0;
	
	
	/**
	 * @return Returns the totShiftDtyHrs.
	 */
	public float getTotShiftDtyHrs() {
		return totShiftDtyHrs;
	}
	/**
	 * @param totShiftDtyHrs The totShiftDtyHrs to set.
	 */
	public void setTotShiftDtyHrs(float totShiftDtyHrs) {
		this.totShiftDtyHrs = totShiftDtyHrs;
	}
	/**
	 * @return Returns the totShiftOtHrs.
	 */
	public float getTotShiftOtHrs() {
		return totShiftOtHrs;
	}
	/**
	 * @param totShiftOtHrs The totShiftOtHrs to set.
	 */
	public void setTotShiftOtHrs(float totShiftOtHrs) {
		this.totShiftOtHrs = totShiftOtHrs;
	}
	/**
	 * @return Returns the employeeEmpHrsDetails.
	 */
	public Vector getEmployeeEmpHrsDetails() {
		return employeeEmpHrsDetails;
	}
	/**
	 * @param employeeEmpHrsDetails The employeeEmpHrsDetails to set.
	 */
	public void setEmployeeEmpHrsDetails(Vector employeeEmpHrsDetails) {
		this.employeeEmpHrsDetails = employeeEmpHrsDetails;
	}
	/**
	 * @return Returns the prodDate.
	 */
	public Date getProdDate() {
		return prodDate;
	}
	/**
	 * @param prodDate The prodDate to set.
	 */
	public void setProdDate(Date prodDate) {
		this.prodDate = prodDate;
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
	 * @return Returns the totShiftDtySlryHrs.
	 */
	public float getTotShiftDtySlryHrs() {
		return totShiftDtySlryHrs;
	}
	/**
	 * @param totShiftDtySlryHrs The totShiftDtySlryHrs to set.
	 */
	public void setTotShiftDtySlryHrs(float totShiftDtySlryHrs) {
		this.totShiftDtySlryHrs = totShiftDtySlryHrs;
	}
	/**
	 * @return Returns the totShiftIncntvSlryHrs.
	 */
	public float getTotShiftIncntvSlryHrs() {
		return totShiftIncntvSlryHrs;
	}
	/**
	 * @param totShiftIncntvSlryHrs The totShiftIncntvSlryHrs to set.
	 */
	public void setTotShiftIncntvSlryHrs(float totShiftIncntvSlryHrs) {
		this.totShiftIncntvSlryHrs = totShiftIncntvSlryHrs;
	}
	/**
	 * @return Returns the totShiftOtSlryHrs.
	 */
	public float getTotShiftOtSlryHrs() {
		return totShiftOtSlryHrs;
	}
	/**
	 * @param totShiftOtSlryHrs The totShiftOtSlryHrs to set.
	 */
	public void setTotShiftOtSlryHrs(float totShiftOtSlryHrs) {
		this.totShiftOtSlryHrs = totShiftOtSlryHrs;
	}
}
