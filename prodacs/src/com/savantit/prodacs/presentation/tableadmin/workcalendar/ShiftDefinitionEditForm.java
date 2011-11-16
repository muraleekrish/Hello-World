/*
 * Created on Nov 1, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workcalendar;

import org.apache.struts.action.ActionForm;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ShiftDefinitionEditForm extends ActionForm 
{
	private String shiftName;
	private String shiftDescription;
	private String modifyShiftDefinition;
	private String modifyShiftDefinitionCancel;
	private String retShiftDefinitionList;
	
	private String formAction="";
	private int id;
	private String[] ids = new String[0];
	
	/**
	 * @return Returns the modifyShiftDefinition.
	 */
	public String getModifyShiftDefinition() {
		return modifyShiftDefinition;
	}
	/**
	 * @param modifyShiftDefinition The modifyShiftDefinition to set.
	 */
	public void setModifyShiftDefinition(String modifyShiftDefinition) {
		this.modifyShiftDefinition = modifyShiftDefinition;
	}
	/**
	 * @return Returns the modifyShiftDefinitionCancel.
	 */
	public String getModifyShiftDefinitionCancel() {
		return modifyShiftDefinitionCancel;
	}
	/**
	 * @param modifyShiftDefinitionCancel The modifyShiftDefinitionCancel to set.
	 */
	public void setModifyShiftDefinitionCancel(
			String modifyShiftDefinitionCancel) {
		this.modifyShiftDefinitionCancel = modifyShiftDefinitionCancel;
	}
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
	/**
	 * @return Returns the retShiftDefinitionList.
	 */
	public String getRetShiftDefinitionList() {
		return retShiftDefinitionList;
	}
	/**
	 * @param retShiftDefinitionList The retShiftDefinitionList to set.
	 */
	public void setRetShiftDefinitionList(String retShiftDefinitionList) {
		this.retShiftDefinitionList = retShiftDefinitionList;
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
}
/***
$Log: ShiftDefinitionEditForm.java,v $
Revision 1.2  2004/12/03 11:51:44  sponnusamy
Shift Definition Controllers are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/