/*
 * Created on Nov 3, 2004
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
public class WorkOrderListForm extends ActionForm
{
	private String workOrderFromDate = "";
	private String workOrderToDate = "";
	private String customer = "";
	private String workOrderStatus = "";
	private String woNo = "";
	
	private String formAction = "";
	private String maxItems = "15";
	private String page = "1";
	private String sortField = "WO_NO";
	private String sortOrder = "ascending";   	
    private String searchText = "";
    private String custCombo = "0";
    private String woCombo = "0";
    private String jbName = "";
    private String jbNameCombo = "0";
    private String dwgNo = "";
    private String dwgNoCombo = "0";
    
    
    
	/**
	 * @return Returns the jbName.
	 */
	public String getJbName() {
		return jbName;
	}
	/**
	 * @param jbName The jbName to set.
	 */
	public void setJbName(String jbName) {
		this.jbName = jbName;
	}
	/**
	 * @return Returns the jbNameCombo.
	 */
	public String getJbNameCombo() {
		return jbNameCombo;
	}
	/**
	 * @param jbNameCombo The jbNameCombo to set.
	 */
	public void setJbNameCombo(String jbNameCombo) {
		this.jbNameCombo = jbNameCombo;
	}
	/**
	 * @return Returns the dwgNo.
	 */
	public String getDwgNo() {
		return dwgNo;
	}
	/**
	 * @param dwgNo The dwgNo to set.
	 */
	public void setDwgNo(String dwgNo) {
		this.dwgNo = dwgNo;
	}
	/**
	 * @return Returns the dwgNoCombo.
	 */
	public String getDwgNoCombo() {
		return dwgNoCombo;
	}
	/**
	 * @param dwgNoCombo The dwgNoCombo to set.
	 */
	public void setDwgNoCombo(String dwgNoCombo) {
		this.dwgNoCombo = dwgNoCombo;
	}
	/**
	 * @return Returns the woCombo.
	 */
	public String getWoCombo() {
		return woCombo;
	}
	/**
	 * @param woCombo The woCombo to set.
	 */
	public void setWoCombo(String woCombo) {
		this.woCombo = woCombo;
	}
    private String listValidEntries = "all";

	
	
	/**
	 * @return Returns the woNo.
	 */
	public String getWoNo() {
		return woNo;
	}
	/**
	 * @param woNo The woNo to set.
	 */
	public void setWoNo(String woNo) {
		this.woNo = woNo;
	}
	/**
	 * @return Returns the customer.
	 */
	public String getCustomer() {
		return customer;
	}
	/**
	 * @param customer The customer to set.
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
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
}
/***
$Log: WorkOrderListForm.java,v $
Revision 1.4  2005/09/08 10:28:13  vkrishnamoorthy
Job Name and DwgNo added in filter.

Revision 1.3  2005/07/27 13:46:12  vkrishnamoorthy
WO# included in Filter.

Revision 1.2  2004/12/15 16:28:07  sponnusamy
WorkOrder controlling part is partially completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/