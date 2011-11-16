/*
 * Created on Mar 31, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.securityadministration;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.savantit.prodacs.businessimplementation.securityadmin.SecAdminUserDetails;
import com.savantit.prodacs.businessimplementation.securityadmin.SecurityAdminException;
import com.savantit.prodacs.businessimplementation.securityadmin.SystemInfoDetails;
import com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails;
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
public class LoginAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		ActionErrors errors = new ActionErrors();
		
		if (form instanceof LoginForm)
		{
			LoginForm frm = (LoginForm) form;
			
			EJBLocator obj = new EJBLocator(); /* Instantiating Object for EJBLocator */
			SecAdminUserDetails secAdminUserObj = new SecAdminUserDetails();
			UserAuthDetails userAuthDetailsObj = new UserAuthDetails();
			
			try
			{
				/* Initializing JNDI Name and Environment */
				obj.setJndiName("SessionSecurityAdminManager");
				obj.setEnvironment();
				
				/* Home and Remote Object Instantiation */
				SessionSecurityAdminManagerHome secAdminHomeObj = (SessionSecurityAdminManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionSecurityAdminManagerHome.class);
				SessionSecurityAdminManager secAdminObj = (SessionSecurityAdminManager)PortableRemoteObject.narrow(secAdminHomeObj.create(),SessionSecurityAdminManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("login"))
				{
					SystemInfoDetails objSystemInfoDetails = new SystemInfoDetails();
					if (frm.getCpuId().trim().equalsIgnoreCase("") && (frm.getMachId().trim().equalsIgnoreCase("")) && (frm.getDriveId().trim().equalsIgnoreCase("")))
					{
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.login.error.sysinfo"));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						action = "failure";
					}
					else
					{
						objSystemInfoDetails.setCpuId(frm.getCpuId().trim());
						objSystemInfoDetails.setMacAddr(frm.getMachId().trim());
						objSystemInfoDetails.setDriveSno(frm.getDriveId().trim());
	
						boolean resultMaster = secAdminObj.checkUserAuthentication(frm.getUserId(),frm.getUserPwd(),objSystemInfoDetails);
						if (resultMaster)
						{
							userAuthDetailsObj = secAdminObj.getUserResources(frm.getUserId());
							userAuthDetailsObj.setPassword(frm.getUserPwd());
							request.getSession().setAttribute("secAdminObj",secAdminObj);
							action = "success";
						}
						else
						{
							frm.setFormAction("loginFailedByUserName");
							errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.login.error.invalidUser"));
							if(!errors.isEmpty())
								saveErrors(request,errors);
							action = "failure";
						}
						request.getSession().setAttribute("##userRights##", userAuthDetailsObj);
					}
				}
			}
			catch(SecurityAdminException se)
			{
				if (se.getExceptionCode().equalsIgnoreCase("EC002"))
				{
					frm.setFormAction("loginFailed");
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",se.getExceptionMessage()));
					if(!errors.isEmpty())
						saveErrors(request,errors);
				}
				else
				{
					frm.setFormAction("loginFailedByUserName");
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",se.getExceptionMessage()));
					if(!errors.isEmpty())
						saveErrors(request,errors);
				}
				action = "failure";
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if (BuildConfig.DMODE)
			System.out.println("Login Action :"+action);
		return mapping.findForward(action);
	}
}


/***
$Log: LoginAction.java,v $
Revision 1.8  2005/12/13 11:20:59  vkrishnamoorthy
Error message caught and thrown appropriately for Invalid User Name.

Revision 1.7  2005/12/13 10:36:09  vkrishnamoorthy
Error message caught and thrown appropriately for Invalid User Name.

Revision 1.6  2005/12/08 14:18:58  vkrishnamoorthy
Modified for Master User Authentication.

Revision 1.5  2005/12/08 12:58:53  vkrishnamoorthy
Modified for Master User Authentication.

Revision 1.4  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.3  2005/04/06 12:20:00  vkrishnamoorthy
Modified to throw error messages.

Revision 1.2  2005/04/04 09:21:18  vkrishnamoorthy
Error Messages thrown correctly.

Revision 1.1  2005/03/31 12:18:23  vkrishnamoorthy
Login process completed.

***/