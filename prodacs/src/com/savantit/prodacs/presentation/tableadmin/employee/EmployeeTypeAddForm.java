/*
 * Created on Oct 29, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.employee;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class EmployeeTypeAddForm extends ActionForm  
{
	private String employeeType = "";
	private String employeeDescription="";
	private String requiredQty="";
	private String addEmployeeType;
	private String dutyHrs;
	private String otHrs;
	private String incentiveHrs;
	
	/**
	 * @return Returns the addEmployeeType.
	 */
	public String getAddEmployeeType() {
		return addEmployeeType;
	}
	/**
	 * @param addEmployeeType The addEmployeeType to set.
	 */
	public void setAddEmployeeType(String addEmployeeType) {
		this.addEmployeeType = addEmployeeType;
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
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{ 
		ActionErrors errors = new ActionErrors();

		if(employeeType==null || employeeType.trim().length()<1 || employeeType.trim().equals(""))
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Employee Type"));
		
		return errors;
	}	
}
/***
$Log: EmployeeTypeAddForm.java,v $
Revision 1.5  2005/06/17 06:38:59  vkrishnamoorthy
Duty, OT, Incentive Hrs added.

Revision 1.4  2005/05/26 13:44:12  vkrishnamoorthy
Team field removed.

Revision 1.3  2004/11/16 15:41:32  sponnusamy
EmployeeType problems are corrected

Revision 1.2  2004/11/10 12:23:42  sponnusamy
EmployeeType  related form and actions

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/