/*
 * Created on Nov 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.operationgroup;

import org.apache.struts.action.ActionForm;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OperationGroupEditForm extends ActionForm 
{
	private String machineName;
	private String modifyOperationGroup;
	private String operationGroupCode = "";
	private String formAction="";
	private int id;
	private String[] ids = new String[0];
	private String machineNames = "";
	private String machRelated = "";
	private String machDetsCheck = "";
	
	
	/**
	 * @return Returns the modifyOperationGroup.
	 */
	public String getModifyOperationGroup() {
		return modifyOperationGroup;
	}
	/**
	 * @param modifyOperationGroup The modifyOperationGroup to set.
	 */
	public void setModifyOperationGroup(String modifyOperationGroup) {
		this.modifyOperationGroup = modifyOperationGroup;
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
	/**
	 * @return Returns the machineNames.
	 */
	public String getMachineNames() {
		return machineNames;
	}
	/**
	 * @param machineNames The machineNames to set.
	 */
	public void setMachineNames(String machineNames) {
		this.machineNames = machineNames;
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
}
/***
$Log: OperationGroupEditForm.java,v $
Revision 1.4  2005/07/14 16:20:04  vkrishnamoorthy
Modified as per MachineRelated values.

Revision 1.3  2005/03/03 09:57:57  vkrishnamoorthy
OperationGroupEdit modified.

Revision 1.2  2004/11/26 09:04:10  sponnusamy
Operation Group Forms are Completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/