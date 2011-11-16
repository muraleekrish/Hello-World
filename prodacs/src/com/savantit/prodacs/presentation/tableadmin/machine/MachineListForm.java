/*
 * Created on Nov 1, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.machine;

import org.apache.struts.action.ActionForm;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MachineListForm extends ActionForm 
{
	private String machineName = "";
	private String machineType = "0";
	private String machineValidEntries = "0";
	private String formAction = "";
	private String maxItems = "15";
	private String page = "1";
	private String sortField = "MC_CDE";
	private String sortOrder = "ascending";
	private String machineSearchTab = "0";
	
	/**
	 * @return Returns the machineName.
	 */
	public String getMachineName() {
		return machineName;
	}
	/**
	 * @param machineName The machineName to set.
	 */
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	/**
	 * @return Returns the machineType.
	 */
	public String getMachineType() {
		return machineType;
	}
	/**
	 * @param machineType The machineType to set.
	 */
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	/**
	 * @return Returns the machineValidEntries.
	 */
	public String getMachineValidEntries() {
		return machineValidEntries;
	}
	/**
	 * @param machineValidEntries The machineValidEntries to set.
	 */
	public void setMachineValidEntries(String machineValidEntries) {
		this.machineValidEntries = machineValidEntries;
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
	 * @return Returns the machineSearchTab.
	 */
	public String getMachineSearchTab() {
		return machineSearchTab;
	}
	/**
	 * @param machineSearchTab The machineSearchTab to set.
	 */
	public void setMachineSearchTab(String machineSearchTab) {
		this.machineSearchTab = machineSearchTab;
	}
}
/***
$Log: MachineListForm.java,v $
Revision 1.2  2004/11/19 13:26:42  sponnusamy
Machine Forms are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/