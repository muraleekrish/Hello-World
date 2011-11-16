/*
 * Created on Apr 22, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.payroll;

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

import com.savantit.prodacs.businessimplementation.payroll.PayRollException;
import com.savantit.prodacs.facade.SessionPayrollDetailsManager;
import com.savantit.prodacs.facade.SessionPayrollDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PayrollIdAddAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		if (form instanceof PayrollIdAddForm)
		{
			PayrollIdAddForm frm = (PayrollIdAddForm)form;
			
			ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();

			try
			{
				EJBLocator obj = new EJBLocator();	/* Object ceration for EJBLocator */
				obj.setJndiName("SessionPayrollDetailsManagerBean");
				obj.setEnvironment();
				
				/* Creation of Home and Remote object */
				SessionPayrollDetailsManagerHome objPayroll = (SessionPayrollDetailsManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionPayrollDetailsManagerHome.class); 
				SessionPayrollDetailsManager payrollDetailsObj = (SessionPayrollDetailsManager) PortableRemoteObject.narrow(objPayroll.create(),SessionPayrollDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("addId"))
				{
					boolean added = payrollDetailsObj.createNewPayrollCycleStat();
					if (BuildConfig.DMODE)
						System.out.println("added :"+added);
					if (added == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Payroll Id",""));
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
			catch(PayRollException pe)
			{
				if (BuildConfig.DMODE)
					pe.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",pe.getExceptionMessage()));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action="failure";
			}
			catch (Exception e)
			{
				if (BuildConfig.DMODE)
				{
					System.out.println("Error in PayrollIdAddAction.java");
					e.printStackTrace();
				}
			}
		}
		if (BuildConfig.DMODE)
			System.out.println("PayrollIdAdd Action :"+action);
		return mapping.findForward(action);
	}
}

/***
$Log: PayrollIdAddAction.java,v $
Revision 1.3  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.2  2005/05/03 13:52:34  vkrishnamoorthy
Exception caught in catch block.

Revision 1.1  2005/04/22 09:09:33  vkrishnamoorthy
Initial commit on ProDACS.

***/