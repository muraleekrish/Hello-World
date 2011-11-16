/*
 * Created on Nov 1, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workcalendar;

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

import com.savantit.prodacs.businessimplementation.workcalendar.ShiftDefnDetails;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
import com.savantit.prodacs.facade.SessionWorkCalendarDetailsManager;
import com.savantit.prodacs.facade.SessionWorkCalendarDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ShiftDefinitionEditAction extends Action 
{
	public ActionForward execute(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/* creating instance of ShiftDefinitionEditForm.java */
		if (form instanceof ShiftDefinitionEditForm)
		{
			ShiftDefinitionEditForm frm = (ShiftDefinitionEditForm)form;
			
			/* EJBLocator */
			EJBLocator obj = new EJBLocator();
			ActionErrors errors = new ActionErrors();
			try
			{
			/* 	Setting the JNDI name and Environment 	*/
			    obj.setJndiName("SessionWorkCalendarDetailsManagerBean");
			    obj.setEnvironment();

			/* 	Creating the Home and Remote Objects 	*/
				SessionWorkCalendarDetailsManagerHome shiftDefHomeObj = (SessionWorkCalendarDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkCalendarDetailsManagerHome.class);
				SessionWorkCalendarDetailsManager shiftDefObj = (SessionWorkCalendarDetailsManager)PortableRemoteObject.narrow(shiftDefHomeObj.create(),SessionWorkCalendarDetailsManager.class);

				if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
				}
				else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					try
					{
						/* Setting the form values to Details Variables */
						ShiftDefnDetails objShiftDefnDetails = new ShiftDefnDetails();
						
						objShiftDefnDetails = shiftDefObj.getShiftDefinition(frm.getId());
						
						frm.setShiftName(objShiftDefnDetails.getShiftName());
						frm.setShiftDescription(objShiftDefnDetails.getShiftDesc());
						
						if (BuildConfig.DMODE)
							System.out.println("Modification Completed.");
					}
					catch(WorkCalendarException we)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",we.getExceptionMessage()));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						action = "failure";

					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("update"))
				{
					/* Setting the form values to Details Variables */
					ShiftDefnDetails objShiftDefnDetails = new ShiftDefnDetails();
					
					objShiftDefnDetails.setShiftId(frm.getId());
					objShiftDefnDetails.setShiftDesc(frm.getShiftDescription());
					
					boolean up = shiftDefObj.updateShiftDefinition(objShiftDefnDetails);
					
					if (up == true)
					{
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.updated","Shift Definition",frm.getShiftName()));
						saveMessages(request,messages);
						action="success";
					}
					else
					{
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.notupdated","Shift Definition",frm.getShiftName()));
						saveMessages(request,messages);
						action="success";				  			
					}			  	
				}
				else if (frm.getFormAction().equalsIgnoreCase("makeValid"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Make Valid");
					
					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(new Integer(ids[i]));
					}
					HashMap hm = new HashMap();
					try
					{
						hm = shiftDefObj.makeShiftDefinitionValid(v);
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"Shift Definition"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"Shift Definitions"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Shift Definition"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Shift Definitions"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Shift Definition"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Shift Definitions"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Shift Definition"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Shift Definitions"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Shift Definition"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Shift Definitions"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Shift Definition"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Shift Definitions"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";

				}
				else if (frm.getFormAction().equalsIgnoreCase("makeInvalid"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Make InValid");
					
					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(new Integer(ids[i]));
					}
					HashMap hm = new HashMap();
					try
					{
						hm = shiftDefObj.makeShiftDefinitionInValid(v);
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"Shift Definition"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"Shift Definitions"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Shift Definition"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Shift Definitions"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Shift Definition"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Shift Definitions"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Shift Definition"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Shift Definitions"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Shift Definition"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Shift Definitions"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Shift Definition"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Shift Definitions"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
				else if (frm.getFormAction().equalsIgnoreCase("delete"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Deletion");
					
					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						if (BuildConfig.DMODE)
							System.out.println("Del: "+ ids[i]);
						v.add(new Integer(ids[i]));
					}
					HashMap hm = new HashMap();
					boolean flag = false;
					try
					{
						hm = shiftDefObj.deleteShiftDefinition(v);
						if (BuildConfig.DMODE)
							System.out.println("Delete(HM): "+hm);
					}catch (WorkCalendarException e)
					{
						flag = true;
						if (BuildConfig.DMODE)
							System.out.println("flag :"+flag);
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.deleted", Integer.toString(success),"Shift Definition"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.deleted", Integer.toString(success),"Shift Definitions"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Shift Definition"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Shift Definitions"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Shift Definition"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Shift Definitions"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Shift Definition"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Shift Definitions"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Shift Definition"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Shift Definitions"));
					}
					if (flag == true)
					{
						if (BuildConfig.DMODE)
							System.out.println("flag :"+flag);
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.common.error.deletion","Shift Definition"));
					}
					else
					{
						if(hm.size()<v.size())
						{
							int diff = v.size()-hm.size();
							if(diff==1)
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.delete.general", Integer.toString(diff),"Shift Definition"));
							else
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.delete.general", Integer.toString(diff),"Shift Definitions"));
						}
					}
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
			}
			catch(Exception e)
			{
				if (BuildConfig.DMODE)
					e.printStackTrace();
			}
		}

		if (BuildConfig.DMODE)
			System.out.println("ShiftDefinition Edit: "+ action);
		return mapping.findForward(action);
	}
}
/***
$Log: ShiftDefinitionEditAction.java,v $
Revision 1.6  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.5  2005/06/16 12:21:43  vkrishnamoorthy
Exception caught for multiple instances Shift deletion.

Revision 1.4  2005/05/16 12:38:13  vkrishnamoorthy
Modified for delete.

Revision 1.3  2005/02/01 05:03:19  vkrishnamoorthy
MessageIds changed appropriately for makeValid and makeInvalid.

Revision 1.2  2004/12/03 11:51:44  sponnusamy
Shift Definition Controllers are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/