/*
 * Created on Mar 22, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.securityadministration;

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
public class GroupListAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action="failure";
		if (form instanceof GroupListForm)
		{
			//GroupListForm frm=(GroupListForm) form;
			action="success";
		}
		return mapping.findForward(action);
	}
}


/***
$Log: GroupListAction.java,v $
Revision 1.1  2005/03/26 10:10:56  vkrishnamoorthy
Initial commit on Prodacs.

***/
