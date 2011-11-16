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

import com.savantit.prodacs.businessimplementation.workcalendar.BaseCalendarDetails;
import com.savantit.prodacs.businessimplementation.workcalendar.ShiftDetails;
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
public class BaseCalendarEditAction extends Action 
{
	public ActionForward execute(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/* creating instance of BaseCalendarEditForm.java */
		if (form instanceof BaseCalendarEditForm)
		{
			BaseCalendarEditForm frm = (BaseCalendarEditForm)form;
			
			/* EJBLocator */
			EJBLocator obj = new EJBLocator();
			ActionErrors errors = new ActionErrors();
			
			try
			{
				/* 	Setting the JNDI name and Environment 	*/
				obj.setJndiName("SessionWorkCalendarDetailsManagerBean");
			   	obj.setEnvironment();

			   	/* 	Creating the Home and Remote Objects 	*/
				SessionWorkCalendarDetailsManagerHome baseCalHomeObj = (SessionWorkCalendarDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkCalendarDetailsManagerHome.class);
				SessionWorkCalendarDetailsManager baseCalDefObj = (SessionWorkCalendarDetailsManager)PortableRemoteObject.narrow(baseCalHomeObj.create(),SessionWorkCalendarDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
				}
				else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					try
					{
					/* Object to Details object */
					BaseCalendarDetails objBaseCalDet = new BaseCalendarDetails();
					
					objBaseCalDet = baseCalDefObj.getBaseCalendarDetails(frm.getId());
					
					frm.setBaseCalendarName(objBaseCalDet.getBcName());
					frm.setStartDayofWeek(objBaseCalDet.getBcStartDay()+"");
					frm.setEndDayofWeek(objBaseCalDet.getBcEndDay()+"");
					
					Vector vec_SftDet = objBaseCalDet.getVec_ShiftDetails();
					ShiftDetails objShiftDetails = null;
					
					String shiftDetails = "";
					for (int i = 0; i < vec_SftDet.size(); i++)
					{
						objShiftDetails = (ShiftDetails) vec_SftDet.get(i);
						if (i != 0)
						{
							shiftDetails = shiftDetails + "^";
						}
						if (i == 0)
						{
							shiftDetails = objShiftDetails.getDay()+"";
							shiftDetails = shiftDetails + "-" + objShiftDetails.getShiftId()+"";
							shiftDetails = shiftDetails+ "-" + objShiftDetails.getShiftName();
							shiftDetails = shiftDetails + "-" + objShiftDetails.getStartTime();
							shiftDetails = shiftDetails + "-" + objShiftDetails.getStartTimeDay();
							shiftDetails = shiftDetails + "-" + objShiftDetails.getEndTime();
							shiftDetails = shiftDetails + "-" + objShiftDetails.getEndTimeDay();
							shiftDetails = shiftDetails + "-" + objShiftDetails.getPredsrShiftId()+"";
							shiftDetails = shiftDetails + "-" + objShiftDetails.getPredsrShiftName();
						}
						else
						{
							shiftDetails = shiftDetails + "" + objShiftDetails.getDay();
							shiftDetails = shiftDetails + "-" + objShiftDetails.getShiftId();
							shiftDetails = shiftDetails + "-" + objShiftDetails.getShiftName();
							shiftDetails = shiftDetails + "-" + objShiftDetails.getStartTime();
							shiftDetails = shiftDetails + "-" + objShiftDetails.getStartTimeDay();
							shiftDetails = shiftDetails + "-" + objShiftDetails.getEndTime();
							shiftDetails = shiftDetails + "-" + objShiftDetails.getEndTimeDay();
							shiftDetails = shiftDetails + "-" + objShiftDetails.getPredsrShiftId();
							shiftDetails = shiftDetails + "-" + objShiftDetails.getPredsrShiftName();
						}
						frm.setHidShiftDets(shiftDetails);
					}
					if (BuildConfig.DMODE)
						System.out.println("Modified.");
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
					/* Object to Details object*/ 
					BaseCalendarDetails objBaseCalDet = new BaseCalendarDetails();
					String arShftDetails[];
					String arShftDets[];
					
					Vector vec_SftDet = new Vector();
					
					objBaseCalDet.setBcId(frm.getId());
					objBaseCalDet.setBcName(frm.getBaseCalendarName());
					objBaseCalDet.setBcStartDay(Integer.parseInt(frm.getStartDayofWeek()));
					objBaseCalDet.setBcEndDay(Integer.parseInt(frm.getEndDayofWeek()));
					
					StringTokenizer stShftDets = new StringTokenizer(frm.getHidShiftDetails(),"^");
					int count = 0;
					arShftDetails = new String[stShftDets.countTokens()];
					
					while (stShftDets.hasMoreTokens())
					{
						arShftDetails[count] = stShftDets.nextToken();
						if (BuildConfig.DMODE)
							System.out.println("Shift Details :"+count+". "+arShftDetails[count]);
						count++;
					}
					
					for (int i = 0; i < arShftDetails.length; i++)
					{
						StringTokenizer stShftDetails = new StringTokenizer(arShftDetails[i],"#");
						count = 0;
						arShftDets = new String[stShftDetails.countTokens()];
						while (stShftDetails.hasMoreTokens())
						{
							arShftDets[count] = stShftDetails.nextToken();
							if (BuildConfig.DMODE)
								System.out.println(count+". "+arShftDets[count]);
							count++;
						}
						
						ShiftDetails objShiftDetails = new ShiftDetails();
						
						objShiftDetails.setDay(Integer.parseInt(arShftDets[5].trim()));
						objShiftDetails.setShiftId(Integer.parseInt(arShftDets[6].trim()));
						String preSftId = (arShftDets[7].trim().length() == 0)?(0+""):(arShftDets[7].trim());
						if (BuildConfig.DMODE)
							System.out.println("preSftId: "+preSftId);
						objShiftDetails.setPredsrShiftId(Integer.parseInt(preSftId));
						
						String[] arStTime = arShftDets[2].split("-");
						if (BuildConfig.DMODE)
							System.out.println("arStTime :"+arStTime[0]);
						objShiftDetails.setStartTime(arStTime[0].trim());
						if (BuildConfig.DMODE)
							System.out.println("arStTime[1] :"+arStTime[1]);
						objShiftDetails.setStartTimeDay(arStTime[1].trim());
						
						String[] arEdTime = arShftDets[3].split("-");
						if (BuildConfig.DMODE)
							System.out.println("arEdTime :"+arEdTime[0]);
						objShiftDetails.setEndTime(arEdTime[0].trim());
						if (BuildConfig.DMODE)
							System.out.println("arEdTime[1] :"+arEdTime[1]);
						objShiftDetails.setEndTimeDay(arEdTime[1].trim());
						
						vec_SftDet.add(objShiftDetails);
					}
					objBaseCalDet.setVec_ShiftDetails(vec_SftDet);						

					boolean update = baseCalDefObj.updateBaseCalendar(objBaseCalDet);
					
					if (update == true)
					{
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.updated","Base Calendar ",frm.getBaseCalendarName()));
						saveMessages(request,messages);
						action="success";
					}
					else
					{
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.notupdated","Base Calendar ",frm.getBaseCalendarName()));
						saveMessages(request,messages);
						action="success";				  			
					}			  	
				}
				else if (frm.getFormAction().equalsIgnoreCase("delete"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Delete");
					
					String ids[] = frm.getIds();
					Vector delList = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						delList.add(new Integer(ids[i]));
						if (BuildConfig.DMODE)
							System.out.println("Del List: "+ ids[i]);
					}
					HashMap hm = new HashMap();
					boolean flag = false;
					try
					{
						hm = baseCalDefObj.deleteBaseCalendar(delList);
						if (BuildConfig.DMODE)
							System.out.println("Delete(HM): "+hm);
					}
					catch (WorkCalendarException we)
					{
						flag = true;
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.deleted", Integer.toString(success),"Base Calendar"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.deleted", Integer.toString(success),"Base Calendars"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Base Calendar"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Base Calendars"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Base Calendar"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Base Calendars"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Base Calendar"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Base Calendars"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Base Calendar"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Base Calendars"));
					}
					if (flag == true)
					{
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.common.error.deletion","Base Calendar"));
					}
					else
					{
						if(hm.size() < delList.size())
						{
							int diff = delList.size()-hm.size();
							if(diff==1)
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.delete.general", Integer.toString(diff),"Base Calendar"));
							else
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.delete.general", Integer.toString(diff),"Base Calendars"));
						}									
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
					Vector tot = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						tot.add(new Integer(ids[i]));
					}
					HashMap hm = new HashMap();
					try
					{
						hm = baseCalDefObj.makeBaseCalendarValid(tot);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
							System.out.println("Problem while MakeValid in BaseCalendarEdit Action");
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"Base Calendar"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"Base Calendars"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Base Calendar"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Base Calendars"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Base Calendar"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Base Calendars"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Base Calendar"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Base Calendars"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Base Calendar"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Base Calendars"));
					}
					if(hm.size() < tot.size())
					{
						int diff = tot.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Base Calendar"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Base Calendars"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
				else if (frm.getFormAction().equalsIgnoreCase("makeInvalid"))
				{
					if (BuildConfig.DMODE)
						System.out.println("MakeInValid");
					
					String ids[] = frm.getIds();
					Vector tot = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						tot.add(new Integer(ids[i]));
					}
					HashMap hm = new HashMap();
					try
					{
						hm = baseCalDefObj.makeBaseCalendarInValid(tot);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
							System.out.println("Problem while MakeInValid in BaseCalendarEdit Action");
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"Base Calendar"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"Base Calendars"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Base Calendar"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Base Calendars"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Base Calendar"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Base Calendars"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Base Calendar"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Base Calendars"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Base Calendar"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Base Calendars"));
					}
					if(hm.size() < tot.size())
					{
						int diff = tot.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Base Calendar"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Base Calendars"));
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
			System.out.println("BaseCalendar Edit: "+ action);
		return mapping.findForward(action);
	}
}
/***
$Log: BaseCalendarEditAction.java,v $
Revision 1.7  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.6  2005/06/16 12:29:00  vkrishnamoorthy
Exception caught for multiple instances BaseCalendar deletion.

Revision 1.5  2005/05/11 06:33:15  vkrishnamoorthy
Modified for displaying appropriate messages.

Revision 1.4  2005/03/19 14:37:39  vkrishnamoorthy
BaseCalendar Model renewed.

Revision 1.3  2005/02/11 11:21:49  sponnusamy
Warnings removed

Revision 1.2  2004/12/07 07:46:59  sponnusamy
BaseCalendar Models are Completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/