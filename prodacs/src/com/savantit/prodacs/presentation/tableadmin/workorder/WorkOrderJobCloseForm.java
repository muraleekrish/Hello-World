/*
 * Created on Nov 4, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workorder;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class WorkOrderJobCloseForm extends ActionForm 
{
	private String jobClose;
	private String woFromDate = "";
	private String woToDate = "";
	private String woNo = "";
	private String woNoCombo = "0";
	private String customer = "";
	private String custCombo = "0";
	private String jbName = "";
	private String jbCombo = "0";
	
	private String formAction = "";
	private String[] ids = new String[0];
	private String maxItems = "15";
	private String page = "1";
	private String sortField = "WO_NO";
	private String sortOrder = "ascending";

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
	 * @return Returns the jobClose.
	 */
	public String getJobClose() {
		return jobClose;
	}
	/**
	 * @param jobClose The jobClose to set.
	 */
	public void setJobClose(String jobClose) {
		this.jobClose = jobClose;
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
	 * @return Returns the ids.
	 */
	public String[] getIds() {
		return ids;
	}
	/**
	 * @param ids The ids to set.
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{ 
		ActionErrors errors = new ActionErrors();
		return errors;
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
	/**
	 * @return Returns the customer.
	 */
	public String getCustomer() {
		return customer;
	}
	/**
	 * @param customer The customer to set.
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	/**
	 * @return Returns the jbCombo.
	 */
	public String getJbCombo() {
		return jbCombo;
	}
	/**
	 * @param jbCombo The jbCombo to set.
	 */
	public void setJbCombo(String jbCombo) {
		this.jbCombo = jbCombo;
	}
	/**
	 * @return Returns the jbName.
	 */
	public String getJbName() {
		return jbName;
	}
	/**
	 * @param jbName The jbName to set.
	 */
	public void setJbName(String jbName) {
		this.jbName = jbName;
	}
	/**
	 * @return Returns the woFromDate.
	 */
	public String getWoFromDate() {
		return woFromDate;
	}
	/**
	 * @param woFromDate The woFromDate to set.
	 */
	public void setWoFromDate(String woFromDate) {
		this.woFromDate = woFromDate;
	}
	/**
	 * @return Returns the woNo.
	 */
	public String getWoNo() {
		return woNo;
	}
	/**
	 * @param woNo The woNo to set.
	 */
	public void setWoNo(String woNo) {
		this.woNo = woNo;
	}
	/**
	 * @return Returns the woNoCombo.
	 */
	public String getWoNoCombo() {
		return woNoCombo;
	}
	/**
	 * @param woNoCombo The woNoCombo to set.
	 */
	public void setWoNoCombo(String woNoCombo) {
		this.woNoCombo = woNoCombo;
	}
	/**
	 * @return Returns the woToDate.
	 */
	public String getWoToDate() {
		return woToDate;
	}
	/**
	 * @param woToDate The woToDate to set.
	 */
	public void setWoToDate(String woToDate) {
		this.woToDate = woToDate;
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
}
/***
$Log: WorkOrderJobCloseForm.java,v $
Revision 1.3  2005/03/05 06:00:41  vkrishnamoorthy
FieldNames added for Filter.

Revision 1.2  2004/12/17 07:55:22  sponnusamy
WorkOrderJob Closing controller are completed.

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/