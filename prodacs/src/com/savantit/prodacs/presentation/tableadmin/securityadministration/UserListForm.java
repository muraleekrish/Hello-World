/*
 * Created on Jan 6, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.securityadministration;

import org.apache.struts.action.ActionForm;

/**
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class UserListForm extends ActionForm 
{
	private String userId = "";
	private String uidSelection = "";
	private String firstName = "";
	private String firstNameSelect = "";
	private String company = "";
	private String companySelect = "";
	private String viewValidEntries = "";
	
	private String formAction = "";
	private String maxItems = "15";
	private String page = "1";
	private String sortField = "USER_NAME";
	private String sortOrder = "ascending";
	
	
	/**
	 * @return Returns the company.
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * @param company The company to set.
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * @return Returns the companySelect.
	 */
	public String getCompanySelect() {
		return companySelect;
	}
	/**
	 * @param companySelect The companySelect to set.
	 */
	public void setCompanySelect(String companySelect) {
		this.companySelect = companySelect;
	}
	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return Returns the firstNameSelect.
	 */
	public String getFirstNameSelect() {
		return firstNameSelect;
	}
	/**
	 * @param firstNameSelect The firstNameSelect to set.
	 */
	public void setFirstNameSelect(String firstNameSelect) {
		this.firstNameSelect = firstNameSelect;
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
	/**
	 * @return Returns the uidSelection.
	 */
	public String getUidSelection() {
		return uidSelection;
	}
	/**
	 * @param uidSelection The uidSelection to set.
	 */
	public void setUidSelection(String uidSelection) {
		this.uidSelection = uidSelection;
	}
	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
$Log: UserListForm.java,v $
Revision 1.1  2005/03/31 06:09:46  vkrishnamoorthy
User Module completed.

Revision 1.2  2005/02/03 05:15:01  vkrishnamoorthy
Log added.

***/