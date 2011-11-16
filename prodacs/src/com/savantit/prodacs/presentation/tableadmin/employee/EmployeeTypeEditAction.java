/*
 * Created on Oct 29, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.employee;

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
public class EmployeeTypeEditAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		{
			String action = "failure";
			
			/* Creating handle to EmployeeTypeEditForm.java */
			if (form instanceof EmployeeTypeEditForm)
			{
				EmployeeTypeEditForm frm = (EmployeeTypeEditForm) form;
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

					if (frm.getFormAction().equalsIgnoreCase("view"))
					{
						action = "view";
					}else if (frm.getFormAction().equalsIgnoreCase("modify"))
					{
						if (BuildConfig.DMODE)
							System.out.println("Modification");
						
						/* object to EmployeeTypeDetails */
						EmployeeTypeDetails objEmpTypDet = new EmployeeTypeDetails();
						
						objEmpTypDet = empObj.getEmployeeTypeDetails(frm.getId());
						
						frm.setEmployeeType(objEmpTypDet.getEmp_Typ_Name());
						frm.setEmployeeDescription(objEmpTypDet.getEmp_Typ_Desc());
						frm.setDutyHrs((objEmpTypDet.isEmp_Typ_Dt() == true)?"on":"off");
						frm.setOtHrs((objEmpTypDet.isEmp_Typ_Ot() == true)?"on":"off");
						frm.setIncentiveHrs((objEmpTypDet.isEmp_Typ_Incentive() == true)?"on":"off");
						
					}else if (frm.getFormAction().equalsIgnoreCase("update"))
					{
						if (BuildConfig.DMODE)
							System.out.println("Updation");
						
						/* object to EmployeeTypeDetails */
						EmployeeTypeDetails objEmpTypDet = new EmployeeTypeDetails();
						
						objEmpTypDet.setEmp_Typ_Id(frm.getId());
						objEmpTypDet.setEmp_Typ_Name(frm.getEmployeeType());
						objEmpTypDet.setEmp_Typ_Desc(frm.getEmployeeDescription());
						objEmpTypDet.setEmp_Typ_Dt(((frm.getDutyHrs().equals(""))?false:true));
						objEmpTypDet.setEmp_Typ_Ot(((frm.getOtHrs().equals(""))?false:true));
						objEmpTypDet.setEmp_Typ_Incentive(((frm.getIncentiveHrs().equals(""))?false:true));

						boolean up = empObj.updateEmployeeTypeDetails(objEmpTypDet);
						
						if (up == true)
						{
							ActionMessages messages = new ActionMessages();
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.updated","Employee Type",frm.getEmployeeType()));
							saveMessages(request,messages);
							action="success";
						}
						else
						{
							ActionMessages messages = new ActionMessages();
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.notupdated","Employee Type",frm.getEmployeeType()));
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
							hm = empObj.makeEmployeeTypesInValid(v);
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
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"Employee Type"));
						else if(success>1)					
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"Employee Types"));					
						if(notFound == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Employee Type"));						
						}
						else if(notFound > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Employee Types"));
						}
						
						if(inUse == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Employee Type"));						
						}
						else if(inUse > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Employee Types"));
						}
						if(locked == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Employee Type"));						
						}
						else if(locked > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Employee Types"));
						}
						if(general == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Employee Type"));						
						}
						else if(general > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Employee Types"));
						}
						if(hm.size()<v.size())
						{
							int diff = v.size()-hm.size();
							if(diff==1)
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Employee Type"));
							else
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Employee Types"));
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
							hm = empObj.makeEmployeeTypesValid(v);
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
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"Employee Type"));
						else if(success>1)					
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"Employee Types"));					
						if(notFound == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Employee Type"));						
						}
						else if(notFound > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Employee Types"));
						}
						
						if(inUse == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Employee Type"));						
						}
						else if(inUse > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Employee Types"));
						}
						if(locked == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Employee Type"));						
						}
						else if(locked > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Employee Types"));
						}
						if(general == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Employee Type"));						
						}
						else if(general > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Employee Types"));
						}
						if(hm.size()<v.size())
						{
							int diff = v.size()-hm.size();
							if(diff==1)
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Employee Type"));
							else
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Employee Types"));
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
				System.out.println("Emp Edit Page Action: "+ action);
			return mapping.findForward(action);
		}
}	
/***
$Log: EmployeeTypeEditAction.java,v $
Revision 1.9  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.8  2005/06/28 07:13:20  vkrishnamoorthy
Duty, OT, Incentive Hrs Checking modified.

Revision 1.7  2005/06/17 09:16:11  vkrishnamoorthy
Duty, OT, Incentive Hrs added.

Revision 1.6  2005/05/28 05:21:01  vkrishnamoorthy
Minimum Required Qty removed from display.

Revision 1.5  2005/05/26 13:44:12  vkrishnamoorthy
Team field removed.

Revision 1.4  2005/05/16 07:42:55  vkrishnamoorthy
Print Statements commented.

Revision 1.3  2004/11/16 15:41:32  sponnusamy
EmployeeType problems are corrected

Revision 1.2  2004/11/10 12:23:42  sponnusamy
EmployeeType  related form and actions

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/