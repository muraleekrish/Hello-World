/*
 * Created on Jan 6, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.securityadministration;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class UserAddForm extends ActionForm 
{
	private String employeeType = "0";
	private String employeeName = "0";
	private String userId = "";
	private String userPwd = "";
	private String confirmPwd = "";
	private String description = "";
	private String firstName = "";
	private String lastName = "";
	private String title = "";
	private String dateOfBirth = "";
	private String company = "0";
	private String address1 = "";
	private String address2 = "";
	private String position = "";
	private String city = "";
	private String state = "";
	private String pinCode = "";
	private String country = "";
	private String mailId = "";
	private String housePhone = "";
	private String workPhone = "";
	private String extension = "";
	private String mobile = "";
	private String fax = "";
	private String[] groupNames;
	
	private String formAction = "";
	private String empName;
	private String custName;
	
	
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
	 * @return Returns the company.
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * @param company The company to set.
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * @return Returns the confirmPwd.
	 */
	public String getConfirmPwd() {
		return confirmPwd;
	}
	/**
	 * @param confirmPwd The confirmPwd to set.
	 */
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
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
	 * @return Returns the dateOfBirth.
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * @param dateOfBirth The dateOfBirth to set.
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the mailId.
	 */
	public String getMailId() {
		return mailId;
	}
	/**
	 * @param mailId The mailId to set.
	 */
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	/**
	 * @return Returns the extension.
	 */
	public String getExtension() {
		return extension;
	}
	/**
	 * @param extension The extension to set.
	 */
	public void setExtension(String extension) {
		this.extension = extension;
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
	 * @return Returns the pinCode.
	 */
	public String getPinCode() {
		return pinCode;
	}
	/**
	 * @param pinCode The pinCode to set.
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	/**
	 * @return Returns the position.
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position The position to set.
	 */
	public void setPosition(String position) {
		this.position = position;
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
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return Returns the userPwd.
	 */
	public String getUserPwd() {
		return userPwd;
	}
	/**
	 * @param userPwd The userPwd to set.
	 */
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	/**
	 * @return Returns the housePhone.
	 */
	public String getHousePhone() {
		return housePhone;
	}
	/**
	 * @param housePhone The housePhone to set.
	 */
	public void setHousePhone(String housePhone) {
		this.housePhone = housePhone;
	}
	/**
	 * @return Returns the workPhone.
	 */
	public String getWorkPhone() {
		return workPhone;
	}
	/**
	 * @param workPhone The workPhone to set.
	 */
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
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
	 * @return Returns the employeeType.
	 */
	public String getEmployeeType() {
		return employeeType;
	}
	/**
	 * @param employeeType The employeeType to set.
	 */
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	/**
	 * @return Returns the groupNames.
	 */
	public String[] getGroupNames() {
		return groupNames;
	}
	/**
	 * @param groupNames The groupNames to set.
	 */
	public void setGroupNames(String[] groupNames) {
		this.groupNames = groupNames;
	}
	/**
	 * @return Returns the empName.
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName The empName to set.
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		if (formAction.equalsIgnoreCase("add"))
		{
			if (userId == null || userId.trim().equalsIgnoreCase("") || userId.trim().length() < 1)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","UserName"));
			
			if (userPwd == null || userPwd.trim().equalsIgnoreCase("") || userPwd.trim().length() < 1)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","UserPassword"));

			if (confirmPwd == null || confirmPwd.trim().equalsIgnoreCase("") || confirmPwd.trim().length() < 1)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","UserPassword Confirm"));
			
			if (groupNames == null)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","GroupNames"));
			
			if (firstName == null || firstName.trim().equalsIgnoreCase("") || firstName.trim().length() < 1)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","FirstName"));
			
			if (lastName == null || lastName.trim().equalsIgnoreCase("") || lastName.trim().length() < 1)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","LastName"));			
		}
		return errors;		
	}
}


/***
$Log: UserAddForm.java,v $
Revision 1.4  2005/07/18 13:47:47  vkrishnamoorthy
User Company modified.

Revision 1.3  2005/06/01 04:34:10  vkrishnamoorthy
User Password Confirm made as mandatory field.

Revision 1.2  2005/03/31 07:51:10  vkrishnamoorthy
Fax Field added.

Revision 1.1  2005/03/31 06:09:46  vkrishnamoorthy
User Module completed.

Revision 1.2  2005/02/03 05:15:01  vkrishnamoorthy
Log added.

***/