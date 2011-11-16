/*
 * Created on Dec 30, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.production;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PayOutProductionAddForm extends ActionForm
{
	private String machineShift = "0";
	private String employeeType = "0";
	private String employeeName = "0";
	private String dutyHrs = "";
	private String otHrs = "";
	private String popReason = "";
	private String popFromDate = "";
	private String formAction = "";
	private String hidEmpTypId = "";
	private String hidEmpDet = "";
	private String showCount = "";
	private String hidPopDet = "";
	private String hidUserId = "";
	
	
	/**
	 * @return Returns the hidUserId.
	 */
	public String getHidUserId() {
		return hidUserId;
	}
	/**
	 * @param hidUserId The hidUserId to set.
	 */
	public void setHidUserId(String hidUserId) {
		this.hidUserId = hidUserId;
	}
	/**
	 * @return Returns the showCount.
	 */
	public String getShowCount() {
		return showCount;
	}
	/**
	 * @param showCount The showCount to set.
	 */
	public void setShowCount(String showCount) {
		this.showCount = showCount;
	}
	/**
	 * @return Returns the dutyHrs.
	 */
	public String getDutyHrs() {
		return dutyHrs;
	}
	/**
	 * @param dutyHrs The dutyHrs to set.
	 */
	public void setDutyHrs(String dutyHrs) {
		this.dutyHrs = dutyHrs;
	}
	/**
	 * @return Returns the employeeName.
	 */
	public String getEmployeeName() {
		return employeeName;
	}
	/**
	 * @param employeeName The employeeName to set.
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	/**
	 * @return Returns the employeeType.
	 */
	public String getEmployeeType() {
		return employeeType;
	}
	/**
	 * @param employeeType The employeeType to set.
	 */
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	/**
	 * @return Returns the machineShift.
	 */
	public String getMachineShift() {
		return machineShift;
	}
	/**
	 * @param machineShift The machineShift to set.
	 */
	public void setMachineShift(String machineShift) {
		this.machineShift = machineShift;
	}
	/**
	 * @return Returns the otHrs.
	 */
	public String getOtHrs() {
		return otHrs;
	}
	/**
	 * @param otHrs The otHrs to set.
	 */
	public void setOtHrs(String otHrs) {
		this.otHrs = otHrs;
	}
	/**
	 * @return Returns the popFromDate.
	 */
	public String getPopFromDate() {
		return popFromDate;
	}
	/**
	 * @param popFromDate The popFromDate to set.
	 */
	public void setPopFromDate(String popFromDate) {
		this.popFromDate = popFromDate;
	}
	/**
	 * @return Returns the popReason.
	 */
	public String getPopReason() {
		return popReason;
	}
	/**
	 * @param popReason The popReason to set.
	 */
	public void setPopReason(String popReason) {
		this.popReason = popReason;
	}
	/**
	 * @return Returns the formAction.
	 */
	public String getFormAction() {
		return formAction;
	}
	/**
	 * @param formAction The formAction to set.
	 */
	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}
	/**
	 * @return Returns the hidEmpTypId.
	 */
	public String getHidEmpTypId() {
		return hidEmpTypId;
	}
	/**
	 * @param hidEmpTypId The hidEmpTypId to set.
	 */
	public void setHidEmpTypId(String hidEmpTypId) {
		this.hidEmpTypId = hidEmpTypId;
	}
	/**
	 * @return Returns the hidEmpDet.
	 */
	public String getHidEmpDet() {
		return hidEmpDet;
	}
	/**
	 * @param hidEmpDet The hidEmpDet to set.
	 */
	public void setHidEmpDet(String hidEmpDet) {
		this.hidEmpDet = hidEmpDet;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{ 
		ActionErrors errors = new ActionErrors();
			
		return errors;
	}	
	/**
	 * @return Returns the hidPopDet.
	 */
	public String getHidPopDet() {
		return hidPopDet;
	}
	/**
	 * @param hidPopDet The hidPopDet to set.
	 */
	public void setHidPopDet(String hidPopDet) {
		this.hidPopDet = hidPopDet;
	}
}

/***
$Log: PayOutProductionAddForm.java,v $
Revision 1.7  2005/06/16 06:19:15  vkrishnamoorthy
Machine Code removed.

Revision 1.6  2005/05/30 13:23:26  vkrishnamoorthy
Modified as per log entries.

Revision 1.5  2005/03/11 13:11:45  sponnusamy
Pop Production Completed.

Revision 1.4  2005/02/03 05:09:15  vkrishnamoorthy
Log added.

***/
