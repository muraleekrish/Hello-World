/*
 * Created on Feb 2, 2005
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
public class MachineTypeAddForm extends ActionForm
{
	private String machineTypeName = "";
	private String description = "";
	private int id = 0;
	
	

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
}

/***
 $Log: MachineTypeAddForm.java,v $
 Revision 1.3  2005/03/08 07:20:14  vkrishnamoorthy
 Modified according to MachineTypeAdd.

 Revision 1.2  2005/02/17 07:26:19  vkrishnamoorthy
 Modified as per NewMachineType adding.

 Revision 1.1  2005/02/03 04:45:16  vkrishnamoorthy
 Initial commit on MachineTypeAddAction.

 ***/
