/*
 * Created on Nov 8, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.production;

import org.apache.struts.action.ActionForm;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CheckMachineAccntngForm extends ActionForm
{
	private String frmDate = "";
	private String toDate = "";
	private String machCode = "0";
	private String formAction = "";
	
	
	/**
	 * @return Returns the frmDate.
	 */
	public String getFrmDate() {
		return frmDate;
	}
	/**
	 * @param frmDate The frmDate to set.
	 */
	public void setFrmDate(String frmDate) {
		this.frmDate = frmDate;
	}
	/**
	 * @return Returns the machCode.
	 */
	public String getMachCode() {
		return machCode;
	}
	/**
	 * @param machCode The machCode to set.
	 */
	public void setMachCode(String machCode) {
		this.machCode = machCode;
	}
	/**
	 * @return Returns the toDate.
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
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
$Log: CheckMachineAccntngForm.java,v $
Revision 1.1  2005/11/08 14:44:28  vkrishnamoorthy
Initial commit on ProDACS.

***/