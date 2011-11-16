/*
 * Created on Nov 4, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workorder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PreCloseLogEditAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/* Create an instanceof PreCloseLogEditForm.java*/
		if (form instanceof PreCloseLogEditForm)
		{ 
			PreCloseLogEditForm frm = (PreCloseLogEditForm)form;

			//ActionErrors errors = new ActionErrors();
			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			try 
			{
				/* 	Setting the JNDI name and Environment 	*/
				obj.setJndiName("SessionWorkOrderDetailsManagerBean");
				obj.setEnvironment();

				/* 	Creating the Home and Remote Objects 	
				SessionWorkOrderDetailsManagerHome woHomeObj = (SessionWorkOrderDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkOrderDetailsManagerHome.class);
				SessionWorkOrderDetailsManager workOrderObj = (SessionWorkOrderDetailsManager)PortableRemoteObject.narrow(woHomeObj.create(),SessionWorkOrderDetailsManager.class);
				*/
				
				if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
				}
			}catch (Exception e)
			{
				if (BuildConfig.DMODE)
	            {
					System.out.println("Problem in PreCloseEdit Action");
	            }
			}
		}
		if (BuildConfig.DMODE)
        {
			System.out.println("PreCloseLog Edit: "+action);
        }
		return mapping.findForward(action);
	}
}
/***
$Log: PreCloseLogEditAction.java,v $
Revision 1.4  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.3  2005/02/11 11:21:49  sponnusamy
Warnings removed

Revision 1.2  2004/12/21 11:23:08  sponnusamy
PreCloseLog Controller is fully completed.

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/