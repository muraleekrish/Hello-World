/*
 * Created on Dec 7, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.securityadministration;

import org.apache.struts.action.ActionForm;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SystemInfoForm extends ActionForm 
{
	private String formAction = "";
	private String cpuId = "";
	private String machId = "";
	private String driveId = "";
	private String userName = "";
	private String description = "";
	
	
	
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
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return Returns the cpuId.
	 */
	public String getCpuId() {
		return cpuId;
	}
	/**
	 * @param cpuId The cpuId to set.
	 */
	public void setCpuId(String cpuId) {
		this.cpuId = cpuId;
	}
	/**
	 * @return Returns the driveId.
	 */
	public String getDriveId() {
		return driveId;
	}
	/**
	 * @param driveId The driveId to set.
	 */
	public void setDriveId(String driveId) {
		this.driveId = driveId;
	}
	/**
	 * @return Returns the machId.
	 */
	public String getMachId() {
		return machId;
	}
	/**
	 * @param machId The machId to set.
	 */
	public void setMachId(String machId) {
		this.machId = machId;
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
}


/***
$Log: SystemInfoForm.java,v $
Revision 1.2  2005/12/15 07:30:21  vkrishnamoorthy
User information added in System Information screen.

Revision 1.1  2005/12/08 13:02:01  vkrishnamoorthy
Initial commit on ProDACS.

***/