/*
 * Created on Apr 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.payroll;

import org.apache.struts.action.ActionForm;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PayrollIdForm extends ActionForm
{
	private String fromDate = "";
	private String toDate = "";
	private String payrollCreated = "0";
	private String payrollClosed = "0"; 
	private String fromDateSelect = "0";
	private String toDateSelect = "0";
	
	private String formAction="";
	private String maxItems="15";
	private String page="1";
	private String sortField="PYRL_CYCLE_STAT_FROM_DATE";
	private String sortOrder="ascending";   	

	/**
	 * @return Returns the fromDateSelect.
	 */
	public String getFromDateSelect() {
		return fromDateSelect;
	}
	/**
	 * @param fromDateSelect The fromDateSelect to set.
	 */
	public void setFromDateSelect(String fromDateSelect) {
		this.fromDateSelect = fromDateSelect;
	}
	/**
	 * @return Returns the toDateSelect.
	 */
	public String getToDateSelect() {
		return toDateSelect;
	}
	/**
	 * @param toDateSelect The toDateSelect to set.
	 */
	public void setToDateSelect(String toDateSelect) {
		this.toDateSelect = toDateSelect;
	}
	/**
	 * @return Returns the fromDate.
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return Returns the payrollclosed.
	 */
	public String getPayrollClosed() {
		return payrollClosed;
	}
	/**
	 * @param payrollclosed The payrollclosed to set.
	 */
	public void setPayrollClosed(String payrollClosed) {
		this.payrollClosed = payrollClosed;
	}
	/**
	 * @return Returns the payrollCreated.
	 */
	public String getPayrollCreated() {
		return payrollCreated;
	}
	/**
	 * @param payrollCreated The payrollCreated to set.
	 */
	public void setPayrollCreated(String payrollCreated) {
		this.payrollCreated = payrollCreated;
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
	 * @return Returns the sortField.
	 */
	public String getSortField() {
		return sortField;
	}
	/**
	 * @param sortField The sortField to set.
	 */
	public void setSortField(String sortField) {
		this.sortField = sortField;
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
}



/***
$Log: PayrollIdForm.java,v $
Revision 1.1  2005/04/22 09:09:33  vkrishnamoorthy
Initial commit on ProDACS.

***/