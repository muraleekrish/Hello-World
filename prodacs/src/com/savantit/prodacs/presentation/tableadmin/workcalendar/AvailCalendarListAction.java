/*
 * Created on Nov 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workcalendar;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.savantit.prodacs.facade.SessionWorkCalendarDetailsManager;
import com.savantit.prodacs.facade.SessionWorkCalendarDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AvailCalendarListAction extends Action 
{
	public ActionForward execute(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/* creating instance of AvailCalendarListForm.java */
		if (form instanceof AvailCalendarListForm)
		{
			AvailCalendarListForm frm = (AvailCalendarListForm)form;

			/* EJBLocator */
			EJBLocator obj = new EJBLocator();
			ActionErrors errors = new ActionErrors();

			try
			{
				/* 	Setting the JNDI name and Environment 	*/
				obj.setJndiName("SessionWorkCalendarDetailsManagerBean");
			   	obj.setEnvironment();

			   	/* 	Creating the Home and Remote Objects 	*/
				SessionWorkCalendarDetailsManagerHome availCalHomeObj = (SessionWorkCalendarDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkCalendarDetailsManagerHome.class);
				SessionWorkCalendarDetailsManager availCalDefObj = (SessionWorkCalendarDetailsManager)PortableRemoteObject.narrow(availCalHomeObj.create(),SessionWorkCalendarDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("change"))
				{
					boolean applied = availCalDefObj.chooseAvbltyName(frm.getApplyId());
					
					if (applied == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.applied","Availability Calendar"));
						
						saveMessages(request,message);
						action="success";
					}
					else
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						action="failure";				  			
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			action = "failure";
		}
		if (BuildConfig.DMODE)
			System.out.println("AvailCalendar List: "+ action);
		return mapping.findForward(action);
	}
}
/***
$Log: AvailCalendarListAction.java,v $
Revision 1.4  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.3  2005/05/05 11:31:31  vkrishnamoorthy
Modified as per applying availability name.

Revision 1.2  2005/02/11 11:21:49  sponnusamy
Warnings removed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/