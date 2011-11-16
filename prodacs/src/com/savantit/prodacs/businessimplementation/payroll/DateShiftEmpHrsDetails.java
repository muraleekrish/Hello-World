/*
 * Created on Jan 19, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.payroll;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DateShiftEmpHrsDetails implements Serializable
{
	private Date prodDate = null;
	private int shiftId = 0;
	private String shiftName = "";
	private float dtyHrs = 0;
	private float dtySlryHrs = 0;
	private float otHrs = 0;
	private float otSlryHrs = 0;
	private float incntvSlryHrs = 0;
	
	private String dataSource = "";
	
	/**
	 * @return Returns the dtyHrs.
	 */
	public float getDtyHrs() {
		return dtyHrs;
	}
	/**
	 * @param dtyHrs The dtyHrs to set.
	 */
	public void setDtyHrs(float dtyHrs) {
		this.dtyHrs = dtyHrs;
	}
	/**
	 * @return Returns the dtySlryHrs.
	 */
	public float getDtySlryHrs() {
		return dtySlryHrs;
	}
	/**
	 * @param dtySlryHrs The dtySlryHrs to set.
	 */
	public void setDtySlryHrs(float dtySlryHrs) {
		this.dtySlryHrs = dtySlryHrs;
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
	 * @return Returns the dataSource.
	 */
	public String getDataSource() {
		return dataSource;
	}
	/**
	 * @param dataSource The dataSource to set.
	 */
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
}
