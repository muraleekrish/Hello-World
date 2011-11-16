/*
 * Created on Mar 8, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.machine;

import org.apache.struts.action.ActionForm;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MachineTypeEditForm extends ActionForm
{
	private String machineTypeName = "";
	private String description = "";
	private String machineId = "";
	
	private String formAction="";
	private int id;
	private String[] ids = new String[0];
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
	 * @return Returns the machineTypeName.
	 */
	public String getMachineTypeName() {
		return machineTypeName;
	}
	/**
	 * @param machineTypeName The machineTypeName to set.
	 */
	public void setMachineTypeName(String machineTypeName) {
		this.machineTypeName = machineTypeName;
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
	 * @return Returns the machineId.
	 */
	public String getMachineId() {
		return machineId;
	}
	/**
	 * @param machineId The machineId to set.
	 */
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
}



/***
$Log: MachineTypeEditForm.java,v $
Revision 1.1  2005/03/08 07:19:38  vkrishnamoorthy
initial commit on MachineType.

***/