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
public class MasterLoginForm extends ActionForm 
{
	private String userId = "";
	private String passWord = "";
	private String masterUserId = "";
	private String masterUserPwd = "";
	private String formAction = "";
	
	
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
	 * @return Returns the masterUserId.
	 */
	public String getMasterUserId() {
		return masterUserId;
	}
	/**
	 * @param masterUserId The masterUserId to set.
	 */
	public void setMasterUserId(String masterUserId) {
		this.masterUserId = masterUserId;
	}
	/**
	 * @return Returns the masterUserPwd.
	 */
	public String getMasterUserPwd() {
		return masterUserPwd;
	}
	/**
	 * @param masterUserPwd The masterUserPwd to set.
	 */
	public void setMasterUserPwd(String masterUserPwd) {
		this.masterUserPwd = masterUserPwd;
	}
	/**
	 * @return Returns the passWord.
	 */
	public String getPassWord() {
		return passWord;
	}
	/**
	 * @param passWord The passWord to set.
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
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
}


/***
$Log: MasterLoginForm.java,v $
Revision 1.1  2005/12/08 13:02:01  vkrishnamoorthy
Initial commit on ProDACS.

***/