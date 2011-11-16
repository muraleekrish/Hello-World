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
public class PayrollInterfaceForm extends ActionForm 
{
	private String cmbPayrollCycle = "0";
	private String sortby = "0";
	private String formAction = "";
	private String dateShiftEmpHrsDetails ="";
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
     * @return Returns the dateShiftEmpHrsDetails.
     */
    public String getDateShiftEmpHrsDetails()
    {
        return dateShiftEmpHrsDetails;
    }
    /**
     * @param dateShiftEmpHrsDetails The dateShiftEmpHrsDetails to set.
     */
    public void setDateShiftEmpHrsDetails(String dateShiftEmpHrsDetails)
    {
        this.dateShiftEmpHrsDetails = dateShiftEmpHrsDetails;
    }
	/**
	 * @return Returns the cmbPayrollCycle.
	 */
	public String getCmbPayrollCycle() {
		return cmbPayrollCycle;
	}
	/**
	 * @param cmbPayrollCycle The cmbPayrollCycle to set.
	 */
	public void setCmbPayrollCycle(String cmbPayrollCycle) {
		this.cmbPayrollCycle = cmbPayrollCycle;
	}
	/**
	 * @return Returns the sortby.
	 */
	public String getSortby() {
		return sortby;
	}
	/**
	 * @param sortby The sortby to set.
	 */
	public void setSortby(String sortby) {
		this.sortby = sortby;
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
}

/***
$Log: PayrollInterfaceForm.java,v $
Revision 1.6  2005/06/11 07:01:06  vkrishnamoorthy
UserName added as parameter while passing.

Revision 1.5  2005/04/24 09:21:50  kduraisamy
Getters, Setters created for dateShiftEmpHrsDetails.

Revision 1.4  2005/01/22 10:25:10  sponnusamy
Payroll interface completed

Revision 1.3  2005/01/21 14:58:40  sponnusamy
Log added.

***/
