/*
 * Created on Oct 29, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.employee;

import org.apache.struts.action.ActionForm;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class EmployeeTypeEditForm extends ActionForm 
{
	private String employeeType;
	private String employeeDescription;
	private String requiredQty;
	private String modifyEmployeeType;
	private String returnEmployeeList;
	private String dutyHrs = "";
	private String otHrs = "";
	private String incentiveHrs = "";

	private String formAction="";
	private int id;
	private String[] ids = new String[0];
	private String employeeId;

	
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
	 * @return Returns the incentiveHrs.
	 */
	public String getIncentiveHrs() {
		return incentiveHrs;
	}
	/**
	 * @param incentiveHrs The incentiveHrs to set.
	 */
	public void setIncentiveHrs(String incentiveHrs) {
		this.incentiveHrs = incentiveHrs;
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
	 * @return Returns the employeeDescription.
	 */
	public String getEmployeeDescription() {
		return employeeDescription;
	}
	/**
	 * @param employeeDescription The employeeDescription to set.
	 */
	public void setEmployeeDescription(String employeeDescription) {
		this.employeeDescription = employeeDescription;
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
	 * @return Returns the modifyEmployeeType.
	 */
	public String getModifyEmployeeType() {
		return modifyEmployeeType;
	}
	/**
	 * @param modifyEmployeeType The modifyEmployeeType to set.
	 */
	public void setModifyEmployeeType(String modifyEmployeeType) {
		this.modifyEmployeeType = modifyEmployeeType;
	}
	/**
	 * @return Returns the requiredQty.
	 */
	public String getRequiredQty() {
		return requiredQty;
	}
	/**
	 * @param requiredQty The requiredQty to set.
	 */
	public void setRequiredQty(String requiredQty) {
		this.requiredQty = requiredQty;
	}
	/**
	 * @return Returns the returnEmployeeList.
	 */
	public String getReturnEmployeeList() {
		return returnEmployeeList;
	}
	/**
	 * @param returnEmployeeList The returnEmployeeList to set.
	 */
	public void setReturnEmployeeList(String returnEmployeeList) {
		this.returnEmployeeList = returnEmployeeList;
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
	 * @return Returns the employeeId.
	 */
	public String getEmployeeId() {
		return employeeId;
	}
	/**
	 * @param employeeId The employeeId to set.
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
}
/***
$Log: EmployeeTypeEditForm.java,v $
Revision 1.6  2005/06/17 09:16:11  vkrishnamoorthy
Duty, OT, Incentive Hrs added.

Revision 1.5  2005/05/26 13:44:12  vkrishnamoorthy
Team field removed.

Revision 1.4  2005/01/20 12:57:16  vkrishnamoorthy
Error Messages thrown

Revision 1.3  2004/11/16 15:41:32  sponnusamy
EmployeeType problems are corrected

Revision 1.2  2004/11/10 12:23:42  sponnusamy
EmployeeType  related form and actions

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/