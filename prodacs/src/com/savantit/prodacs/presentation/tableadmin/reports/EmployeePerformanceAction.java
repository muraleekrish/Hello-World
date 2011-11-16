/*
 * Created on Dec 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.reports;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.savantit.prodacs.infra.util.BuildConfig;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmployeePerformanceAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/*Creating an instance of EmployeePerformanceForm.java*/
		if (form instanceof EmployeePerformanceForm)
		{
			action="success";
		}
		if (BuildConfig.DMODE)
		{
			System.out.println("IEmployeePerformance Action:"+action);
		}
		return mapping.findForward(action);
	}
}


/***
$Log: EmployeePerformanceAction.java,v $
Revision 1.1  2005/12/27 09:09:47  vkrishnamoorthy
Initial commit on ProDACS.

***/