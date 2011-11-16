/*
 * Created on Nov 10, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.employee;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class EmployeeStatusDetails implements Serializable
{
	private String empStatName;
	private int empStatId;
	private Date empDateStamp;
	private int empIsValid;
	public EmployeeStatusDetails()
	{
		empStatName = "";
		empStatId = 0;
		empDateStamp = null;
		empIsValid = 0;
				
	}
	
	/**
	 * @return Returns the empDateStamp.
	 */
	public Date getEmpDateStamp() {
		return empDateStamp;
	}
	/**
	 * @param empDateStamp The empDateStamp to set.
	 */
	public void setEmpDateStamp(Date empDateStamp) {
		this.empDateStamp = empDateStamp;
	}
	/**
	 * @return Returns the empIsValid.
	 */
	public int getEmpIsValid() {
		return empIsValid;
	}
	/**
	 * @param empIsValid The empIsValid to set.
	 */
	public void setEmpIsValid(int empIsValid) {
		this.empIsValid = empIsValid;
	}
	/**
	 * @return Returns the empStatId.
	 */
	public int getEmpStatId() {
		return empStatId;
	}
	/**
	 * @param empStatId The empStatId to set.
	 */
	public void setEmpStatId(int empStatId) {
		this.empStatId = empStatId;
	}
	/**
	 * @return Returns the empStatName.
	 */
	public String getEmpStatName() {
		return empStatName;
	}
	/**
	 * @param empStatName The empStatName to set.
	 */
	public void setEmpStatName(String empStatName) {
		this.empStatName = empStatName;
	}
}
