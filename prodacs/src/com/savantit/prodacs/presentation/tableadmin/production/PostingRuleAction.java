/*
 * Created on May 12, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.production;

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

import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.facade.SessionProductionDetailsManager;
import com.savantit.prodacs.facade.SessionProductionDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PostingRuleAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		if (form instanceof PostingRuleForm)
		{
			PostingRuleForm frm = (PostingRuleForm)form;

			/* EJBLocator */
			EJBLocator obj = new EJBLocator();
			ActionErrors errors = new ActionErrors();

			try
			{
				/* 	Setting the JNDI name and Environment 	*/
				obj.setJndiName("SessionProductionDetailsManagerBean");
			   	obj.setEnvironment();

			   	/* 	Creating the Home and Remote Objects 	*/
			   	SessionProductionDetailsManagerHome postRuleHomeObj = (SessionProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionProductionDetailsManagerHome.class);
			   	SessionProductionDetailsManager postingRuleObj = (SessionProductionDetailsManager)PortableRemoteObject.narrow(postRuleHomeObj.create(),SessionProductionDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("apply"))
				{
					boolean applied = postingRuleObj.changePostingRule(Integer.parseInt(frm.getAppliedRule()));
					
					if (applied == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.applied","Posting Rule"));
						
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
			catch (ProductionException pe)
			{
				if (BuildConfig.DMODE)
	            {
					System.out.println("Error: "+ pe.getExceptionMessage());
	            }
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.production.error",pe.getExceptionMessage()));
				if (!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return mapping.findForward(action);
	}
}


/***
$Log: PostingRuleAction.java,v $
Revision 1.2  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.1  2005/05/12 14:12:39  vkrishnamoorthy
Initial commit on ProDACS.

***/