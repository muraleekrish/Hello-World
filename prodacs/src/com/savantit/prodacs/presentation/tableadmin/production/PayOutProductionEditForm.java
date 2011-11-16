/*
 * Created on Dec 31, 2004
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
public class PayOutProductionEditForm extends ActionForm 
{
	private String machineShift = "0";
	private String employeeType = "0";
	private String employeeName = "0";
	private String dutyHrs = "";
	private String otHrs = "";
	private String popReason = "";
	private String popFromDate = "";
	private String hidEmpTypId = "";
	private String hidEmpDet = "";
	private String hidEmpDetails = "";
	private String hidUserId = "";
	
	private String formAction="";
	private int id;
	private String[] ids = new String[0];

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
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return Returns the ids.
	 */
	public String[] getIds() {
		return ids;
	}
	/**
	 * @param ids The ids to set.
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
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
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{ 
		ActionErrors errors = new ActionErrors();
		return errors;
	}	

	/**
	 * @return Returns the hidEmpDetails.
	 */
	public String getHidEmpDetails() {
		return hidEmpDetails;
	}
	/**
	 * @param hidEmpDetails The hidEmpDetails to set.
	 */
	public void setHidEmpDetails(String hidEmpDetails) {
		this.hidEmpDetails = hidEmpDetails;
	}
}

/***
$Log: PayOutProductionEditForm.java,v $
Revision 1.5  2005/06/16 06:32:35  vkrishnamoorthy
Machine Code removed.

Revision 1.4  2005/05/30 13:23:26  vkrishnamoorthy
Modified as per log entries.

Revision 1.3  2005/02/03 05:09:15  vkrishnamoorthy
Log added.

***/
