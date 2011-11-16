/*
 * Created on Mar 22, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.securityadministration;

import org.apache.struts.action.ActionForm;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GroupListForm extends ActionForm 
{
	private String groupName="";
	private String groupSelection="";
	private String viewValidEntries="";
	
	private String formAction = "";
	private String maxItems = "15";
	private String page = "1";
	private String sortField = "ROLE_NAME";
	private String sortOrder = "ascending";
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
	 * @return Returns the groupName.
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName The groupName to set.
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return Returns the groupSelection.
	 */
	public String getGroupSelection() {
		return groupSelection;
	}
	/**
	 * @param groupSelection The groupSelection to set.
	 */
	public void setGroupSelection(String groupSelection) {
		this.groupSelection = groupSelection;
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
$Log: GroupListForm.java,v $
Revision 1.1  2005/03/26 10:10:56  vkrishnamoorthy
Initial commit on Prodacs.

***/
