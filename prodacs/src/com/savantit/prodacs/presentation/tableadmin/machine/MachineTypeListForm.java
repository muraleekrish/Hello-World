/*
 * Created on Mar 6, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.machine;

import org.apache.struts.action.ActionForm;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MachineTypeListForm extends ActionForm 
{
	private String machineType = "";
	
	private String showPage;
	private String formAction = "";
	private String maxItems = "15";
	private String page = "1";
	private String sortField = "MC_TYP_NAME";
	private String sortOrder = "ascending";   	
	
	private String search;
	private String searchText = "";
	private String viewValidEntries = "all";
	

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
	 * @return Returns the machineType.
	 */
	public String getMachineType() {
		return machineType;
	}
	/**
	 * @param machineType The machineType to set.
	 */
	public void setMachineType(String machineType) {
		this.machineType = machineType;
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
}

/***
$Log: MachineTypeListForm.java,v $
Revision 1.1  2005/03/08 07:19:38  vkrishnamoorthy
initial commit on MachineType.

***/