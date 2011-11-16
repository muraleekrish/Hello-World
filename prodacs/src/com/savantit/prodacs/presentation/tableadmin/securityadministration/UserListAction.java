/*
 * Created on Jan 6, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class UserListAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action="failure";
		if (form instanceof UserListForm)
		{
			//UserListForm frm=(UserListForm) form;
			action="success";
		}
		return mapping.findForward(action);
	}
}


/***
$Log: UserListAction.java,v $
Revision 1.1  2005/03/31 06:09:46  vkrishnamoorthy
User Module completed.

Revision 1.3  2005/02/11 11:21:49  sponnusamy
Warnings removed

Revision 1.2  2005/02/03 05:15:01  vkrishnamoorthy
Log added.

***/