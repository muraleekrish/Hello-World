/*
 * Created on Nov 8, 2005
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

import com.savantit.prodacs.infra.util.BuildConfig;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CheckMachineAccntngAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/*Creating an instance of ProductionAcctngDetailsForm.java*/
		if (BuildConfig.DMODE)
			System.out.println("Check Production Accounting Action Starts");
		
		if (form instanceof CheckMachineAccntngForm)
		{
			CheckMachineAccntngForm frm = (CheckMachineAccntngForm) form;
		}
		return mapping.findForward(action);
	}
}

/***
$Log: CheckMachineAccntngAction.java,v $
Revision 1.1  2005/11/08 14:44:28  vkrishnamoorthy
Initial commit on ProDACS.

***/