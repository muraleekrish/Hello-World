/*
 * Created on Nov 1, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.machine;

import java.util.Date;
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

import com.savantit.prodacs.businessimplementation.machine.MachineDetails;
import com.savantit.prodacs.facade.SessionMachineDetailsManager;
import com.savantit.prodacs.facade.SessionMachineDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MachineEditAction extends Action 
{
	public ActionForward execute(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/* creating instance of MachineEditForm.java */
		if (form instanceof MachineEditForm)
		{
			MachineEditForm frm = (MachineEditForm)form;

			/* EJBLocator */
			EJBLocator obj = new EJBLocator();
			ActionErrors errors = new ActionErrors();
			try
			{
			/* 	Setting the JNDI name and Environment 	*/
 			    obj.setJndiName("SessionMachineDetailsManagerBean");
			   	obj.setEnvironment();

			/* 	Creating the Home and Remote Objects 	*/
				SessionMachineDetailsManagerHome machineHomeObj = (SessionMachineDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionMachineDetailsManagerHome.class);
				SessionMachineDetailsManager machineObj = (SessionMachineDetailsManager)PortableRemoteObject.narrow(machineHomeObj.create(),SessionMachineDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
				}
				else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Modification Started.");
					/* Object to MachineDetails */
					MachineDetails objMachineDetails = new MachineDetails();

					objMachineDetails = machineObj.getMachineDetails(frm.getId());
					
					frm.setMachineCode(objMachineDetails.getMach_Cde());
					frm.setMachineName(objMachineDetails.getMach_Name());
					frm.setMachineType(Integer.toString(objMachineDetails.getMach_Typ_Id()));
					if (objMachineDetails.getMach_Install_Date() == null)
					{
						frm.setInstallationDate("");
					}
					else
					{
						frm.setInstallationDate(objMachineDetails.getMach_Install_Date().toString().substring(0,10));
					}
					if (BuildConfig.DMODE)
						System.out.println("objMachineDetails.getMach_InUse(): "+objMachineDetails.getMach_InUse());
					
					frm.setInUse((objMachineDetails.getMach_InUse().equals("1"))?"1":"0");
					if (BuildConfig.DMODE)
						System.out.println("frm.getInUse(): "+ frm.getInUse());
						
					/* Supplier Details */
					frm.setSupplier(objMachineDetails.getMach_SP_Name());
					frm.setSupplierContactPerson(objMachineDetails.getMach_SP_Cntct_Person());
					frm.setSupplierAddress(objMachineDetails.getMach_SP_Addr());
					frm.setSupplierPhoneNumber(objMachineDetails.getMach_SP_Phone());
					
					/* Service Provider Details */
					frm.setServiceProvider(objMachineDetails.getMach_SPLR_Name());
					frm.setServiceContactPerson(objMachineDetails.getMach_SPLR_Cntct_Person());
					frm.setServiceAddress(objMachineDetails.getMach_SPLR_Addr());
					frm.setServicePhoneNumber(objMachineDetails.getMach_SPLR_Phone());
					
					if (BuildConfig.DMODE)
						System.out.println("Machine Details Modification Loaded.");					
				}
				else if (frm.getFormAction().equalsIgnoreCase("update"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Updation");
					
					/* Object to MachineDetails */
					MachineDetails objMachineDetails = new MachineDetails();

					objMachineDetails.setMach_Cde(frm.getMachineCode());
					objMachineDetails.setMach_Name(frm.getMachineName());
					objMachineDetails.setMach_Typ_Id(Integer.parseInt(frm.getMachineType().trim()));
					
					/* Date Conversion for InstallationDate */
					if (!frm.getInstallationDate().equalsIgnoreCase(""))
					{
					StringTokenizer st = new StringTokenizer(frm.getInstallationDate(),"-");
					int yr = 0;
					int month = 0;
					int day = 0;
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
					objMachineDetails.setMach_Install_Date(ge.getTime());
					}
					else
					{
						Date temp = new Date();
						objMachineDetails.setMach_Install_Date(temp);
					}
					
					objMachineDetails.setMach_InUse(((frm.getInUse().equals(""))?"0":"1"));
					
						/* Supplier Details */
					objMachineDetails.setMach_SP_Name(frm.getSupplier());
					objMachineDetails.setMach_SP_Cntct_Person(frm.getSupplierContactPerson());
					objMachineDetails.setMach_SP_Addr(frm.getSupplierAddress());
					objMachineDetails.setMach_SP_Phone(frm.getSupplierPhoneNumber());
					
						/* Service Provider Details */
					objMachineDetails.setMach_SPLR_Name(frm.getServiceProvider());
					objMachineDetails.setMach_SPLR_Cntct_Person(frm.getServiceContactPerson());
					objMachineDetails.setMach_SPLR_Addr(frm.getServiceAddress());
					objMachineDetails.setMach_SPLR_Phone(frm.getServicePhoneNumber());
						
					boolean update = machineObj.updateMachineDetails(objMachineDetails);
					
					if (update == true)
					{
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.updated","Machine",frm.getMachineName()));
						saveMessages(request,messages);
						action="success";
					}
					else
					{
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.notupdated","Machine",frm.getMachineName()));
						saveMessages(request,messages);
						action="success";				  			
					}			  	
					action="success";
				}
				else if (frm.getFormAction().equalsIgnoreCase("makeValid"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Make Valid");
					
					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(ids[i]);
					}
					HashMap hm = new HashMap();
					try
					{
						hm = machineObj.makeMachinesValid(v);
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"Machine"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"Machines"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Machine"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Machines"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Machine"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Machines"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Machine"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Machines"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Machine"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Machines"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Machine"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Machines"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";

				}else if (frm.getFormAction().equalsIgnoreCase("makeInvalid"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Make InValid");
					
					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(ids[i]);
					}
					HashMap hm = new HashMap();
					try
					{
						hm = machineObj.makeMachinesInValid(v);
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"Machine"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"Machines"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Machine"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Machines"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Machine"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Machines"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Machine"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Machines"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Machine"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Machines"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Machine"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Machines"));
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
			System.out.println("MachineEdit: "+ action);
		return mapping.findForward(action);
	}

}
/***
$Log: MachineEditAction.java,v $
Revision 1.8  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.7  2005/03/14 09:01:53  sponnusamy
Changes made in Date

Revision 1.6  2005/03/12 09:04:20  sponnusamy
Date Problems are Corrected.

Revision 1.5  2005/02/01 04:58:18  vkrishnamoorthy
MessageIds changed appropriately for makeValid and makeInvalid.

Revision 1.4  2005/01/25 07:29:43  sponnusamy
Installation Date Empty values adjusted

Revision 1.3  2004/12/22 08:02:04  sponnusamy
In date field Null values are restricted.

Revision 1.2  2004/11/19 13:26:42  sponnusamy
Machine Forms are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/