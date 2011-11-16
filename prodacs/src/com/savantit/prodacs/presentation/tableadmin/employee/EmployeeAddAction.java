/*
 * Created on Oct 29, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.employee;

import java.util.GregorianCalendar;
import java.util.StringTokenizer;

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

import com.savantit.prodacs.businessimplementation.employee.EmployeeDetails;
import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
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
public class EmployeeAddAction extends Action 
{
	public ActionForward execute(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		int yr = 0;
		int month = 0;
		int day = 0;

		/* creating instance of EmployeeAddForm.java */
		if (form instanceof EmployeeAddForm)
		{
			EmployeeAddForm frm = (EmployeeAddForm)form;
			
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
				EmployeeDetails objEmployeeDetails = new EmployeeDetails();
				
				objEmployeeDetails.setEmp_Code(frm.getEmployeeCode().trim());
				objEmployeeDetails.setEmp_Name(frm.getEmployeeName().trim());
				objEmployeeDetails.setEmp_Typ_Id(Integer.parseInt(frm.getEmployeeType().trim()));
				objEmployeeDetails.setEmp_Stat_Id(Integer.parseInt(frm.getEmployeeStatus().trim()));
				objEmployeeDetails.setEmp_Insrvce(((frm.getEmployeeInService().equals(""))?false:true));

				/* Date Conversion for EmployeeDoJ */
				if (!frm.getEmployeeDOJ().equalsIgnoreCase(""))
				{
					StringTokenizer st = new StringTokenizer(frm.getEmployeeDOJ(),"-");
					if(st.hasMoreTokens())
					{
						yr = Integer.parseInt(st.nextToken().trim());
					}
					if(st.hasMoreTokens())
					{
						month = Integer.parseInt(st.nextToken().trim());
					}
					if(st.hasMoreTokens())
					{		
						day = Integer.parseInt(st.nextToken().trim());
					}
					GregorianCalendar ge = new GregorianCalendar(yr,month-1,day);
	
					objEmployeeDetails.setEmp_Doj(ge.getTime());
					if (BuildConfig.DMODE)
						System.out.println("Emp DOJ: "+ objEmployeeDetails.getEmp_Doj());
				}	
				/* Date Conversion for EmployeeDOB */
				if (!frm.getEmployeeDOB().equalsIgnoreCase(""))
				{
					StringTokenizer st = new StringTokenizer(frm.getEmployeeDOB(),"-");
					if(st.hasMoreTokens())
					{
						yr = Integer.parseInt(st.nextToken().trim());
					}
					if(st.hasMoreTokens())
					{
						month = Integer.parseInt(st.nextToken().trim());
					}
					if(st.hasMoreTokens())
					{		
						day = Integer.parseInt(st.nextToken().trim());
					}
					GregorianCalendar ge = new GregorianCalendar(yr,month-1,day);
					objEmployeeDetails.setEmp_Dob(ge.getTime());
					if (BuildConfig.DMODE)
						System.out.println("Emp DOB: "+ objEmployeeDetails.getEmp_Dob());
				}
				objEmployeeDetails.setEmp_BloodGp(frm.getBloodGroup().trim());
				objEmployeeDetails.setEmp_Cntct_Addr1(frm.getAddress1().trim());
				objEmployeeDetails.setEmp_Cntct_Addr2(frm.getAddress2().trim());
				objEmployeeDetails.setEmp_Cntct_City(frm.getCity().trim());
				objEmployeeDetails.setEmp_Cntct_State(frm.getState().trim());
				objEmployeeDetails.setEmp_Cntct_Pcode(frm.getPincode().trim());
				objEmployeeDetails.setEmp_Cntct_Phone1(frm.getPhone1().trim());
				objEmployeeDetails.setEmp_Cntct_Phone2(frm.getPhone2().trim());
				objEmployeeDetails.setEmp_Cntct_Name(frm.getContactName().trim());
				objEmployeeDetails.setEmp_Permnt_Addr1(frm.getAddress3().trim());
				objEmployeeDetails.setEmp_Permnt_Addr2(frm.getAddress4().trim());
				objEmployeeDetails.setEmp_Permnt_City(frm.getCity1().trim());
				objEmployeeDetails.setEmp_Permnt_State(frm.getState1().trim());
				objEmployeeDetails.setEmp_Permnt_Pcode(frm.getPincode1().trim());
				objEmployeeDetails.setEmp_Permnt_Phone1(frm.getPhone3().trim());
				objEmployeeDetails.setEmp_Permnt_Phone2(frm.getPhone4().trim());
				
				boolean added = empObj.addEmployee(objEmployeeDetails);
				
				if (added == true)
				{
					ActionMessages message = new ActionMessages();
					message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Employee",frm.getEmployeeName()));
					
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
			System.out.println("Employee Add: "+ action);
		return mapping.findForward(action);
	}
 }
/***
$Log: EmployeeAddAction.java,v $
Revision 1.7  2005/08/17 10:19:24  vkrishnamoorthy
Fields added.

Revision 1.6  2005/08/01 06:26:30  vkrishnamoorthy
Employee code added.

Revision 1.5  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.4  2005/05/16 07:42:55  vkrishnamoorthy
Print Statements commented.

Revision 1.3  2005/02/16 10:03:47  vkrishnamoorthy
Modified for date set and get.

Revision 1.2  2004/11/18 13:59:45  sponnusamy
Employee associated Forms are finished

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/