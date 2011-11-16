/*
 * Created on Nov 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.operationgroup;

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
public class OperationGroupAddForm extends ActionForm 
{
	private String operationGroupCode;
	private String machineName;
	private String machRelated;
	private String machDetsCheck;
	
	
	/**
	 * @return Returns the machDetsCheck.
	 */
	public String getMachDetsCheck() {
		return machDetsCheck;
	}
	/**
	 * @param machDetsCheck The machDetsCheck to set.
	 */
	public void setMachDetsCheck(String machDetsCheck) {
		this.machDetsCheck = machDetsCheck;
	}
	/**
	 * @return Returns the machRelated.
	 */
	public String getMachRelated() {
		return machRelated;
	}
	/**
	 * @param machRelated The machRelated to set.
	 */
	public void setMachRelated(String machRelated) {
		this.machRelated = machRelated;
	}
	/**
	 * @return Returns the machineName.
	 */
	public String getMachineName() {
		return machineName;
	}
	/**
	 * @param machineName The machineName to set.
	 */
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	/**
	 * @return Returns the operationGroupCode.
	 */
	public String getOperationGroupCode() {
		return operationGroupCode;
	}
	/**
	 * @param operationGroupCode The operationGroupCode to set.
	 */
	public void setOperationGroupCode(String operationGroupCode) {
		this.operationGroupCode = operationGroupCode;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{ 
		ActionErrors errors = new ActionErrors();

		if(operationGroupCode==null || operationGroupCode.trim().length()<1 || operationGroupCode.trim().equals(""))
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Operation Group Code"));

		if(machineName==null || machineName.equalsIgnoreCase("0"))
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Machine Name"));
			
		return errors;
	}	
}
/***
$Log: OperationGroupAddForm.java,v $
Revision 1.4  2005/07/14 12:23:57  vkrishnamoorthy
Machine related field added.

Revision 1.3  2005/03/03 07:01:21  vkrishnamoorthy
OperationGroupAdd modified.

Revision 1.2  2004/11/26 09:04:10  sponnusamy
Operation Group Forms are Completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/