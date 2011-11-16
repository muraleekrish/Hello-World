/*
 * Created on Jan 3, 2005
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
public class RadialProductionListForm extends ActionForm
{
	private String proFromDate = "";
	private String proToDate = "";
	private String proJobName = "";
	private String proWorkOrder = "0";
	private String proMachineCode = "0";
	private String postedView = "no";
	private String jobSelect = "0";
	private String proShiftName = "0";
	
	private String formAction = "";
	private String maxItems = "15";
	private String page = "1";
	private String sortField = "MC_CDE";
	private String sortOrder = "ascending";   	
	private String listValidEntries = "all";

	
	
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
	 * @return Returns the proJobName.
	 */
	public String getProJobName() {
		return proJobName;
	}
	/**
	 * @param proJobName The proJobName to set.
	 */
	public void setProJobName(String proJobName) {
		this.proJobName = proJobName;
	}
	/**
	 * @return Returns the proMachineCode.
	 */
	public String getProMachineCode() {
		return proMachineCode;
	}
	/**
	 * @param proMachineCode The proMachineCode to set.
	 */
	public void setProMachineCode(String proMachineCode) {
		this.proMachineCode = proMachineCode;
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
	 * @return Returns the proWorkOrder.
	 */
	public String getProWorkOrder() {
		return proWorkOrder;
	}
	/**
	 * @param proWorkOrder The proWorkOrder to set.
	 */
	public void setProWorkOrder(String proWorkOrder) {
		this.proWorkOrder = proWorkOrder;
	}
	/**
	 * @return Returns the jobSelect.
	 */
	public String getJobSelect() {
		return jobSelect;
	}
	/**
	 * @param jobSelect The jobSelect to set.
	 */
	public void setJobSelect(String jobSelect) {
		this.jobSelect = jobSelect;
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
	 * @return Returns the proShiftName.
	 */
	public String getProShiftName() {
		return proShiftName;
	}
	/**
	 * @param proShiftName The proShiftName to set.
	 */
	public void setProShiftName(String proShiftName) {
		this.proShiftName = proShiftName;
	}
}

/***
$Log: RadialProductionListForm.java,v $
Revision 1.6  2005/08/22 10:28:54  vkrishnamoorthy
ShiftName added in filter.

Revision 1.5  2005/05/19 09:35:48  vkrishnamoorthy
Unposted entry in filter made as default.

Revision 1.4  2005/05/19 04:17:42  vkrishnamoorthy
IsPosted entry added in Filter.

Revision 1.3  2005/02/03 05:09:15  vkrishnamoorthy
Log added.

***/