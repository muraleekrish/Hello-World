/*
 * Created on Dec 30, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.production;

import org.apache.struts.action.ActionForm;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PayOutProductionListForm extends ActionForm
{
	private String popShiftName = "0";
	private String proToDate = "";
	private String proFromDate = "";
	private String postedView = "no";
	private String popEmplName = "";
	private String emplSelect = "0"; 
	
	private String formAction = "";
	private String maxItems = "15";
	private String page = "1";
	private String sortField = "POP_CRNT_DATE";
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
	 * @return Returns the popShiftName.
	 */
	public String getPopShiftName() {
		return popShiftName;
	}
	/**
	 * @param popShiftName The popShiftName to set.
	 */
	public void setPopShiftName(String popShiftName) {
		this.popShiftName = popShiftName;
	}
	/**
	 * @return Returns the proFromDate.
	 */
	public String getProFromDate() {
		return proFromDate;
	}
	/**
	 * @param proFromDate The proFromDate to set.
	 */
	public void setProFromDate(String proFromDate) {
		this.proFromDate = proFromDate;
	}
	/**
	 * @return Returns the proToDate.
	 */
	public String getProToDate() {
		return proToDate;
	}
	/**
	 * @param proToDate The proToDate to set.
	 */
	public void setProToDate(String proToDate) {
		this.proToDate = proToDate;
	}
	/**
	 * @return Returns the postedView.
	 */
	public String getPostedView() {
		return postedView;
	}
	/**
	 * @param postedView The postedView to set.
	 */
	public void setPostedView(String postedView) {
		this.postedView = postedView;
	}
	/**
	 * @return Returns the emplSelect.
	 */
	public String getEmplSelect() {
		return emplSelect;
	}
	/**
	 * @param emplSelect The emplSelect to set.
	 */
	public void setEmplSelect(String emplSelect) {
		this.emplSelect = emplSelect;
	}
	/**
	 * @return Returns the popEmplName.
	 */
	public String getPopEmplName() {
		return popEmplName;
	}
	/**
	 * @param popEmplName The popEmplName to set.
	 */
	public void setPopEmplName(String popEmplName) {
		this.popEmplName = popEmplName;
	}
}

/***
$Log: PayOutProductionListForm.java,v $
Revision 1.7  2005/10/24 10:11:05  vkrishnamoorthy
Emp name added and Values after viewing restored in list page's filter.

Revision 1.6  2005/06/16 06:19:15  vkrishnamoorthy
Machine Code removed.

Revision 1.5  2005/05/19 09:35:48  vkrishnamoorthy
Unposted entry in filter made as default.

Revision 1.4  2005/05/19 04:17:42  vkrishnamoorthy
IsPosted entry added in Filter.

Revision 1.3  2005/02/03 05:09:15  vkrishnamoorthy
Log added.

***/