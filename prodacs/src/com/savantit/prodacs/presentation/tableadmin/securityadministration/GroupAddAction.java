/*
 * Created on Mar 22, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.securityadministration;

import java.util.Vector;

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

import com.savantit.prodacs.businessimplementation.securityadmin.SecAdminGroupDetails;
import com.savantit.prodacs.businessimplementation.securityadmin.SecurityAdminException;
import com.savantit.prodacs.facade.SessionSecurityAdminManager;
import com.savantit.prodacs.facade.SessionSecurityAdminManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GroupAddAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		if (form instanceof GroupAddForm)
		{
			GroupAddForm frm = (GroupAddForm) form;
			EJBLocator obj = new EJBLocator();
			ActionErrors errors = new ActionErrors();
			SecAdminGroupDetails secAdminDetails = new SecAdminGroupDetails();
			try
			{
				/* JNDI Name and Environment Setting*/
				obj.setJndiName("SessionSecurityAdminManager");
				obj.setEnvironment();
				
				/* Creation of Home and Remote Objects */
				SessionSecurityAdminManagerHome secAdminHomeObj = (SessionSecurityAdminManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionSecurityAdminManagerHome.class);
				SessionSecurityAdminManager secAdminObj = (SessionSecurityAdminManager)PortableRemoteObject.narrow(secAdminHomeObj.create(),SessionSecurityAdminManager.class);
				
				secAdminDetails.setGroup_Name(frm.getGroupName());
				secAdminDetails.setGroup_Desc(frm.getGroupDesc());
				
				Vector vecSelectedResources = new Vector();
				if (frm.getSelectedResource() != null)
				{
					String[] selectedResources = frm.getSelectedResource();
					for(int i = 0; i < selectedResources.length; i++)
					{
						if (BuildConfig.DMODE)
							System.out.println("RESOURCE ID :"+selectedResources[i]);
						vecSelectedResources.add(selectedResources[i]);
					}
				}
				secAdminDetails.setVResources(vecSelectedResources);
				try
				{
					boolean added = secAdminObj.addNewGroup(secAdminDetails);
					
					if (added == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Group Name",frm.getGroupName()));
						saveMessages(request,message);
						action = "success";						
					}
					else
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						action="failure";				  			
					}
				}
				catch(SecurityAdminException e)
				{
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
					if(!errors.isEmpty())
						saveErrors(request,errors);
					action = "failure";
				}
			}
			catch(Exception e)
			{
				if (BuildConfig.DMODE)
					e.printStackTrace();
			}
		}
		if (BuildConfig.DMODE)
			System.out.println("Group Add Action :"+action);
		return mapping.findForward(action);
	}
}

/***
$Log: GroupAddAction.java,v $
Revision 1.3  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.2  2005/04/05 12:25:45  vkrishnamoorthy
Unwanted Println's removed.

Revision 1.1  2005/03/26 10:09:52  vkrishnamoorthy
Initial commit on Prodacs.

***/
