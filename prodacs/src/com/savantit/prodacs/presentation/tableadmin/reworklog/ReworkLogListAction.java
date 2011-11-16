/*
 * Created on Jan 5, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.reworklog;

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
public class ReworkLogListAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action="failure";
		if (form instanceof ReworkLogListForm)
		{
			/* Creating a instance of ReworkListForm*/
			//ReworkLogListForm frm = (ReworkLogListForm) form;
			
			action="success";
		}
		return mapping.findForward(action);
	}
}

/**
$Log: ReworkLogListAction.java,v $
Revision 1.3  2005/02/11 11:21:50  sponnusamy
Warnings removed

Revision 1.2  2005/01/12 15:23:20  sponnusamy
List page properties included.

 **/
