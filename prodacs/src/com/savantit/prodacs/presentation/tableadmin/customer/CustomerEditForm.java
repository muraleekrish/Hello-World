/*
 * Created on Oct 29, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.customer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CustomerEditForm extends ActionForm 
{
	private String customerName = "";
	private String customerType = "";
	private String customerInService = "";
	private String address1 = "";
	private String address2 = "";
	private String city = "";
	private String state = "";
	private String pincode = "";
	private String country = "";
	private String firstName = "";
	private String lastName = "";
	private String designation = "";
	private String phoneLine1 = "";
	private String extension1 = "";
	private String phoneLine2 = "";
	private String extension2 = "";
	private String phoneLine3 = "";
	private String extension3 = "";
	private String fax = "";
	private String mobile = "";
	private String email = "";
	private String website = "";
	private String modifyCustomer = "";
	private String addCancel;
	private String viewCustomerList;
	
	private String formAction="";
	private int id;
	private String[] ids = new String[0];

	

	/**
	 * @return Returns the designation.
	 */
	public String getDesignation() {
		return designation;
	}
	/**
	 * @param designation The designation to set.
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return Returns the extension1.
	 */
	public String getExtension1() {
		return extension1;
	}
	/**
	 * @param extension1 The extension1 to set.
	 */
	public void setExtension1(String extension1) {
		this.extension1 = extension1;
	}
	/**
	 * @return Returns the extension2.
	 */
	public String getExtension2() {
		return extension2;
	}
	/**
	 * @param extension2 The extension2 to set.
	 */
	public void setExtension2(String extension2) {
		this.extension2 = extension2;
	}
	/**
	 * @return Returns the extension3.
	 */
	public String getExtension3() {
		return extension3;
	}
	/**
	 * @param extension3 The extension3 to set.
	 */
	public void setExtension3(String extension3) {
		this.extension3 = extension3;
	}
	/**
	 * @return Returns the mobile.
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile The mobile to set.
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return Returns the phoneLine3.
	 */
	public String getPhoneLine3() {
		return phoneLine3;
	}
	/**
	 * @param phoneLine3 The phoneLine3 to set.
	 */
	public void setPhoneLine3(String phoneLine3) {
		this.phoneLine3 = phoneLine3;
	}
	/**
	 * @return Returns the website.
	 */
	public String getWebsite() {
		return website;
	}
	/**
	 * @param website The website to set.
	 */
	public void setWebsite(String website) {
		this.website = website;
	}
	/**
	 * @return Returns the addCancel.
	 */
	public String getAddCancel() {
		return addCancel;
	}
	/**
	 * @param addCancel The addCancel to set.
	 */
	public void setAddCancel(String addCancel) {
		this.addCancel = addCancel;
	}
	/**
	 * @return Returns the address1.
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param address1 The address1 to set.
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return Returns the address2.
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @param address2 The address2 to set.
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return Returns the city.
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return Returns the country.
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return Returns the customerInService.
	 */
	public String getCustomerInService() {
		return customerInService;
	}
	/**
	 * @param customerInService The customerInService to set.
	 */
	public void setCustomerInService(String customerInService) {
		this.customerInService = customerInService;
	}
	/**
	 * @return Returns the customerName.
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName The customerName to set.
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return Returns the customerType.
	 */
	public String getCustomerType() {
		return customerType;
	}
	/**
	 * @param customerType The customerType to set.
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	/**
	 * @return Returns the fax.
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax The fax to set.
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return Returns the lastName.
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName The lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return Returns the modifyCustomer.
	 */
	public String getModifyCustomer() {
		return modifyCustomer;
	}
	/**
	 * @param modifyCustomer The modifyCustomer to set.
	 */
	public void setModifyCustomer(String modifyCustomer) {
		this.modifyCustomer = modifyCustomer;
	}
	/**
	 * @return Returns the phoneLine1.
	 */
	public String getPhoneLine1() {
		return phoneLine1;
	}
	/**
	 * @param phoneLine1 The phoneLine1 to set.
	 */
	public void setPhoneLine1(String phoneLine1) {
		this.phoneLine1 = phoneLine1;
	}
	/**
	 * @return Returns the phoneLine2.
	 */
	public String getPhoneLine2() {
		return phoneLine2;
	}
	/**
	 * @param phoneLine2 The phoneLine2 to set.
	 */
	public void setPhoneLine2(String phoneLine2) {
		this.phoneLine2 = phoneLine2;
	}
	/**
	 * @return Returns the pincode.
	 */
	public String getPincode() {
		return pincode;
	}
	/**
	 * @param pincode The pincode to set.
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	/**
	 * @return Returns the state.
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state The state to set.
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return Returns the viewCustomerList.
	 */
	public String getViewCustomerList() {
		return viewCustomerList;
	}
	/**
	 * @param viewCustomerList The viewCustomerList to set.
	 */
	public void setViewCustomerList(String viewCustomerList) {
		this.viewCustomerList = viewCustomerList;
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
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
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
	
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		if (customerType == null || customerType.trim().equals("0"))
		{
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.common.error.required","CustomerType"));
		}
		return errors;
	}
	
}

/***
$Log: CustomerEditForm.java,v $
Revision 1.5  2005/08/17 09:12:33  vkrishnamoorthy
Fields added.

Revision 1.4  2005/01/19 06:26:10  vkrishnamoorthy
Exception message throwed.

Revision 1.3  2004/11/17 12:00:27  sponnusamy
Customer associated forms are modified

Revision 1.2  2004/11/16 15:39:52  sponnusamy
Customer pages are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/