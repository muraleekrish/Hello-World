/*
 * Created on Oct 28, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.customer;

import org.apache.struts.action.ActionForm;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CustomerListForm extends ActionForm
{
	private String listCustomerName = "";
	private String listCustomerType = "0";
	private String listValidEntries = "all";
	private String showPage;
	private String formAction = "";
	private String maxItems = "15";
	private String page = "1";
	private String sortField = "CUST_NAME";
	private String sortOrder = "ascending";   	
    private String search;
    private String custCombo = "0";

    private String searchText = "";
    private String viewValidEntries = "all";
    
    

	
	
	/**
	 * @return Returns the listCustomerName.
	 */
	public String getListCustomerName() {
		return listCustomerName;
	}
	/**
	 * @param listCustomerName The listCustomerName to set.
	 */
	public void setListCustomerName(String listCustomerName) {
		this.listCustomerName = listCustomerName;
	}
	/**
	 * @return Returns the listCustomerType.
	 */
	public String getListCustomerType() {
		return listCustomerType;
	}
	/**
	 * @param listCustomerType The listCustomerType to set.
	 */
	public void setListCustomerType(String listCustomerType) {
		this.listCustomerType = listCustomerType;
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
	 * @return Returns the search.
	 */
	public String getSearch() {
		return search;
	}
	/**
	 * @param search The search to set.
	 */
	public void setSearch(String search) {
		this.search = search;
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
	 * @return Returns the showPage.
	 */
	public String getShowPage() {
		return showPage;
	}
	/**
	 * @param showPage The showPage to set.
	 */
	public void setShowPage(String showPage) {
		this.showPage = showPage;
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
	 * @return Returns the viewValidEntries.
	 */
	public String getViewValidEntries() {
		return viewValidEntries;
	}
	/**
	 * @param viewValidEntries The viewValidEntries to set.
	 */
	public void setViewValidEntries(String viewValidEntries) {
		this.viewValidEntries = viewValidEntries;
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
$Log: CustomerListForm.java,v $
Revision 1.4  2005/02/16 05:40:24  vkrishnamoorthy
Field names initialization added.

Revision 1.3  2005/02/12 09:11:45  sponnusamy
Value maintained through session

Revision 1.2  2004/11/16 15:39:52  sponnusamy
Customer pages are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/