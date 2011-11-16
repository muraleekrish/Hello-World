/*
 * Created on Jan 4, 2005
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
public class NonProductionListForm extends ActionForm 
{
	private String nonProShiftName = "0";
	private String nonProMachineCode = "0";
	private String nonProFromDate = "";
	private String nonProToDate = "";
	private String nonProdValid = "all";
	private String nonProdEmplName = "";
	private String emplSelect = "0";
	private String postedView = "no";
	
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
	 * @return Returns the nonProdValid.
	 */
	public String getNonProdValid() {
		return nonProdValid;
	}
	/**
	 * @param nonProdValid The nonProdValid to set.
	 */
	public void setNonProdValid(String nonProdValid) {
		this.nonProdValid = nonProdValid;
	}
	/**
	 * @return Returns the nonProFromDate.
	 */
	public String getNonProFromDate() {
		return nonProFromDate;
	}
	/**
	 * @param nonProFromDate The nonProFromDate to set.
	 */
	public void setNonProFromDate(String nonProFromDate) {
		this.nonProFromDate = nonProFromDate;
	}
	/**
	 * @return Returns the nonProMachineCode.
	 */
	public String getNonProMachineCode() {
		return nonProMachineCode;
	}
	/**
	 * @param nonProMachineCode The nonProMachineCode to set.
	 */
	public void setNonProMachineCode(String nonProMachineCode) {
		this.nonProMachineCode = nonProMachineCode;
	}
	/**
	 * @return Returns the nonProShiftName.
	 */
	public String getNonProShiftName() {
		return nonProShiftName;
	}
	/**
	 * @param nonProShiftName The nonProShiftName to set.
	 */
	public void setNonProShiftName(String nonProShiftName) {
		this.nonProShiftName = nonProShiftName;
	}
	/**
	 * @return Returns the nonProToDate.
	 */
	public String getNonProToDate() {
		return nonProToDate;
	}
	/**
	 * @param nonProToDate The nonProToDate to set.
	 */
	public void setNonProToDate(String nonProToDate) {
		this.nonProToDate = nonProToDate;
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
	 * @return Returns the nonProdEmplName.
	 */
	public String getNonProdEmplName() {
		return nonProdEmplName;
	}
	/**
	 * @param nonProdEmplName The nonProdEmplName to set.
	 */
	public void setNonProdEmplName(String nonProdEmplName) {
		this.nonProdEmplName = nonProdEmplName;
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
}

/***
$Log: NonProductionListForm.java,v $
Revision 1.5  2005/10/24 08:59:31  vkrishnamoorthy
Employee Name added in filter.

Revision 1.4  2005/05/19 09:35:48  vkrishnamoorthy
Unposted entry in filter made as default.

Revision 1.3  2005/05/19 04:17:42  vkrishnamoorthy
IsPosted entry added in Filter.

Revision 1.2  2005/02/03 05:09:15  vkrishnamoorthy
Log added.

***/