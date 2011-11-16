/*
 * Created on Jan 4, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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

import com.savantit.prodacs.facade.SessionPayrollDetailsManager;
import com.savantit.prodacs.facade.SessionPayrollDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;


/**
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PayrollCycleAction extends Action 
{
	public ActionForward execute (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		String action="failure";
		if (form instanceof PayrollCycleForm)
		{
			/* Creating a instance to PayRoll Cycle Form */
			PayrollCycleForm frm = (PayrollCycleForm) form;
			
			ActionErrors errors = new ActionErrors();
			ActionMessages message = new ActionMessages();

			try
			{
				EJBLocator obj = new EJBLocator();
				obj.setJndiName("SessionPayrollDetailsManagerBean");
				obj.setEnvironment();
				
				/* Creation of Home and Remote object*/
				SessionPayrollDetailsManagerHome objPayroll = (SessionPayrollDetailsManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionPayrollDetailsManagerHome.class); 
				SessionPayrollDetailsManager payrollDetailsObj = (SessionPayrollDetailsManager) PortableRemoteObject.narrow(objPayroll.create(),SessionPayrollDetailsManager.class);
				
				
				boolean payroll = payrollDetailsObj.createNewPayrollCycle(frm.getHidCycleType(),frm.getHidCycle());
				
				if (payroll == true)
				{
					message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Payroll Cycle",""));
					
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
			catch (Exception e)
			{
				if (BuildConfig.DMODE)
				{
					System.out.println("Problem in PayrollCycleAction");
					e.printStackTrace();
				}
			}
		}
		return mapping.findForward(action);
	}
}

/***
$Log: PayrollCycleAction.java,v $
Revision 1.4  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.3  2005/01/21 14:59:07  sponnusamy
Messages throwed.

Revision 1.2  2005/01/21 09:18:02  sponnusamy
In filter woId and machine id are modified.

***/
