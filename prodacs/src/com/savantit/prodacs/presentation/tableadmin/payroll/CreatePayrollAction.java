/*
 * Created on Jan 4, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.payroll;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
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

import com.savantit.prodacs.facade.SessionPayrollDetailsManager;
import com.savantit.prodacs.facade.SessionPayrollDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CreatePayrollAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action="failure";
		if (BuildConfig.DMODE)
			System.out.println("Create payroll Action Starts.");
		
		if (form instanceof CreatePayrollForm)
		{
			/* Instance of Create Payroll Form */
			CreatePayrollForm frm = (CreatePayrollForm) form;
			
			ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();

			try
			{
				EJBLocator obj = new EJBLocator();
				obj.setJndiName("SessionPayrollDetailsManagerBean");
				obj.setEnvironment();
				
				/* Creation of Home and Remote object*/
				SessionPayrollDetailsManagerHome objPayroll = (SessionPayrollDetailsManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionPayrollDetailsManagerHome.class); 
				SessionPayrollDetailsManager payrollDetailsObj = (SessionPayrollDetailsManager) PortableRemoteObject.narrow(objPayroll.create(),SessionPayrollDetailsManager.class);

				if (frm.getFormAction().equalsIgnoreCase("create"))
				{
					StringTokenizer stIds = new StringTokenizer(frm.getIds(),"-");
					Vector vecIds = new Vector();
					HashMap payroll = new HashMap();
					while(stIds.hasMoreTokens())
					{
						vecIds.add(new Integer(stIds.nextToken().trim()));
					}
					try
					{
						String userName = frm.getHidUserId();
						payroll = payrollDetailsObj.createPayRoll(vecIds,userName);
					}
					catch (Exception ex)
					{
						if (BuildConfig.DMODE)
							System.out.println("Error in Create Payroll Action: "+ex.toString());
					}
					int success = 0;
					int failed = 0;
					for (Iterator it=payroll.entrySet().iterator(); it.hasNext(); ) 
					{ 
						Map.Entry entry = (Map.Entry)it.next(); 
						int value = ((Integer)entry.getValue()).intValue();
						if(value == 1)
						{
							success++;
						}
						else if(value == 2)
						{
							failed++;
						}
					}
					if(success<=1)
					{
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.created", Integer.toString(success),"Payroll"));
						saveMessages(request,messages);
					}
					else if(success>1)
					{
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.created", Integer.toString(success),"Payroll's"));
						
						saveMessages(request,messages);
					}
					if(failed == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notCreated", Integer.toString(failed),"Payroll"));						
					}
					else if(failed > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notCreated", Integer.toString(failed),"Payroll's"));
					}
					if(payroll.size()<vecIds.size())
					{
						int diff = vecIds.size()-payroll.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Payroll"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Payroll's"));
					}									
					if(!errors.isEmpty())
					{
						saveErrors(request,errors);
					}
					action = "success";
				}
			}
			catch (Exception e)
			{
				if (BuildConfig.DMODE)
				{
					System.out.println("Problem in PayrollCycleAction");
					e.printStackTrace();
				}
			}
		}
		if (BuildConfig.DMODE)
			System.out.println("Create Payroll Action: "+ action);
		return mapping.findForward(action);
	}
}

/***
$Log: CreatePayrollAction.java,v $
Revision 1.4  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.3  2005/06/11 07:01:06  vkrishnamoorthy
UserName added as parameter while passing.

Revision 1.2  2005/01/22 10:25:30  sponnusamy
Payroll interface completed

***/
