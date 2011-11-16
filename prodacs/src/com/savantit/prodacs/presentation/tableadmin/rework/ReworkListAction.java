/*
 * Created on Nov 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.rework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.savantit.prodacs.infra.util.BuildConfig;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ReworkListAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/*Creating an instance of ReworkListForm.java*/
		if (form instanceof ReworkListForm)
		{
			//ReworkListForm frm = (ReworkListForm) form;
			action="success";
		}
		if (BuildConfig.DMODE)
			System.out.println("Rework List:"+action);
		return mapping.findForward(action);
	}
}
/***
$Log: ReworkListAction.java,v $
Revision 1.3  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.2  2005/02/11 11:21:49  sponnusamy
Warnings removed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/