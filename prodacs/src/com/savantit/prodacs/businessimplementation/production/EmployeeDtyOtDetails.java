/*
 * Created on Dec 10, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.production;

import java.io.Serializable;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class EmployeeDtyOtDetails implements Serializable 
{
	private int prodId;
	private int empId;
	private String empType;
	private int empTypdId;
	private String empName;
	private String empCode;
	private float dutyHrs;
	private float otHrs;
	private float dutySlryHrs;
	private float otSlryHrs;
	private float incntvSlryHrs;
	
	public EmployeeDtyOtDetails()
	{
	
		this.prodId = 0;
		this.empId = 0;
		this.empType = "";
		this.empTypdId = 0;
		this.empName = "";
		this.empCode = "";
		this.dutyHrs = 0;
		this.otHrs = 0;
		this.dutySlryHrs = 0;
		this.otSlryHrs = 0;
		this.incntvSlryHrs = 0;
	}
	/**
	 * @return Returns the dutyHrs.
	 */
	public float getDutyHrs() {
		return dutyHrs;
	}
	/**
	 * @param dutyHrs The dutyHrs to set.
	 */
	public void setDutyHrs(float dutyHrs) {
		this.dutyHrs = dutyHrs;
	}
	/**
	 * @return Returns the empId.
	 */
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
	 * @return Returns the empType.
	 */
	public String getEmpType() {
		return empType;
	}
	/**
	 * @param empType The empType to set.
	 */
	public void setEmpType(String empType) {
		this.empType = empType;
	}
	/**
	 * @return Returns the otHrs.
	 */
	public float getOtHrs() {
		return otHrs;
	}
	/**
	 * @param otHrs The otHrs to set.
	 */
	public void setOtHrs(float otHrs) {
		this.otHrs = otHrs;
	}
	/**
	 * @return Returns the prodId.
	 */
	public int getProdId() {
		return prodId;
	}
	/**
	 * @param prodId The prodId to set.
	 */
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}
	/**
	 * @return Returns the dutySlryHrs.
	 */
	public float getDutySlryHrs() {
		return dutySlryHrs;
	}
	/**
	 * @param dutySlryHrs The dutySlryHrs to set.
	 */
	public void setDutySlryHrs(float dutySlryHrs) {
		this.dutySlryHrs = dutySlryHrs;
	}
	/**
	 * @return Returns the otSlryHrs.
	 */
	public float getOtSlryHrs() {
		return otSlryHrs;
	}
	/**
	 * @param otSlryHrs The otSlryHrs to set.
	 */
	public void setOtSlryHrs(float otSlryHrs) {
		this.otSlryHrs = otSlryHrs;
	}
	/**
	 * @return Returns the incntvSlryHrs.
	 */
	public float getIncntvSlryHrs() {
		return incntvSlryHrs;
	}
	/**
	 * @param incntvSlryHrs The incntvSlryHrs to set.
	 */
	public void setIncntvSlryHrs(float incntvSlryHrs) {
		this.incntvSlryHrs = incntvSlryHrs;
	}
	/**
	 * @return Returns the empTypdId.
	 */
	public int getEmpTypdId() {
		return empTypdId;
	}
	/**
	 * @param empTypdId The empTypdId to set.
	 */
	public void setEmpTypdId(int empTypdId) {
		this.empTypdId = empTypdId;
	}
    /**
     * @return Returns the empCode.
     */
    public String getEmpCode()
    {
        return empCode;
    }
    /**
     * @param empCode The empCode to set.
     */
    public void setEmpCode(String empCode)
    {
        this.empCode = empCode;
    }
}
