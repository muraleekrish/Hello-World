/*
 * Created on Nov 4, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workorder;

import org.apache.struts.action.ActionForm;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DummyWorkOrderListForm extends ActionForm 
{
	private String workOrderFromDate = "";
	private String workOrderToDate = "";
	private String customerText = "";
	private String custCombo = "0";
	private String workOrderStatus = "0";
	private String listValidEntries = "all";
	
	private String formAction = "";
	private String maxItems = "15";
	private String page = "1";
	private String sortField = "WO_NO";
	private String sortOrder = "ascending";   	
    private String searchText = "";

	
	/**
	 * @return Returns the custCombo.
	 */
	public String getCustCombo() {
		return custCombo;
	}
	/**
	 * @param custCombo The custCombo to set.
	 */
	public void setCustCombo(String custCombo) {
		this.custCombo = custCombo;
	}
	/**
	 * @return Returns the customerText.
	 */
	public String getCustomerText() {
		return customerText;
	}
	/**
	 * @param customerText The customerText to set.
	 */
	public void setCustomerText(String customerText) {
		this.customerText = customerText;
	}
	/**
	 * @return Returns the listValidEntries.
	 */
	public String getListValidEntries() {
		return listValidEntries;
	}
	/**
	 * @param listValidEntries The listValidEntries to set.
	 */
	public void setListValidEntries(String listValidEntries) {
		this.listValidEntries = listValidEntries;
	}
	/**
	 * @return Returns the workOrderFromDate.
	 */
	public String getWorkOrderFromDate() {
		return workOrderFromDate;
	}
	/**
	 * @param workOrderFromDate The workOrderFromDate to set.
	 */
	public void setWorkOrderFromDate(String workOrderFromDate) {
		this.workOrderFromDate = workOrderFromDate;
	}
	/**
	 * @return Returns the workOrderStatus.
	 */
	public String getWorkOrderStatus() {
		return workOrderStatus;
	}
	/**
	 * @param workOrderStatus The workOrderStatus to set.
	 */
	public void setWorkOrderStatus(String workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}
	/**
	 * @return Returns the workOrderToDate.
	 */
	public String getWorkOrderToDate() {
		return workOrderToDate;
	}
	/**
	 * @param workOrderToDate The workOrderToDate to set.
	 */
	public void setWorkOrderToDate(String workOrderToDate) {
		this.workOrderToDate = workOrderToDate;
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
	 * @return Returns the searchText.
	 */
	public String getSearchText() {
		return searchText;
	}
	/**
	 * @param searchText The searchText to set.
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
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
$Log: DummyWorkOrderListForm.java,v $
Revision 1.3  2005/02/07 12:11:27  sponnusamy
DummyWorkOrder is completed

Revision 1.2  2004/12/16 11:23:04  sponnusamy
DummyWorkOrder list Form Completed.

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/