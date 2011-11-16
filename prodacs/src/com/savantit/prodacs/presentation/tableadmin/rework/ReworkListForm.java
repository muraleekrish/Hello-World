/*
 * Created on Nov 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.rework;

import org.apache.struts.action.ActionForm;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ReworkListForm extends ActionForm 
{
	private String reworkCategory = "0";
	private String formAction = "";
	private String maxItems = "15";
	private String page = "1";
	private String sortField = "RWK_CATEGORY";
	private String sortOrder = "ascending";
	private String validEntry = "";

	
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
	 * @return Returns the validEntry.
	 */
	public String getValidEntry() {
		return validEntry;
	}
	/**
	 * @param validEntry The validEntry to set.
	 */
	public void setValidEntry(String validEntry) {
		this.validEntry = validEntry;
	}
	/**
	 * @return Returns the reworkCategory.
	 */
	public String getReworkCategory() {
		return reworkCategory;
	}
	/**
	 * @param reworkCategory The reworkCategory to set.
	 */
	public void setReworkCategory(String reworkCategory) {
		this.reworkCategory = reworkCategory;
	}
}
/***
$Log: ReworkListForm.java,v $
Revision 1.2  2004/11/22 10:42:28  sponnusamy
Rework forms are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/