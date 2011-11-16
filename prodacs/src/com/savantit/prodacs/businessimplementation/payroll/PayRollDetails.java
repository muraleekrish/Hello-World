/*
 * Created on Jan 21, 2005
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
public class PayRollDetails implements Serializable
{
	private int prePyrlId = 0;
	private int shiftId = 0;
	private Date prodDate = null;
	private String shiftName = "";
	private float dtyHrs = 0;
	private float dtySlryHrs = 0;
	private float otHrs = 0;
	private float otSlryHrs = 0;
	private float incntvSlryHrs = 0;
	private int pyrlCycleStatId = 0;
	private int empId = 0;
	private String empName = "";
	private int empTypeId = 0;
	private String empTypeName = "";
	private String dataSource = "";
	private int noOfTimesAdjstd = 0;
	
	
	
	
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
	 * @return Returns the noOfTimesAdjstd.
	 */
	public int getNoOfTimesAdjstd() {
		return noOfTimesAdjstd;
	}
	/**
	 * @param noOfTimesAdjstd The noOfTimesAdjstd to set.
	 */
	public void setNoOfTimesAdjstd(int noOfTimesAdjstd) {
		this.noOfTimesAdjstd = noOfTimesAdjstd;
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
	 * @return Returns the prePyrlId.
	 */
	public int getPrePyrlId() {
		return prePyrlId;
	}
	/**
	 * @param prePyrlId The prePyrlId to set.
	 */
	public void setPrePyrlId(int prePyrlId) {
		this.prePyrlId = prePyrlId;
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
	 * @return Returns the pyrlCycleStatId.
	 */
	public int getPyrlCycleStatId() {
		return pyrlCycleStatId;
	}
	/**
	 * @param pyrlCycleStatId The pyrlCycleStatId to set.
	 */
	public void setPyrlCycleStatId(int pyrlCycleStatId) {
		this.pyrlCycleStatId = pyrlCycleStatId;
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
	 * @return Returns the empTypeId.
	 */
	public int getEmpTypeId() {
		return empTypeId;
	}
	/**
	 * @param empTypeId The empTypeId to set.
	 */
	public void setEmpTypeId(int empTypeId) {
		this.empTypeId = empTypeId;
	}
	/**
	 * @return Returns the empTypeName.
	 */
	public String getEmpTypeName() {
		return empTypeName;
	}
	/**
	 * @param empTypeName The empTypeName to set.
	 */
	public void setEmpTypeName(String empTypeName) {
		this.empTypeName = empTypeName;
	}
}
