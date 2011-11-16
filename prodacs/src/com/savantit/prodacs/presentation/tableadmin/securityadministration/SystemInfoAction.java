/*
 * Created on Dec 7, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.securityadministration;

import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.ejb.CreateException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.savantit.prodacs.businessimplementation.securityadmin.SecurityAdminException;
import com.savantit.prodacs.businessimplementation.securityadmin.SystemInfoDetails;
import com.savantit.prodacs.facade.SessionSecurityAdminManager;
import com.savantit.prodacs.facade.SessionSecurityAdminManagerHome;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SystemInfoAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		EJBLocator obj = new EJBLocator();
		ActionErrors errors = new ActionErrors();
		if (form instanceof SystemInfoForm)
		{
			SystemInfoForm frm = (SystemInfoForm) form;
			
			try
			{
				/* Initializing JNDI Name and Environment */
				obj.setJndiName("SessionSecurityAdminManager");
				obj.setEnvironment();
				
				/* Home and Remote Object Instantiation */
				SessionSecurityAdminManagerHome secAdminHomeObj = (SessionSecurityAdminManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionSecurityAdminManagerHome.class);
				SessionSecurityAdminManager secAdminObj = (SessionSecurityAdminManager)PortableRemoteObject.narrow(secAdminHomeObj.create(),SessionSecurityAdminManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("sendSysInfo"))
				{
					SystemInfoDetails objSystemInfoDetails = new SystemInfoDetails();
					objSystemInfoDetails.setCpuId(frm.getCpuId().trim());
					objSystemInfoDetails.setMacAddr(frm.getMachId().trim());
					objSystemInfoDetails.setDriveSno(frm.getDriveId().trim());
					objSystemInfoDetails.setUserName(frm.getUserName().trim());
					objSystemInfoDetails.setSystDetails(frm.getDescription().trim());
					
					boolean result = secAdminObj.addSystemInfo(objSystemInfoDetails);
					if (result)
					{
						action = "success";
					}
				}
			}
			catch(SecurityAdminException se)
			{
				se.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.common.error.name",se.getExceptionMessage()));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch (RemoteException e)
			{
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.common.error.name",e.getMessage()));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.common.error.name",e.getMessage()));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch (ClassCastException e) 
			{
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.common.error.name",e.getMessage()));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch (CreateException e) 
			{
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.common.error.name",e.getMessage()));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
		}
		return mapping.findForward(action);
	}
}



/***
$Log: SystemInfoAction.java,v $
Revision 1.2  2005/12/15 07:30:21  vkrishnamoorthy
User information added in System Information screen.

Revision 1.1  2005/12/08 13:02:01  vkrishnamoorthy
Initial commit on ProDACS.

***/