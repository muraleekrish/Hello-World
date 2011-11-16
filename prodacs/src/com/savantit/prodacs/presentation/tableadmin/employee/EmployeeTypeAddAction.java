/*
 * Created on Oct 29, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
import com.savantit.prodacs.businessimplementation.employee.EmployeeTypeDetails;
import com.savantit.prodacs.facade.SessionEmpDetailsManager;
import com.savantit.prodacs.facade.SessionEmpDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class EmployeeTypeAddAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		{
			String action = "failure";
			
			if (form instanceof EmployeeTypeAddForm)
			{
				/* Creating instance to EmployeeTypeAddForm.java */
				EmployeeTypeAddForm frm = (EmployeeTypeAddForm) form;
				
				/* EJBLocator */
				EJBLocator obj = new EJBLocator();
				ActionErrors errors = new ActionErrors();
				try
				{
					/* Setting the JNDI name and Environment */
					obj.setJndiName("SessionEmpDetailsManagerBean");
					obj.setEnvironment();

					/* 	Creating the Home and Remote Objects 	*/
					SessionEmpDetailsManagerHome empHomeObj = (SessionEmpDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionEmpDetailsManagerHome.class);
					SessionEmpDetailsManager empObj = (SessionEmpDetailsManager)PortableRemoteObject.narrow(empHomeObj.create(),SessionEmpDetailsManager.class);
					
					/* Setting the form values to Details Variables */
					EmployeeTypeDetails objEmpTypeDet = new EmployeeTypeDetails();
					
					objEmpTypeDet.setEmp_Typ_Name(frm.getEmployeeType().trim());
					objEmpTypeDet.setEmp_Typ_Desc(frm.getEmployeeDescription().trim());
					objEmpTypeDet.setEmp_Typ_Dt(((frm.getDutyHrs() == null)?false:true));
					objEmpTypeDet.setEmp_Typ_Ot(((frm.getOtHrs() == null)?false:true));
					objEmpTypeDet.setEmp_Typ_Incentive(((frm.getIncentiveHrs() == null)?false:true));
					
					boolean added = empObj.addEmployeeTypeDetails(objEmpTypeDet);
					
					if (added == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Employee Type",frm.getEmployeeType()));
						
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
				catch(EmployeeException e)
				{
					e.printStackTrace();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
					if(!errors.isEmpty())
						saveErrors(request,errors);
					action = "failure";
				}
				catch(Exception e)
				{
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
				System.out.println("Employee Type Add: "+action);
			
			return mapping.findForward(action);
		}
}
/***
$Log: EmployeeTypeAddAction.java,v $
Revision 1.9  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.8  2005/06/17 07:46:13  vkrishnamoorthy
Duty, OT, Incentive Hrs checked for null entries.

Revision 1.7  2005/06/17 06:38:59  vkrishnamoorthy
Duty, OT, Incentive Hrs added.

Revision 1.6  2005/05/26 13:44:12  vkrishnamoorthy
Team field removed.

Revision 1.5  2005/05/17 03:40:31  vkrishnamoorthy
Error message thrown appropriately.

Revision 1.4  2005/05/16 07:42:55  vkrishnamoorthy
Print Statements commented.

Revision 1.3  2004/11/16 15:41:32  sponnusamy
EmployeeType problems are corrected

Revision 1.2  2004/11/10 12:23:42  sponnusamy
EmployeeType  related form and actions

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit old prodacs

***/