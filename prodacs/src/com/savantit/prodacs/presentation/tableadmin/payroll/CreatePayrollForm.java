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
public class CreatePayrollForm extends ActionForm 
{
	private String cmbPayrollCycle = "0";
	private String formAction = "";
	private String maxItems = "15";
	private String page = "1";
	private String ids = "";
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
	 * @return Returns the maxItems.
	 */
	public String getMaxItems() {
		return maxItems;
	}
	/**
	 * @param maxItems The maxItems to set.
	 */
	public void setMaxItems(String maxItems) {
		this.maxItems = maxItems;
	}
	/**
	 * @return Returns the page.
	 */
	public String getPage() {
		return page;
	}
	/**
	 * @param page The page to set.
	 */
	public void setPage(String page) {
		this.page = page;
	}
	/**
	 * @return Returns the ids.
	 */
	public String getIds() {
		return ids;
	}
	/**
	 * @param ids The ids to set.
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}
}

/***
$Log: CreatePayrollForm.java,v $
Revision 1.3  2005/06/11 07:01:06  vkrishnamoorthy
UserName added as parameter while passing.

Revision 1.2  2005/01/22 10:25:30  sponnusamy
Payroll interface completed

***/
