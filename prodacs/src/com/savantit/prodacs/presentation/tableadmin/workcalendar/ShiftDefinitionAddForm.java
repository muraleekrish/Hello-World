/*
 * Created on Nov 1, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workcalendar;

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
public class ShiftDefinitionAddForm extends ActionForm 
{
	private String shiftName = "";
	private String shiftDescription = "";
	
	/**
	 * @return Returns the shiftDescription.
	 */
	public String getShiftDescription() {
		return shiftDescription;
	}
	/**
	 * @param shiftDescription The shiftDescription to set.
	 */
	public void setShiftDescription(String shiftDescription) {
		this.shiftDescription = shiftDescription;
	}
	/**
	 * @return Returns the shiftName.
	 */
	public String getShiftName() {
		return shiftName;
	}
	/**
	 * @param shiftName The shiftName to set.
	 */
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{ 
		ActionErrors errors = new ActionErrors();

		if(shiftName==null || shiftName.trim().length()<1 || shiftName.trim().equals(""))
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Shift Name"));
		
		return errors;
	}	

}
/***
$Log: ShiftDefinitionAddForm.java,v $
Revision 1.2  2004/12/03 11:51:44  sponnusamy
Shift Definition Controllers are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/