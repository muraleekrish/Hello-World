/*
 * Created on Dec 7, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.securityadministration;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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

import com.savantit.prodacs.businessimplementation.securityadmin.SecAdminUserDetails;
import com.savantit.prodacs.businessimplementation.securityadmin.SecurityAdminException;
import com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails;
import com.savantit.prodacs.facade.SessionSecurityAdminManager;
import com.savantit.prodacs.facade.SessionSecurityAdminManagerHome;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MasterLoginAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		ActionErrors errors = new ActionErrors();
		
		if (form instanceof MasterLoginForm)
		{
			MasterLoginForm frm = (MasterLoginForm) form;
			
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
				SessionSecurityAdminManager secAdminObj;
				secAdminObj = (SessionSecurityAdminManager)PortableRemoteObject.narrow(secAdminHomeObj.create(),SessionSecurityAdminManager.class);

				if (frm.getFormAction().equalsIgnoreCase("login"))
				{
					boolean result = secAdminObj.checkMasterUserAuthentication(frm.getMasterUserId(),frm.getMasterUserPwd());
					if (result)
					{
						action = "success";
					}
					else
					{
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.login.error.invalidUser"));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						action = "failure";
					}
				}
				else
				{
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.login.error.invalidUser"));
					if(!errors.isEmpty())
						saveErrors(request,errors);
					action = "failure";
				}
				request.getSession().setAttribute("##userRights##", userAuthDetailsObj);
			}
			catch(SecurityAdminException se)
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.login.error.invalidUser"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch (ClassCastException e)
			{
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.login.error.invalidUser"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			} 
			catch (RemoteException e) 
			{
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.login.error.invalidUser"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			} 
			catch (CreateException e) 
			{
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.login.error.invalidUser"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			} 
			catch (InvalidKeyException e) 
			{
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.login.error.invalidUser"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch (NoSuchAlgorithmException e) 
			{
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.login.error.invalidUser"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch (NoSuchPaddingException e) 
			{
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.login.error.invalidUser"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch (IllegalStateException e) 
			{
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.login.error.invalidUser"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch (IllegalBlockSizeException e) 
			{
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.login.error.invalidUser"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch (BadPaddingException e) 
			{
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.login.error.invalidUser"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.login.error.invalidUser"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch (IOException e) 
			{
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.login.error.invalidUser"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
		}
		return mapping.findForward(action);
	}
}


/***
$Log: MasterLoginAction.java,v $
Revision 1.2  2005/12/14 05:44:14  vkrishnamoorthy
Error message caught and thrown appropriately for Invalid Master User Name.

Revision 1.1  2005/12/08 13:02:01  vkrishnamoorthy
Initial commit on ProDACS.

***/