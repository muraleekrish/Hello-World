/*
 * Created on Mar 25, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.securityadministration;

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
public class GroupEditForm extends ActionForm
{
	private String groupName;
	private String groupDesc;
	private String[] selectedResource;
	
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
	 * @return Returns the groupDesc.
	 */
	public String getGroupDesc() {
		return groupDesc;
	}
	/**
	 * @param groupDesc The groupDesc to set.
	 */
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	/**
	 * @return Returns the groupName.
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName The groupName to set.
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	 * @return Returns the selectedResource.
	 */
	public String[] getSelectedResource() {
		return selectedResource;
	}
	/**
	 * @param selectedResource The selectedResource to set.
	 */
	public void setSelectedResource(String[] selectedResource) {
		this.selectedResource = selectedResource;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		if (formAction.equals("update"))
		{
			if (groupName==null || groupName.trim().length()<1 || groupName.trim().equals(""))
			{
				errors.add("groupName", new ActionError("prodacs.common.error.required","Group Name"));
			}

			if ((selectedResource==null) || (selectedResource.length == 0))
			{
				errors.add("selectedResource", new ActionError("prodacs.common.error.required","Atleast One Resource"));
			}
		}	
		return errors;		
	}
}


/***
$Log: GroupEditForm.java,v $
Revision 1.2  2005/05/14 06:55:29  vkrishnamoorthy
Mandatory field checking added.

Revision 1.1  2005/03/26 10:08:18  vkrishnamoorthy
Initial commit on Prodacs.

***/