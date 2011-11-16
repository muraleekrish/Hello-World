/*
 * Created on Oct 30, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.employee;

import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.savantit.prodacs.infra.util.BuildConfig;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class EmployeeEditForm extends ActionForm 
{
	private String employeeName;
	private String employeeCode;
	private String employeeType;
	private String employeeStatus;
	private String employeeDOJ;
	private String employeeDOB;
	private String employeeInService="";
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String pincode;
	private String modifyEmployee;
	private String modifyCancel;
	private String returnEmployeeList;
	private String bloodGroup = "";
	private String phone1 = "";
	private String phone2 = "";
	private String address3 = "";
	private String address4 = "";
	private String city1 = "";
	private String state1 = "";
	private String pincode1 = "";
	private String phone3 = "";
	private String phone4 = "";
	private String contactName = "";
	
	private String formAction="";
	private int id;
	private String[] ids = new String[0];
	

	
	/**
	 * @return Returns the address3.
	 */
	public String getAddress3() {
		return address3;
	}
	/**
	 * @param address3 The address3 to set.
	 */
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	/**
	 * @return Returns the address4.
	 */
	public String getAddress4() {
		return address4;
	}
	/**
	 * @param address4 The address4 to set.
	 */
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	/**
	 * @return Returns the bloodGroup.
	 */
	public String getBloodGroup() {
		return bloodGroup;
	}
	/**
	 * @param bloodGroup The bloodGroup to set.
	 */
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	/**
	 * @return Returns the city1.
	 */
	public String getCity1() {
		return city1;
	}
	/**
	 * @param city1 The city1 to set.
	 */
	public void setCity1(String city1) {
		this.city1 = city1;
	}
	/**
	 * @return Returns the contactName.
	 */
	public String getContactName() {
		return contactName;
	}
	/**
	 * @param contactName The contactName to set.
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * @return Returns the phone1.
	 */
	public String getPhone1() {
		return phone1;
	}
	/**
	 * @param phone1 The phone1 to set.
	 */
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	/**
	 * @return Returns the phone2.
	 */
	public String getPhone2() {
		return phone2;
	}
	/**
	 * @param phone2 The phone2 to set.
	 */
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	/**
	 * @return Returns the phone3.
	 */
	public String getPhone3() {
		return phone3;
	}
	/**
	 * @param phone3 The phone3 to set.
	 */
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	/**
	 * @return Returns the phone4.
	 */
	public String getPhone4() {
		return phone4;
	}
	/**
	 * @param phone4 The phone4 to set.
	 */
	public void setPhone4(String phone4) {
		this.phone4 = phone4;
	}
	/**
	 * @return Returns the pincode1.
	 */
	public String getPincode1() {
		return pincode1;
	}
	/**
	 * @param pincode1 The pincode1 to set.
	 */
	public void setPincode1(String pincode1) {
		this.pincode1 = pincode1;
	}
	/**
	 * @return Returns the state1.
	 */
	public String getState1() {
		return state1;
	}
	/**
	 * @param state1 The state1 to set.
	 */
	public void setState1(String state1) {
		this.state1 = state1;
	}
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
	 * @return Returns the employeeDOB.
	 */
	public String getEmployeeDOB() {
		return employeeDOB;
	}
	/**
	 * @param employeeDOB The employeeDOB to set.
	 */
	public void setEmployeeDOB(String employeeDOB) {
		this.employeeDOB = employeeDOB;
	}
	/**
	 * @return Returns the employeeDOJ.
	 */
	public String getEmployeeDOJ() {
		return employeeDOJ;
	}
	/**
	 * @param employeeDOJ The employeeDOJ to set.
	 */
	public void setEmployeeDOJ(String employeeDOJ) {
		this.employeeDOJ = employeeDOJ;
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
	 * @return Returns the employeeStatus.
	 */
	public String getEmployeeStatus() {
		return employeeStatus;
	}
	/**
	 * @param employeeStatus The employeeStatus to set.
	 */
	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
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
	 * @return Returns the modifyCancel.
	 */
	public String getModifyCancel() {
		return modifyCancel;
	}
	/**
	 * @param modifyCancel The modifyCancel to set.
	 */
	public void setModifyCancel(String modifyCancel) {
		this.modifyCancel = modifyCancel;
	}
	/**
	 * @return Returns the modifyEmployee.
	 */
	public String getModifyEmployee() {
		return modifyEmployee;
	}
	/**
	 * @param modifyEmployee The modifyEmployee to set.
	 */
	public void setModifyEmployee(String modifyEmployee) {
		this.modifyEmployee = modifyEmployee;
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
	 * @return Returns the returnEmployeeList.
	 */
	public String getReturnEmployeeList() {
		return returnEmployeeList;
	}
	/**
	 * @param returnEmployeeList The returnEmployeeList to set.
	 */
	public void setReturnEmployeeList(String returnEmployeeList) {
		this.returnEmployeeList = returnEmployeeList;
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
	/**
	 * @return Returns the employeeInService.
	 */
	public String getEmployeeInService() {
		return employeeInService;
	}
	/**
	 * @param employeeInService The employeeInService to set.
	 */
	public void setEmployeeInService(String employeeInService) {
		this.employeeInService = employeeInService;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		if (formAction.equalsIgnoreCase("update"))
		{
			if (employeeType==null || employeeType.trim().equals("0"))
			{
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.common.error.required","EmployeeType"));
			}
			if (employeeStatus==null || employeeStatus.trim().equals("0"))
			{
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.common.error.required","EmployeeStatus"));
			}
			int year = 0;
			int month = 0;
			int day = 0;
			int yr = 0;
			int mon = 0;
			int d = 0;
			String[] f_months = {"01","03","05","07","09","11"};
			String[] h_months = {"04","06","08","10","12"};
			String q_month = "02";
			if (!employeeDOJ.equalsIgnoreCase(""))
			{
				StringTokenizer stDate = new StringTokenizer(employeeDOJ,"-");
				if (stDate.hasMoreTokens())
				{
					year = Integer.parseInt(stDate.nextToken().trim());
				}
				if (stDate.hasMoreTokens())
				{
					month = Integer.parseInt(stDate.nextToken().trim());
				}
				if (stDate.hasMoreTokens())
				{
					day = Integer.parseInt(stDate.nextToken().trim());
				}
				GregorianCalendar gc = new GregorianCalendar(year,month-1,day);
				boolean isLeap = gc.isLeapYear(year);
				if (BuildConfig.DMODE)
					System.out.println("IsLeap :"+isLeap);
				if (!isLeap)
				{
					for (int i = 0; i < f_months.length; i++)
					{
						if (month == Integer.parseInt(f_months[i]))
						{
							if (day > 31)
							{
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.date","Date of Join"));
							}
						}
					}
					for (int j = 0; j < h_months.length; j++)
					{
						if (month == Integer.parseInt(h_months[j]))
						{
							if (day > 30)
							{
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.date","Date of Join"));
							}
						}
					}
					if (month == Integer.parseInt(q_month))
					{
						if (day > 28)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.date","Date of Join"));
						}
					}
				}
				else
				{
					for (int i = 0; i < f_months.length; i++)
					{
						if (month == Integer.parseInt(f_months[i]))
						{
							if (day > 31)
							{
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.date","Date of Join"));
							}
						}
					}
					for (int j = 0; j < h_months.length; j++)
					{
						if (month == Integer.parseInt(h_months[j]))
						{
							if (day > 30)
							{
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.date","Date of Join"));
							}
						}
					}
					if (month == Integer.parseInt(q_month))
					{
						if (day > 29)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.date","Date of Join"));
						}
					}
				}
			}

			if (!employeeDOB.equalsIgnoreCase(""))
			{
				StringTokenizer st = new StringTokenizer(employeeDOB,"-");
				if (st.hasMoreTokens())
				{
					yr = Integer.parseInt(st.nextToken().trim());
				}
				if (st.hasMoreTokens())
				{
					mon = Integer.parseInt(st.nextToken().trim());
				}
				if (st.hasMoreTokens())
				{
					d = Integer.parseInt(st.nextToken().trim());
				}
				GregorianCalendar gc = new GregorianCalendar(yr,mon-1,d);
				boolean isLeap = gc.isLeapYear(year);
				if (BuildConfig.DMODE)
					System.out.println("IsLeap1 :"+isLeap);
				if (!isLeap)
				{
					for (int i = 0; i < f_months.length; i++)
					{
						if (mon == Integer.parseInt(f_months[i]))
						{
							if (d > 31)
							{
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.date","Date of Birth"));
							}
						}
					}
					for (int j = 0; j < h_months.length; j++)
					{
						if (mon == Integer.parseInt(h_months[j]))
						{
							if (d > 30)
							{
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.date","Date of Birth"));
							}
						}
					}
					if (mon == Integer.parseInt(q_month))
					{
						if (d > 28)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.date","Date of Birth"));
						}
					}
				}
				else
				{
					for (int i = 0; i < f_months.length; i++)
					{
						if (mon == Integer.parseInt(f_months[i]))
						{
							if (d > 31)
							{
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.date","Date of Birth"));
							}
						}
					}
					for (int j = 0; j < h_months.length; j++)
					{
						if (mon == Integer.parseInt(h_months[j]))
						{
							if (d > 30)
							{
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.date","Date of Birth"));
							}
						}
					}
					if (mon == Integer.parseInt(q_month))
					{
						if (d > 29)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.date","Date of Birth"));
						}
					}
				}
			}
		}
		return errors;
	}
}
/***
$Log: EmployeeEditForm.java,v $
Revision 1.11  2005/08/17 10:19:24  vkrishnamoorthy
Fields added.

Revision 1.10  2005/08/01 06:26:30  vkrishnamoorthy
Employee code added.

Revision 1.9  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.8  2005/05/27 11:58:44  vkrishnamoorthy
Date validations remodified.

Revision 1.7  2005/05/11 13:47:24  vkrishnamoorthy
Date Validations added.

Revision 1.6  2005/03/02 13:17:22  vkrishnamoorthy
Modified for date validation.

Revision 1.5  2005/01/19 11:25:33  vkrishnamoorthy
Error messages are throwed in Edit page.

Revision 1.4  2004/12/22 08:01:37  sponnusamy
In date field Null values are restricted.

Revision 1.3  2004/11/18 13:59:45  sponnusamy
Employee associated Forms are finished

Revision 1.2  2004/11/16 15:41:32  sponnusamy
EmployeeType problems are corrected

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/