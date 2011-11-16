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
public class ReworkAddForm extends ActionForm 
{
	private String reworkCategory = "0";
	private String reworkReason = "";
	private String newReworkCategory = "";
	
	
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
	 * @return Returns the newReworkCategory.
	 */
	public String getNewReworkCategory() {
		return newReworkCategory;
	}
	/**
	 * @param newReworkCategory The newReworkCategory to set.
	 */
	public void setNewReworkCategory(String newReworkCategory) {
		this.newReworkCategory = newReworkCategory;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		
		if (reworkCategory.equals("1"))
		{
			if(newReworkCategory==null || newReworkCategory.trim().length() < 1 || newReworkCategory.trim().equals(""))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","New Rework Category"));	

			if(reworkReason==null || reworkReason.trim().length()<1 || reworkReason.trim().equals(""))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Rework Reason"));
		}
		else
		{
			if(reworkCategory==null || reworkCategory.trim().equals("0"))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Rework Category"));	

			if(reworkReason==null || reworkReason.trim().length()<1 || reworkReason.trim().equals(""))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Rework Reason"));
		}	
		return errors;
	}	

}
/***
$Log: ReworkAddForm.java,v $
Revision 1.2  2004/12/22 08:02:38  sponnusamy
Rework Add forms modified.

Revision 1.1  2004/11/22 10:42:28  sponnusamy
Rework forms are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/