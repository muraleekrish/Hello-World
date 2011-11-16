/*
 * Created on Dec 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.reports;

import org.apache.struts.action.ActionForm;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IdleBreakDownForm extends ActionForm
{
	private String formAction = "";
	private String startDate = "";
	private String endDate = "";
	private String machineCode = "0";
	
	
	/**
	 * @return Returns the machineCode.
	 */
	public String getMachineCode() {
		return machineCode;
	}
	/**
	 * @param machineCode The machineCode to set.
	 */
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	/**
	 * @return Returns the endDate.
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return Returns the startDate.
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
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
$Log: IdleBreakDownForm.java,v $
Revision 1.1  2005/12/27 09:09:47  vkrishnamoorthy
Initial commit on ProDACS.

***/