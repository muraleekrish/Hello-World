/*
 * Created on Oct 29, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.employee;

import org.apache.struts.action.ActionForm;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class EmployeeListForm extends ActionForm 
{
	private String employeeName="";
	private String showPage;
	private String formAction="";
	private String maxItems="15";
	private String page="1";
	private String sortField="EMP_NAME";
	private String sortOrder="ascending";   	
    private String search;
    private String searchText="";
    private String viewValidEntries="no";
    private String employeeNameSearchTab = "";
    private String employeeTypeNameSearch = "0";
    private String employeeStatusSearch = "0";
    private String employeeCode = "";
    private String employeeCodeSearch = "0";
    
    
    
	/**
	 * @return Returns the employeeCode.
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}
	/**
	 * @param employeeCode The employeeCode to set.
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	/**
	 * @return Returns the employeeCodeSearch.
	 */
	public String getEmployeeCodeSearch() {
		return employeeCodeSearch;
	}
	/**
	 * @param employeeCodeSearch The employeeCodeSearch to set.
	 */
	public void setEmployeeCodeSearch(String employeeCodeSearch) {
		this.employeeCodeSearch = employeeCodeSearch;
	}
	/**
	 * @return Returns the employeeStatusSearch.
	 */
	public String getEmployeeStatusSearch() {
		return employeeStatusSearch;
	}
	/**
	 * @param employeeStatusSearch The employeeStatusSearch to set.
	 */
	public void setEmployeeStatusSearch(String employeeStatusSearch) {
		this.employeeStatusSearch = employeeStatusSearch;
	}
	/**
	 * @return Returns the employeeTypeNameSearch.
	 */
	public String getEmployeeTypeNameSearch() {
		return employeeTypeNameSearch;
	}
	/**
	 * @param employeeTypeNameSearch The employeeTypeNameSearch to set.
	 */
	public void setEmployeeTypeNameSearch(String employeeTypeNameSearch) {
		this.employeeTypeNameSearch = employeeTypeNameSearch;
	}
	/**
	 * @return Returns the employeeName.
	 */
	public String getEmployeeName() {
		return employeeName;
	}
	/**
	 * @param employeeName The employeeName to set.
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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
	 * @return Returns the search.
	 */
	public String getSearch() {
		return search;
	}
	/**
	 * @param search The search to set.
	 */
	public void setSearch(String search) {
		this.search = search;
	}
	/**
	 * @return Returns the searchText.
	 */
	public String getSearchText() {
		return searchText;
	}
	/**
	 * @param searchText The searchText to set.
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
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
	 * @return Returns the employeeNameSearchTab.
	 */
	public String getEmployeeNameSearchTab() {
		return employeeNameSearchTab;
	}
	/**
	 * @param employeeNameSearchTab The employeeNameSearchTab to set.
	 */
	public void setEmployeeNameSearchTab(String employeeNameSearchTab) {
		this.employeeNameSearchTab = employeeNameSearchTab;
	}
}
/***
$Log: EmployeeListForm.java,v $
Revision 1.6  2005/09/08 09:32:03  vkrishnamoorthy
View employee valid made default in filter.

Revision 1.5  2005/08/04 10:43:35  vkrishnamoorthy
Employee Code added to filter.

Revision 1.4  2005/06/08 05:36:02  vkrishnamoorthy
Filter modified for EmployeeStatus and EmployeeTypeName.

Revision 1.3  2004/11/18 13:59:45  sponnusamy
Employee associated Forms are finished

Revision 1.2  2004/11/16 15:41:32  sponnusamy
EmployeeType problems are corrected

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/