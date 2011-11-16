/*
 * Created on Feb 14, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.employee;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
import com.savantit.prodacs.businessimplementation.employee.EmployeeStatusDetails;
import com.savantit.prodacs.facade.SessionEmpDetailsManager;
import com.savantit.prodacs.facade.SessionEmpDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NewStatusNameAction extends Action
{
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		String action = "failure";
		if (form instanceof NewStatusNameForm)
		{
			NewStatusNameForm frm = (NewStatusNameForm) form;
			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			ActionErrors errors = new ActionErrors();
			
			try
			{
				/* 	Setting the JNDI name and Environment 	*/
				obj.setJndiName("SessionEmpDetailsManagerBean");
				obj.setEnvironment();
				
				/* 	Creating the Home and Remote Objects 	*/
				SessionEmpDetailsManagerHome homeObj = (SessionEmpDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionEmpDetailsManagerHome.class);
				SessionEmpDetailsManager empObj = (SessionEmpDetailsManager)PortableRemoteObject.narrow(homeObj.create(),SessionEmpDetailsManager.class);
				
				if (BuildConfig.DMODE)
					System.out.println("General Name: "+ frm.getEmployeeStatus());
				EmployeeStatusDetails objEmployeeStatusDetails = new EmployeeStatusDetails();
				
				objEmployeeStatusDetails.setEmpStatName(frm.getEmployeeStatus());
				frm.setId(empObj.addNewEmployeeStatus(objEmployeeStatusDetails));
			}
			catch(EmployeeException e)
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
			System.out.println("New Status Name Action :"+action);
		return mapping.findForward(action);
	}
}


/***
$Log: NewStatusNameAction.java,v $
Revision 1.5  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.4  2005/05/16 07:42:55  vkrishnamoorthy
Print Statements commented.

Revision 1.3  2005/02/16 11:49:01  vkrishnamoorthy
Modified as per NewStatusName adding.

Revision 1.2  2005/02/15 04:10:57  vkrishnamoorthy
Unwanted imports removed.

Revision 1.1  2005/02/14 11:54:38  vkrishnamoorthy
Initial commit on NewStatusNameAction.

***/