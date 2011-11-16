/*
 * Created on Nov 1, 2004
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

import com.savantit.prodacs.businessimplementation.workcalendar.ShiftDefnDetails;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
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
public class ShiftDefinitionAddAction extends Action 
{
	public ActionForward execute(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/* creating instance of ShiftDefinitionAddForm.java */
		if (form instanceof ShiftDefinitionAddForm)
		{
			ShiftDefinitionAddForm frm = (ShiftDefinitionAddForm)form;

			/* EJBLocator */
			EJBLocator obj = new EJBLocator();
			ActionErrors errors = new ActionErrors();
			try
			{
			/* 	Setting the JNDI name and Environment 	*/
			    obj.setJndiName("SessionWorkCalendarDetailsManagerBean");
			    obj.setEnvironment();

			/* 	Creating the Home and Remote Objects 	*/
				SessionWorkCalendarDetailsManagerHome shiftDefHomeObj = (SessionWorkCalendarDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkCalendarDetailsManagerHome.class);
				SessionWorkCalendarDetailsManager shiftDefObj = (SessionWorkCalendarDetailsManager)PortableRemoteObject.narrow(shiftDefHomeObj.create(),SessionWorkCalendarDetailsManager.class);

			/* Setting the form values to Details Variables */
				ShiftDefnDetails objShiftDefnDetails = new ShiftDefnDetails();
				
				objShiftDefnDetails.setShiftName(frm.getShiftName());
				objShiftDefnDetails.setShiftDesc(frm.getShiftDescription());
				
				boolean added = shiftDefObj.addShiftDefinition(objShiftDefnDetails);
				
				if (added == true)
				{
					ActionMessages message = new ActionMessages();
					message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Shift Definition",frm.getShiftName()));
					
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
			catch (WorkCalendarException e)
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch(Exception e)
			{
				if (BuildConfig.DMODE)
					e.printStackTrace();
				if(e.toString().toLowerCase().indexOf("parent key not found")!=-1)
				{
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.parentKey.notFound"));
					if(!errors.isEmpty())
						saveErrors(request,errors);				
				}
				else
				{
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
					if(!errors.isEmpty())
						saveErrors(request,errors);
				}
				action = "failure";
			}
		}

		if (BuildConfig.DMODE)
			System.out.println("ShiftDefinition Add: "+ action);
		return mapping.findForward(action);
	}
}
/***
$Log: ShiftDefinitionAddAction.java,v $
Revision 1.3  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.2  2004/12/03 11:51:44  sponnusamy
Shift Definition Controllers are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/