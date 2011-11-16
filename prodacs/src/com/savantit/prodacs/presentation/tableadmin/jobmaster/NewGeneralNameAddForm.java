/*
 * Created on Feb 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.jobmaster;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author sponnusamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NewGeneralNameAddForm extends ActionForm 
{
	private int id = 0;
	private String generalName = "";
	
	
	/**
	 * @return Returns the generalName.
	 */
	public String getGeneralName() {
		return generalName;
	}
	/**
	 * @param generalName The generalName to set.
	 */
	public void setGeneralName(String generalName) {
		this.generalName = generalName;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{ 
		ActionErrors errors = new ActionErrors();

		if(generalName==null || generalName.trim().length()<1 || generalName.trim().equals(""))
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","General Name"));
		
		return errors;
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
}

/***
$Log: NewGeneralNameAddForm.java,v $
Revision 1.2  2005/02/17 04:12:28  vkrishnamoorthy
Modified as per NewGeneralName adding.

Revision 1.1  2005/02/05 11:55:49  sponnusamy
New General Name field added.

***/
