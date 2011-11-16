/*
 * Created on Mar 31, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
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
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LoginForm extends ActionForm
{
	private String userId = "";
	private String userPwd = "";
	private String formAction = "";
	private String cpuId = "";
	private String machId = "";
	private String driveId = "";
	
	
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
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		if (formAction.equalsIgnoreCase("login"))
		{
			if (userId == null || userId.equalsIgnoreCase("") || userId.trim().length() < 1)
			{
				errors.add("userId", new ActionError("prodacs.login.error.userName.required"));
			}
			
			if (userPwd == null || userPwd.equalsIgnoreCase("") || userPwd.trim().length() < 1)
			{
				errors.add("userPwd", new ActionError("prodacs.login.error.password.required"));
			}
		}
		return errors;	
	}
}



/***
$Log: LoginForm.java,v $
Revision 1.2  2005/12/08 12:58:53  vkrishnamoorthy
Modified for Master User Authentication.

Revision 1.1  2005/03/31 12:18:23  vkrishnamoorthy
Login process completed.

***/