/*
 * Created on May 26, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.employee;

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

import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
import com.savantit.prodacs.businessimplementation.employee.EmployeeTypeDetails;
import com.savantit.prodacs.facade.SessionEmpDetailsManager;
import com.savantit.prodacs.facade.SessionEmpDetailsManagerHome;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmployeeTypeRuleAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		{
			String action = "failure";
			
			if (form instanceof EmployeeTypeRuleForm)
			{
				/* Creating instance to EmployeeTypeAddForm.java */
				EmployeeTypeRuleForm frm = (EmployeeTypeRuleForm) form;
				
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
					if (frm.getFormAction().equalsIgnoreCase("update"))
					{
						Vector vec_result = new Vector();
						EmployeeTypeDetails[] objEmpTypeDetList = null;
						StringTokenizer stEmpDets = new StringTokenizer(frm.getEmpDets(),"^");
						while (stEmpDets.hasMoreTokens())
						{
							EmployeeTypeDetails objEmpTypeDet = new EmployeeTypeDetails();
							String empDetails = stEmpDets.nextToken();
							StringTokenizer empDets = new StringTokenizer(empDetails,"-");
							if (empDets.countTokens() > 0)
							{
								String empTypName = empDets.nextToken();
								String minRqdQty = empDets.nextToken();
								String empTypId = empDets.nextToken();
								
								objEmpTypeDet.setEmp_Typ_Name(empTypName.trim());
								objEmpTypeDet.setMin_Rqd_Qty(Integer.parseInt(minRqdQty.trim()));
								objEmpTypeDet.setEmp_Typ_Id(Integer.parseInt(empTypId.trim()));
							}
							vec_result.add(objEmpTypeDet);
						}
						objEmpTypeDetList = new EmployeeTypeDetails[vec_result.size()];
						vec_result.copyInto(objEmpTypeDetList);

						boolean added = empObj.updateEmployeeTypeDetailsWithTeam(objEmpTypeDetList);

						if (added == true)
						{
							ActionMessages message = new ActionMessages();
							message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.update","Employee Type"));
							
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
				catch (EmployeeException ee)
				{
					ee.printStackTrace();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",ee.getExceptionMessage()));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			return mapping.findForward(action);
		}
}
