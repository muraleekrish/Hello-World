/*
 * Created on Feb 14, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.employee;

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
public class NewStatusNameForm extends ActionForm
{
	private int id = 0;
	private String employeeStatus = "";
	
	
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
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		if(employeeStatus==null || employeeStatus.trim().length()<1 || employeeStatus.trim().equals(""))
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Employee Status"));

		return errors;		
	}
}

/***
$Log: NewStatusNameForm.java,v $
Revision 1.2  2005/02/16 11:49:01  vkrishnamoorthy
Modified as per NewStatusName adding.

Revision 1.1  2005/02/14 11:55:12  vkrishnamoorthy
Initial commit on NewStatusNameForm.

***/