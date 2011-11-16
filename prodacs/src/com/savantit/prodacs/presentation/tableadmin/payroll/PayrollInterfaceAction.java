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

import com.savantit.prodacs.businessimplementation.payroll.PayRollException;
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
public class PayrollInterfaceAction extends Action
{
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		String action="failure";
		if (form instanceof PayrollInterfaceForm)
		{
			PayrollInterfaceForm frm = (PayrollInterfaceForm) form;
			
			ActionErrors errors = new ActionErrors();
			ActionMessages message = new ActionMessages();
			if (BuildConfig.DMODE)
				System.out.println("Payroll Interface Form");
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
					String userName = frm.getHidUserId();
					boolean payroll = payrollDetailsObj.createPyrlInterface(Integer.parseInt(frm.getCmbPayrollCycle().trim()),userName);
					
					if (payroll == true)
					{
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Payroll Interface",""));
						
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
			catch (PayRollException pe)
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",pe.getExceptionMessage()));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action="failure";				  			
			}
			catch (Exception e)
			{
				if (BuildConfig.DMODE)
				{
					System.out.println("Problem in Interface action");
					e.printStackTrace();
				}
			}
		}
		if (BuildConfig.DMODE)
		{
			System.out.println("Payroll Interface Action :"+action);
		}
		return mapping.findForward(action);
	}
}

/***
$Log: PayrollInterfaceAction.java,v $
Revision 1.8  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.7  2005/06/11 07:01:06  vkrishnamoorthy
UserName added as parameter while passing.

Revision 1.6  2005/05/27 06:27:13  vkrishnamoorthy
Error message captured and thrown appropriately.

Revision 1.5  2005/01/25 05:19:22  sponnusamy
Payroll Administration connected with EJB methods.

Revision 1.4  2005/01/22 10:25:10  sponnusamy
Payroll interface completed

Revision 1.3  2005/01/21 14:58:40  sponnusamy
Log added.

***/
