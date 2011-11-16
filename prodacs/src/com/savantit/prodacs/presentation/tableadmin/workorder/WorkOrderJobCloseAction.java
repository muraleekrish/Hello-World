/*
 * Created on Nov 4, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workorder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

import com.savantit.prodacs.facade.SessionWorkOrderDetailsManager;
import com.savantit.prodacs.facade.SessionWorkOrderDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class WorkOrderJobCloseAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/* Create an instanceof WorkOrderJobCloseForm.java*/
		if (form instanceof WorkOrderJobCloseForm)
		{ 
			WorkOrderJobCloseForm frm = (WorkOrderJobCloseForm)form;
			if (BuildConfig.DMODE)
            {
				System.out.println("WorkOrder/Job Closing");				
            }
			ActionErrors errors = new ActionErrors();
			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			
			try
			{
			   /* 	Setting the JNDI name and Environment 	*/
			   obj.setJndiName("SessionWorkOrderDetailsManagerBean");
			   obj.setEnvironment();

			   /* 	Creating the Home and Remote Objects 	*/
			   SessionWorkOrderDetailsManagerHome woHomeObj = (SessionWorkOrderDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkOrderDetailsManagerHome.class);
			   SessionWorkOrderDetailsManager workOrderObj = (SessionWorkOrderDetailsManager)PortableRemoteObject.narrow(woHomeObj.create(),SessionWorkOrderDetailsManager.class);
			   
			   if (frm.getFormAction().equalsIgnoreCase("post"))
			   {
				String ids[] = frm.getIds();
				if (BuildConfig.DMODE)
	            {
					System.out.println("ids: "+ ids.length);
	            }
			   	Vector vec_CloseList = new Vector();
				for (int i = 0; i < ids.length-1; i++)
				{
					vec_CloseList.add(new Integer(ids[i]));
					if (BuildConfig.DMODE)
		            {
						System.out.println("Id's: "+ids[i]);
		            }
				}

			   	HashMap closeWrkOrd = new HashMap();
				try
				{
					closeWrkOrd = workOrderObj.closeWorkOrder(vec_CloseList);
				}catch (Exception e)
				{
					if (BuildConfig.DMODE)
		            {
						System.out.println("Problem in WorkOrder Closing.");
		            }
				}
				
				int posted = 0;
				int notPosted = 0;
				int problm = 0;
				for (Iterator it=closeWrkOrd.entrySet().iterator(); it.hasNext(); ) 
				{ 
					Map.Entry entry = (Map.Entry)it.next(); 
					int value = ((Integer)entry.getValue()).intValue();
					if(value == 0)
					{
						posted++;
					}
					else if(value == 1)
					{
						notPosted++;
					}
					else if(value == 2)
					{
						problm++;
					}
				}
				ActionMessages messages = new ActionMessages();
				if(posted >= 1)
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.closed", Integer.toString(posted),"WorkOrder"));
				if(notPosted == 1)
				{
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notPosted),"WorkOrder"));						
				}
				else if(notPosted > 1)
				{
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notPosted),"WorkOrders"));
				}
				
				if(problm == 1)
				{
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(problm),"WorkOrder"));						
				}
				else if(problm > 1)
				{
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(problm),"WorkOrders"));
				}
				if(closeWrkOrd.size() < vec_CloseList.size())
				{
					int diff = vec_CloseList.size() - closeWrkOrd.size();
					if(diff==1)
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"WorkOrder"));
					else
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"WorkOrders"));
				}									
				if(!errors.isEmpty())
					saveErrors(request,errors);					
				saveMessages(request,messages);		
				action = "success";
				if (BuildConfig.DMODE)
	            {
					System.out.println("WorkOrder Jobs are Closed Properly.");
	            }
			   }
			}catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if (BuildConfig.DMODE)
        {
			System.out.println("WorkOrderJobClose : "+action);
        }
		return mapping.findForward(action);
	}
}
/***
$Log: WorkOrderJobCloseAction.java,v $
Revision 1.4  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.3  2005/02/02 12:04:08  sponnusamy
Different Exception are catched
while closing the WorkOrder or JbClosing

Revision 1.2  2004/12/17 07:55:22  sponnusamy
WorkOrderJob Closing controller are completed.

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/