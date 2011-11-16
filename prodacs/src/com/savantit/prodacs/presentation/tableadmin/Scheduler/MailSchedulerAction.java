/*
 * Created on Jan 7, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.Scheduler;

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
public class MailSchedulerAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		
		/*Creating an instance of MailSchedulerForm.java*/
		if (form instanceof MailSchedulerForm)
		{
			//MailSchedulerForm frm = (MailSchedulerForm) form;
			action="success";
		}
		if (BuildConfig.DMODE)
			System.out.println("Mail Scheduler Action:"+action);
		return mapping.findForward(action);
	}
}


/***
 $Log: MailSchedulerAction.java,v $
 Revision 1.1  2006/01/10 07:28:47  vkrishnamoorthy
 Initial commit on ProDACS.

***/
