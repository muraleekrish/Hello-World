/*
 * Created on Oct 28, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CustomerListAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		return mapping.findForward(action);
	}
}
/***
$Log: CustomerListAction.java,v $
Revision 1.2  2004/11/16 15:39:52  sponnusamy
Customer pages are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/