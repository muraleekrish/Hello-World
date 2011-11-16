/*
 * Created on Nov 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.jobmaster;

import org.apache.struts.action.ActionForm;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class JobMasterListForm extends ActionForm 
{
	private String show = "";
	private String showPage = "";
	private String formAction="";
	private String maxItems="15";
	private String page="1";
	private String sortField="CUST_NAME";
	private String sortOrder="ascending";
	private String validEntry = "all";

	private String drawingNo = "";	
	private String drawingSearchTab = "0";
	private String txtCustomerName = "";
	private String searchCustomerName = "0";
	private String txtJobName = "";
	private String searchJobName = "0";
	private String txtMaterial = "";
	private String searchMaterial = "0";

	
	/**
	 * @return Returns the show.
	 */
	public String getShow() {
		return show;
	}
	/**
	 * @param show The show to set.
	 */
	public void setShow(String show) {
		this.show = show;
	}
	/**
	 * @return Returns the drawingNo.
	 */
	public String getDrawingNo() {
		return drawingNo;
	}
	/**
	 * @param drawingNo The drawingNo to set.
	 */
	public void setDrawingNo(String drawingNo) {
		this.drawingNo = drawingNo;
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
	 * @return Returns the drawingSearchTab.
	 */
	public String getDrawingSearchTab() {
		return drawingSearchTab;
	}
	/**
	 * @param drawingSearchTab The drawingSearchTab to set.
	 */
	public void setDrawingSearchTab(String drawingSearchTab) {
		this.drawingSearchTab = drawingSearchTab;
	}
	/**
	 * @return Returns the searchCustomerName.
	 */
	public String getSearchCustomerName() {
		return searchCustomerName;
	}
	/**
	 * @param searchCustomerName The searchCustomerName to set.
	 */
	public void setSearchCustomerName(String searchCustomerName) {
		this.searchCustomerName = searchCustomerName;
	}
	/**
	 * @return Returns the searchJobName.
	 */
	public String getSearchJobName() {
		return searchJobName;
	}
	/**
	 * @param searchJobName The searchJobName to set.
	 */
	public void setSearchJobName(String searchJobName) {
		this.searchJobName = searchJobName;
	}
	/**
	 * @return Returns the searchMaterial.
	 */
	public String getSearchMaterial() {
		return searchMaterial;
	}
	/**
	 * @param searchMaterial The searchMaterial to set.
	 */
	public void setSearchMaterial(String searchMaterial) {
		this.searchMaterial = searchMaterial;
	}
	/**
	 * @return Returns the txtCustomerName.
	 */
	public String getTxtCustomerName() {
		return txtCustomerName;
	}
	/**
	 * @param txtCustomerName The txtCustomerName to set.
	 */
	public void setTxtCustomerName(String txtCustomerName) {
		this.txtCustomerName = txtCustomerName;
	}
	/**
	 * @return Returns the txtJobName.
	 */
	public String getTxtJobName() {
		return txtJobName;
	}
	/**
	 * @param txtJobName The txtJobName to set.
	 */
	public void setTxtJobName(String txtJobName) {
		this.txtJobName = txtJobName;
	}
	/**
	 * @return Returns the txtMaterial.
	 */
	public String getTxtMaterial() {
		return txtMaterial;
	}
	/**
	 * @param txtMaterial The txtMaterial to set.
	 */
	public void setTxtMaterial(String txtMaterial) {
		this.txtMaterial = txtMaterial;
	}
}
/***
$Log: JobMasterListForm.java,v $
Revision 1.2  2004/12/03 06:40:48  sponnusamy
JobMaster controller is completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/