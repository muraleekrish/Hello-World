/*
 * Created on Sep 1, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.production;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WOJobStatusListAction  extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/*Creating an instance of ProductionListForm.java*/
		if (form instanceof WOJobStatusListForm)
		{
			//ProductionListForm frm = (ProductionListForm) form;
			action="success";
		}
		return mapping.findForward(action);
	}
}


/***
$Log: WOJobStatusListAction.java,v $
Revision 1.1  2005/09/01 10:10:06  vkrishnamoorthy
Initial commit on ProDACS.

***/