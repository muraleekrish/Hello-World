/*
 * Created on Feb 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.jobmaster;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.savantit.prodacs.businessimplementation.job.JobException;
import com.savantit.prodacs.facade.SessionJobDetailsManager;
import com.savantit.prodacs.facade.SessionJobDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NewGeneralNameAddAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		if (form instanceof NewGeneralNameAddForm)
		{
			NewGeneralNameAddForm frm = (NewGeneralNameAddForm) form;
			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			ActionErrors errors = new ActionErrors();
			
			try
			{
				/* 	Setting the JNDI name and Environment 	*/
				obj.setJndiName("SessionJobDetailsManagerBean");
		   		obj.setEnvironment();
	
				/* 	Creating the Home and Remote Objects 	*/
				SessionJobDetailsManagerHome jobHomeObj = (SessionJobDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionJobDetailsManagerHome.class);
				SessionJobDetailsManager jobObj = (SessionJobDetailsManager)PortableRemoteObject.narrow(jobHomeObj.create(),SessionJobDetailsManager.class);
				
				if (BuildConfig.DMODE)
					System.out.println("General Name: "+ frm.getGeneralName());
				frm.setId(jobObj.addJobGeneralName(frm.getGeneralName()));
			}
			catch(JobException e)
			{
				if (BuildConfig.DMODE)
					e.printStackTrace();
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
			System.out.println("General Name: "+ action);
		return mapping.findForward(action);
	}
}
