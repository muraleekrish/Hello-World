/*
 * Created on May 26, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.employee;

import org.apache.struts.action.ActionForm;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmployeeTypeRuleForm extends ActionForm 
{
	private String team;
	private String empTypId;
	private String empTypName;
	private String formAction = "";
	private String empDets;
	
	
	/**
	 * @return Returns the empDets.
	 */
	public String getEmpDets() {
		return empDets;
	}
	/**
	 * @param empDets The empDets to set.
	 */
	public void setEmpDets(String empDets) {
		this.empDets = empDets;
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
	 * @return Returns the empTypId.
	 */
	public String getEmpTypId() {
		return empTypId;
	}
	/**
	 * @param empTypId The empTypId to set.
	 */
	public void setEmpTypId(String empTypId) {
		this.empTypId = empTypId;
	}
	/**
	 * @return Returns the team.
	 */
	public String getTeam() {
		return team;
	}
	/**
	 * @param team The team to set.
	 */
	public void setTeam(String team) {
		this.team = team;
	}
	/**
	 * @return Returns the empTypName.
	 */
	public String getEmpTypName() {
		return empTypName;
	}
	/**
	 * @param empTypName The empTypName to set.
	 */
	public void setEmpTypName(String empTypName) {
		this.empTypName = empTypName;
	}
}


/***
$Log: EmployeeTypeRuleForm.java,v $
Revision 1.1  2005/05/26 13:43:31  vkrishnamoorthy
Initial Commit on ProDACS.

***/