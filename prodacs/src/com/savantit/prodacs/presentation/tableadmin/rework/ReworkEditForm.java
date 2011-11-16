/*
 * Created on Nov 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.rework;

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
public class ReworkEditForm extends ActionForm 
{
	private String reworkReason = "";
	private String reworkCategory = "";
	
	private String formAction="";
	private int id;
	private String[] ids = new String[0];

	/**
	 * @return Returns the reworkReason.
	 */
	public String getReworkReason() {
		return reworkReason;
	}
	/**
	 * @param reworkReason The reworkReason to set.
	 */
	public void setReworkReason(String reworkReason) {
		this.reworkReason = reworkReason;
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
	 * @return Returns the reworkCategory.
	 */
	public String getReworkCategory() {
		return reworkCategory;
	}
	/**
	 * @param reworkCategory The reworkCategory to set.
	 */
	public void setReworkCategory(String reworkCategory) {
		this.reworkCategory = reworkCategory;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		if (formAction.equalsIgnoreCase("update"))
		{
			if (reworkReason == null || reworkReason.trim().equals(""))
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","ReworkReason"));
			}
		}
		return errors;
	}
}
/***
$Log: ReworkEditForm.java,v $
Revision 1.3  2005/01/19 11:29:39  vkrishnamoorthy
Error Messages thrown

Revision 1.2  2004/11/22 10:42:28  sponnusamy
Rework forms are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/