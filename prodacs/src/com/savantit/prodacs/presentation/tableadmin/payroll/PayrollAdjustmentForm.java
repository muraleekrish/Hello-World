/*
 * Created on Jan 4, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.payroll;

import org.apache.struts.action.ActionForm;

/**
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PayrollAdjustmentForm extends ActionForm
{
	private String shift = "0";
	private String employeeType = "0";
	private String employeeName = "0";
	private String rdPayAdjust = "";
	private String regularSalaryHrs = "";
	private String otSalaryHrs = "";
	private String incSalHrs = "";
	private String payrollCycle = "0";
	private String formAction = "";
	private String sortOrder = "ascending";   	
	private String payrollDet = "";
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
	 * @return Returns the incSalHrs.
	 */
	public String getIncSalHrs() {
		return incSalHrs;
	}
	/**
	 * @param incSalHrs The incSalHrs to set.
	 */
	public void setIncSalHrs(String incSalHrs) {
		this.incSalHrs = incSalHrs;
	}
	/**
	 * @return Returns the otSalaryHrs.
	 */
	public String getOtSalaryHrs() {
		return otSalaryHrs;
	}
	/**
	 * @param otSalaryHrs The otSalaryHrs to set.
	 */
	public void setOtSalaryHrs(String otSalaryHrs) {
		this.otSalaryHrs = otSalaryHrs;
	}
	/**
	 * @return Returns the payrollCycle.
	 */
	public String getPayrollCycle() {
		return payrollCycle;
	}
	/**
	 * @param payrollCycle The payrollCycle to set.
	 */
	public void setPayrollCycle(String payrollCycle) {
		this.payrollCycle = payrollCycle;
	}
	/**
	 * @return Returns the rdPayAdjust.
	 */
	public String getRdPayAdjust() {
		return rdPayAdjust;
	}
	/**
	 * @param rdPayAdjust The rdPayAdjust to set.
	 */
	public void setRdPayAdjust(String rdPayAdjust) {
		this.rdPayAdjust = rdPayAdjust;
	}
	/**
	 * @return Returns the regularSalaryHrs.
	 */
	public String getRegularSalaryHrs() {
		return regularSalaryHrs;
	}
	/**
	 * @param regularSalaryHrs The regularSalaryHrs to set.
	 */
	public void setRegularSalaryHrs(String regularSalaryHrs) {
		this.regularSalaryHrs = regularSalaryHrs;
	}
	/**
	 * @return Returns the shift.
	 */
	public String getShift() {
		return shift;
	}
	/**
	 * @param shift The shift to set.
	 */
	public void setShift(String shift) {
		this.shift = shift;
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
	 * @return Returns the sortOrder.
	 */
	public String getSortOrder() {
		return sortOrder;
	}
	/**
	 * @param sortOrder The sortOrder to set.
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	/**
	 * @return Returns the payrollDet.
	 */
	public String getPayrollDet() {
		return payrollDet;
	}
	/**
	 * @param payrollDet The payrollDet to set.
	 */
	public void setPayrollDet(String payrollDet) {
		this.payrollDet = payrollDet;
	}
}

/***
$Log: PayrollAdjustmentForm.java,v $
Revision 1.3  2005/06/11 07:01:06  vkrishnamoorthy
UserName added as parameter while passing.

Revision 1.2  2005/01/25 05:19:22  sponnusamy
Payroll Administration connected with EJB methods.

***/
