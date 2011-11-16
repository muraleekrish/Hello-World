/*
 * Created on Nov 4, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workorder;

import org.apache.struts.action.ActionForm;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DummyWorkOrderEditForm extends ActionForm 
{
	private String dyWOHash;
	private String dyCustomerName;
	private String dyJobName;
	private String dyGeneralName;
	private String dyDrawingHash;
	private String dyRevisionHash;
	private String dyMaterialType;
	private String modifyDummyWO;
	/* Used in DummyWorkOrderView.jsp page */
	private String retDyWOList;

	private String formAction = "";
	private int id;
	private String[] ids = new String[0];

	
	

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
	 * @return Returns the dyCustomerName.
	 */
	public String getDyCustomerName() {
		return dyCustomerName;
	}
	/**
	 * @param dyCustomerName The dyCustomerName to set.
	 */
	public void setDyCustomerName(String dyCustomerName) {
		this.dyCustomerName = dyCustomerName;
	}
	/**
	 * @return Returns the dyDrawingHash.
	 */
	public String getDyDrawingHash() {
		return dyDrawingHash;
	}
	/**
	 * @param dyDrawingHash The dyDrawingHash to set.
	 */
	public void setDyDrawingHash(String dyDrawingHash) {
		this.dyDrawingHash = dyDrawingHash;
	}
	/**
	 * @return Returns the dyGeneralName.
	 */
	public String getDyGeneralName() {
		return dyGeneralName;
	}
	/**
	 * @param dyGeneralName The dyGeneralName to set.
	 */
	public void setDyGeneralName(String dyGeneralName) {
		this.dyGeneralName = dyGeneralName;
	}
	/**
	 * @return Returns the dyJobName.
	 */
	public String getDyJobName() {
		return dyJobName;
	}
	/**
	 * @param dyJobName The dyJobName to set.
	 */
	public void setDyJobName(String dyJobName) {
		this.dyJobName = dyJobName;
	}
	/**
	 * @return Returns the dyMaterialType.
	 */
	public String getDyMaterialType() {
		return dyMaterialType;
	}
	/**
	 * @param dyMaterialType The dyMaterialType to set.
	 */
	public void setDyMaterialType(String dyMaterialType) {
		this.dyMaterialType = dyMaterialType;
	}
	/**
	 * @return Returns the dyRevisionHash.
	 */
	public String getDyRevisionHash() {
		return dyRevisionHash;
	}
	/**
	 * @param dyRevisionHash The dyRevisionHash to set.
	 */
	public void setDyRevisionHash(String dyRevisionHash) {
		this.dyRevisionHash = dyRevisionHash;
	}
	/**
	 * @return Returns the dyWOHash.
	 */
	public String getDyWOHash() {
		return dyWOHash;
	}
	/**
	 * @param dyWOHash The dyWOHash to set.
	 */
	public void setDyWOHash(String dyWOHash) {
		this.dyWOHash = dyWOHash;
	}
	/**
	 * @return Returns the modifyDummyWO.
	 */
	public String getModifyDummyWO() {
		return modifyDummyWO;
	}
	/**
	 * @param modifyDummyWO The modifyDummyWO to set.
	 */
	public void setModifyDummyWO(String modifyDummyWO) {
		this.modifyDummyWO = modifyDummyWO;
	}
	/**
	 * @return Returns the retDyWOList.
	 */
	public String getRetDyWOList() {
		return retDyWOList;
	}
	/**
	 * @param retDyWOList The retDyWOList to set.
	 */
	public void setRetDyWOList(String retDyWOList) {
		this.retDyWOList = retDyWOList;
	}
}
/***
$Log: DummyWorkOrderEditForm.java,v $
Revision 1.2  2005/02/07 12:11:27  sponnusamy
DummyWorkOrder is completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/