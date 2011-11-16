/*
 * Created on Jul 18, 2005
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
 * @author ppalaniappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WorkOrderJobStatusListAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/*Creating an instance of ProductionListForm.java*/
		if (form instanceof WorkOrderJobStatusListForm)
		{
			//ProductionListForm frm = (ProductionListForm) form;
			action="success";
		}
		return mapping.findForward(action);
	}
}
/***
$Log: WorkOrderJobStatusListAction.java,v $
Revision 1.1  2005/07/18 13:05:36  ppalaniappan
Initial commit.

***/