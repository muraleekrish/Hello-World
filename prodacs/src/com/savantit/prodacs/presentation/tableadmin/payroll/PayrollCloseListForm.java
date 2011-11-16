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
public class PayrollCloseListForm extends ActionForm 
{
	private String payrollCycle = "0";
	private String formAction = "";
	private String maxItems = "15";
	private String page = "1";
	private String ids = "";

	
	/**
	 * @return Returns the payrollcycle.
	 */
	public String getPayrollCycle() {
		return payrollCycle;
	}
	/**
	 * @param payrollcycle The payrollcycle to set.
	 */
	public void setPayrollCycle(String payrollCycle) {
		this.payrollCycle = payrollCycle;
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
}

/***
$Log: PayrollCloseListForm.java,v $
Revision 1.2  2005/01/25 05:19:22  sponnusamy
Payroll Administration connected with EJB methods.

***/
