/*
 * Created on Sep 1, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.production;

import org.apache.struts.action.ActionForm;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WOJobStatusListForm  extends ActionForm
{
    private String proFromDate = "";
	private String proToDate = "";
	private String proJobName = "";
	private String jobSelect = "0";
	private String proDrawingNo = "";
	private String drawingSelect = "0";
	private String custType = "0";
	private String custName = "0";
	
	private String formAction = "";
	private String maxItems = "3";	
	
	private String page = "1";
	private String showPage;
	
	
	/**
	 * @return Returns the custName.
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @param custName The custName to set.
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
	/**
	 * @return Returns the custType.
	 */
	public String getCustType() {
		return custType;
	}
	/**
	 * @param custType The custType to set.
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}
	/**
	 * @return Returns the drawingSelect.
	 */
	public String getDrawingSelect() {
		return drawingSelect;
	}
	/**
	 * @param drawingSelect The drawingSelect to set.
	 */
	public void setDrawingSelect(String drawingSelect) {
		this.drawingSelect = drawingSelect;
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
	 * @return Returns the proDrawingNo.
	 */
	public String getProDrawingNo() {
		return proDrawingNo;
	}
	/**
	 * @param proDrawingNo The proDrawingNo to set.
	 */
	public void setProDrawingNo(String proDrawingNo) {
		this.proDrawingNo = proDrawingNo;
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
}



/***
$Log: WOJobStatusListForm.java,v $
Revision 1.1  2005/09/01 10:10:06  vkrishnamoorthy
Initial commit on ProDACS.

***/