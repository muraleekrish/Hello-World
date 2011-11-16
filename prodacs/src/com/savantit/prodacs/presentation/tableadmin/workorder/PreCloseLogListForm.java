/*
 * Created on Nov 4, 2004
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
public class PreCloseLogListForm extends ActionForm 
{
	private String preCloseLogFromDate = "";
	private String preCloseLogToDate = "";
	private String preCloseLogJobName = "0";
	private String preCloseLogWOHash = "0";
	private String jobNameText = "";
	private String woNoText = "";

	private String listValidEntries = "all";
	private String formAction = "";
	private String maxItems = "15";
	private String page = "1";
	private String sortField = "WO_NO";
	private String sortOrder = "ascending";   	
	
	/**
	 * @return Returns the preCloseLogFromDate.
	 */
	public String getPreCloseLogFromDate() {
		return preCloseLogFromDate;
	}
	/**
	 * @param preCloseLogFromDate The preCloseLogFromDate to set.
	 */
	public void setPreCloseLogFromDate(String preCloseLogFromDate) {
		this.preCloseLogFromDate = preCloseLogFromDate;
	}
	/**
	 * @return Returns the preCloseLogJobName.
	 */
	public String getPreCloseLogJobName() {
		return preCloseLogJobName;
	}
	/**
	 * @param preCloseLogJobName The preCloseLogJobName to set.
	 */
	public void setPreCloseLogJobName(String preCloseLogJobName) {
		this.preCloseLogJobName = preCloseLogJobName;
	}
	/**
	 * @return Returns the preCloseLogToDate.
	 */
	public String getPreCloseLogToDate() {
		return preCloseLogToDate;
	}
	/**
	 * @param preCloseLogToDate The preCloseLogToDate to set.
	 */
	public void setPreCloseLogToDate(String preCloseLogToDate) {
		this.preCloseLogToDate = preCloseLogToDate;
	}
	/**
	 * @return Returns the preCloseLogWOHash.
	 */
	public String getPreCloseLogWOHash() {
		return preCloseLogWOHash;
	}
	/**
	 * @param preCloseLogWOHash The preCloseLogWOHash to set.
	 */
	public void setPreCloseLogWOHash(String preCloseLogWOHash) {
		this.preCloseLogWOHash = preCloseLogWOHash;
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
	 * @return Returns the jobNameText.
	 */
	public String getJobNameText() {
		return jobNameText;
	}
	/**
	 * @param jobNameText The jobNameText to set.
	 */
	public void setJobNameText(String jobNameText) {
		this.jobNameText = jobNameText;
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
	 * @return Returns the woNoText.
	 */
	public String getWoNoText() {
		return woNoText;
	}
	/**
	 * @param woNoText The woNoText to set.
	 */
	public void setWoNoText(String woNoText) {
		this.woNoText = woNoText;
	}
}
/***
$Log: PreCloseLogListForm.java,v $
Revision 1.2  2004/12/21 11:23:08  sponnusamy
PreCloseLog Controller is fully completed.

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/