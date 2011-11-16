/*
 * Created on Oct 30, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.employee;

import java.util.GregorianCalendar;
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

import com.savantit.prodacs.businessimplementation.employee.EmployeeDetails;
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
public class EmployeeEditAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		if (BuildConfig.DMODE)
			System.out.println("EmployeeEdit :"+action);		
		/* Create instance of EmployeeEditForm.java */
		if (form instanceof EmployeeEditForm)
		{
			EmployeeEditForm frm = (EmployeeEditForm)form;
			
			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			ActionErrors errors = new ActionErrors();		

			try
			{
				/* 	Setting the JNDI name and Environment 	*/
				obj.setJndiName("SessionEmpDetailsManagerBean");
		   		obj.setEnvironment();

				/* 	Creating the Home and Remote Objects 	*/
				SessionEmpDetailsManagerHome empHomeObj = (SessionEmpDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionEmpDetailsManagerHome.class);
				SessionEmpDetailsManager empObj = (SessionEmpDetailsManager)PortableRemoteObject.narrow(empHomeObj.create(),SessionEmpDetailsManager.class);

				if (BuildConfig.DMODE)
					System.out.println("Form Action: "+ frm.getFormAction());
				
				if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
				}else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Modification");
					
					/* object to EmployeeTypeDetails */
					EmployeeDetails objEmployeeDetails = new EmployeeDetails();
					
					objEmployeeDetails = empObj.getEmployeeDetails(frm.getId());
					frm.setEmployeeCode(objEmployeeDetails.getEmp_Code());
					frm.setEmployeeName(objEmployeeDetails.getEmp_Name());
						
					frm.setEmployeeType(objEmployeeDetails.getEmp_Typ_Id()+"");
					frm.setEmployeeStatus(objEmployeeDetails.getEmp_Stat_Id()+"");
					frm.setEmployeeInService((objEmployeeDetails.getEmp_Insrvce() == true)?"on":"off");
					if (objEmployeeDetails.getEmp_Doj() == null)
					{
						frm.setEmployeeDOJ("");
					}
					else
					{
						frm.setEmployeeDOJ(objEmployeeDetails.getEmp_Doj().toString().substring(0,10));
					}
					if (BuildConfig.DMODE)
						System.out.println("Emp DOJ: "+frm.getEmployeeDOJ());
					
					if (objEmployeeDetails.getEmp_Dob() == null)
					{
						frm.setEmployeeDOB("");
					}
					else
					{
						frm.setEmployeeDOB(objEmployeeDetails.getEmp_Dob().toString().substring(0,10));
					}
					if (BuildConfig.DMODE)
						System.out.println("Emp DOB: "+frm.getEmployeeDOB());
					frm.setBloodGroup(objEmployeeDetails.getEmp_BloodGp());
					frm.setAddress1(objEmployeeDetails.getEmp_Cntct_Addr1());
					frm.setAddress2(objEmployeeDetails.getEmp_Cntct_Addr2());
					frm.setCity(objEmployeeDetails.getEmp_Cntct_City());
					frm.setState(objEmployeeDetails.getEmp_Cntct_State());
					frm.setPincode(objEmployeeDetails.getEmp_Cntct_Pcode());
					frm.setPhone1(objEmployeeDetails.getEmp_Cntct_Phone1());
					frm.setPhone2(objEmployeeDetails.getEmp_Cntct_Phone2());
					frm.setContactName(objEmployeeDetails.getEmp_Cntct_Name());
					frm.setAddress3(objEmployeeDetails.getEmp_Permnt_Addr1());
					frm.setAddress4(objEmployeeDetails.getEmp_Permnt_Addr2());
					frm.setCity1(objEmployeeDetails.getEmp_Permnt_City());
					frm.setState1(objEmployeeDetails.getEmp_Permnt_State());
					frm.setPincode1(objEmployeeDetails.getEmp_Permnt_Pcode());
					frm.setPhone3(objEmployeeDetails.getEmp_Permnt_Phone1());
					frm.setPhone4(objEmployeeDetails.getEmp_Permnt_Phone2());
					
					if (BuildConfig.DMODE)
						System.out.println("Modify Process Completed");
				}else if (frm.getFormAction().equalsIgnoreCase("update"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Updation");
					
					/* object to EmployeeTypeDetails */
					EmployeeDetails objEmployeeDetails = new EmployeeDetails();

					objEmployeeDetails.setEmp_Id(frm.getId());
					objEmployeeDetails.setEmp_Name(frm.getEmployeeName().trim());
					objEmployeeDetails.setEmp_Code(frm.getEmployeeCode().trim());
					objEmployeeDetails.setEmp_Typ_Id(Integer.parseInt(frm.getEmployeeType().trim()));
					objEmployeeDetails.setEmp_Stat_Id(Integer.parseInt(frm.getEmployeeStatus().trim()));
					objEmployeeDetails.setEmp_Insrvce(((frm.getEmployeeInService().equals(""))?false:true));
					
					/* Date Conversion for EmployeeDoJ */
					StringTokenizer st = null;
					int yr = 0;
					int month = 0;
					int day = 0;
					GregorianCalendar ge = null;
					
					if (!frm.getEmployeeDOJ().equalsIgnoreCase(""))
					{
						st = new StringTokenizer(frm.getEmployeeDOJ(),"-");
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
						ge = new GregorianCalendar(yr,month-1,day);
						objEmployeeDetails.setEmp_Doj(ge.getTime());
					}

					/* Date Conversion for DOB */
					if (!frm.getEmployeeDOB().equalsIgnoreCase(""))
					{
						st = new StringTokenizer(frm.getEmployeeDOB(),"-");
						yr = 0;
						month = 0;
						day = 0;
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
						ge = new GregorianCalendar(yr,month-1,day);
						objEmployeeDetails.setEmp_Dob(ge.getTime());
						if (BuildConfig.DMODE)
							System.out.println("U DOB: "+ objEmployeeDetails.getEmp_Dob());
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
					
					boolean up = empObj.updateEmployee(objEmployeeDetails);
					
					if (up == true)
					{
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.updated","Employee",frm.getEmployeeName()));
						saveMessages(request,messages);
						action="success";
					}
					else
					{
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.notupdated","Employee",frm.getEmployeeName()));
						saveMessages(request,messages);
						action="success";				  			
					}			  	
					action="success";
				}else if (frm.getFormAction().equalsIgnoreCase("makeInvalid"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Make Invalid");

					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(new Integer(ids[i]));
					}
					HashMap hm = new HashMap();
					try
					{
						hm = empObj.makeEmployeesInValid(v);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
							System.out.println("Problem in MakeInvalid");
					}
					
					int success = 0;
					int notFound = 0;
					int inUse = 0;
					int locked = 0;
					int general = 0;
					for (Iterator it=hm.entrySet().iterator(); it.hasNext(); ) 
					{ 
						Map.Entry entry = (Map.Entry)it.next(); 
						int value = ((Integer)entry.getValue()).intValue();
						if(value == 0)
						{
							success++;
						}
						else if(value == 1)
						{
							notFound++;
						}
						else if(value == 2)
						{
							inUse++;
						}
						else if(value == 3)
						{
							locked++;
						}						
						else if(value == 4)
						{
							general++;
						}												
					}
					ActionMessages messages = new ActionMessages();
					if(success<=1)
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"Employee"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"Employees"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Employee"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Employees"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Employee"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Employees"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Employee"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Employees"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Employee"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Employees"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Employee"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Employees"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
				else if (frm.getFormAction().equalsIgnoreCase("makeValid"))
				{
					if (BuildConfig.DMODE)
						System.out.println("MakeValid");

					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(new Integer(ids[i]));
					}
					HashMap hm = new HashMap();
					try
					{
						hm = empObj.makeEmployeesValid(v);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("Error on Emp Type MakeValid");
							e.printStackTrace();
						}
					}
					int success = 0;
					int notFound = 0;
					int inUse = 0;
					int locked = 0;
					int general = 0;
					for (Iterator it=hm.entrySet().iterator(); it.hasNext(); ) 
					{ 
						Map.Entry entry = (Map.Entry)it.next(); 
						int value = ((Integer)entry.getValue()).intValue();
						if(value == 0)
						{
							success++;
						}
						else if(value == 1)
						{
							notFound++;
						}
						else if(value == 2)
						{
							inUse++;
						}
						else if(value == 3)
						{
							locked++;
						}						
						else if(value == 4)
						{
							general++;
						}												
					}
					ActionMessages messages = new ActionMessages();
					if(success<=1)
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"Employee"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"Employees"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Employee"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Employees"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Employee"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Employees"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Employee"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Employees"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Employee"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Employees"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Employee"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Employees"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
			}catch (Exception e)
			{
				if (BuildConfig.DMODE)
					e.printStackTrace();
			}
		}

		if (BuildConfig.DMODE)
			System.out.println("EmployeeEdit :"+action);
		return mapping.findForward(action);
	}
}
/***
$Log: EmployeeEditAction.java,v $
Revision 1.11  2005/08/17 10:19:24  vkrishnamoorthy
Fields added.

Revision 1.10  2005/08/01 06:26:30  vkrishnamoorthy
Employee code added.

Revision 1.9  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.8  2005/05/27 08:55:24  vkrishnamoorthy
Modified for date validation.

Revision 1.7  2005/05/16 07:42:55  vkrishnamoorthy
Print Statements commented.

Revision 1.6  2005/03/14 09:01:53  sponnusamy
Changes made in Date

Revision 1.5  2005/03/12 09:04:20  sponnusamy
Date Problems are Corrected.

Revision 1.4  2005/01/19 11:25:33  vkrishnamoorthy
Error messages are throwed in Edit page.

Revision 1.3  2004/12/22 08:01:37  sponnusamy
In date field Null values are restricted.

Revision 1.2  2004/11/18 13:59:45  sponnusamy
Employee associated Forms are finished

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/