/*
 * Created on Nov 1, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workcalendar;

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
public class BaseCalendarAddAction extends Action 
{
	public ActionForward execute(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/* creating instance of BaseCalendarAddForm.java */
		if (form instanceof BaseCalendarAddForm)
		{
			BaseCalendarAddForm frm = (BaseCalendarAddForm)form;
			
			if (frm.getFormAction().equalsIgnoreCase("add"))
			{
				if (BuildConfig.DMODE)
					System.out.println("Add case");

				/* EJBLocator */
				EJBLocator obj = new EJBLocator();
				ActionErrors errors = new ActionErrors();
				
				String arCalDet[];
				String arPrnlDet[];

				Vector vec_SftDet = new Vector();
				try
				{
					/* 	Setting the JNDI name and Environment 	*/
					obj.setJndiName("SessionWorkCalendarDetailsManagerBean");
				   	obj.setEnvironment();

				   	/* 	Creating the Home and Remote Objects 	*/
					SessionWorkCalendarDetailsManagerHome baseCalHomeObj = (SessionWorkCalendarDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkCalendarDetailsManagerHome.class);
					SessionWorkCalendarDetailsManager baseCalDefObj = (SessionWorkCalendarDetailsManager)PortableRemoteObject.narrow(baseCalHomeObj.create(),SessionWorkCalendarDetailsManager.class);

					/* Object to Details object */
					BaseCalendarDetails objBaseCalDet = new BaseCalendarDetails();
					
					objBaseCalDet.setBcName(frm.getBaseCalendarName());
					objBaseCalDet.setBcStartDay(Integer.parseInt(frm.getStartDayofWeek()));
					objBaseCalDet.setBcEndDay(Integer.parseInt(frm.getEndDayofWeek()));
					
					StringTokenizer stCalDets = new StringTokenizer(frm.getHidShiftDets(),"^");
					int count = 0;
					arCalDet = new String[stCalDets.countTokens()];
					
					while (stCalDets.hasMoreTokens())
					{
						arCalDet[count] = stCalDets.nextToken();
						if (BuildConfig.DMODE)
							System.out.println(count+". "+ arCalDet[count]);
						count++;
					}
					
					for (int i = 0; i < arCalDet.length; i++)
					{
						StringTokenizer stPrnlDet = new StringTokenizer(arCalDet[i],"#");
						count = 0;
						arPrnlDet = new String[stPrnlDet.countTokens()];
						while (stPrnlDet.hasMoreTokens())
						{
							arPrnlDet[count] = stPrnlDet.nextToken();
							if (BuildConfig.DMODE)
								System.out.println(count+". "+ arPrnlDet[count]);
							count++;
						}

						ShiftDetails objShiftDetails = new ShiftDetails();
						
						objShiftDetails.setDay(Integer.parseInt(arPrnlDet[5].trim()));
						objShiftDetails.setShiftId(Integer.parseInt(arPrnlDet[6].trim()));
						String preSftId = (arPrnlDet[7].trim().length() == 0)?(0+""):(arPrnlDet[7].trim());
						if (BuildConfig.DMODE)
							System.out.println("preSftId: "+preSftId);
						objShiftDetails.setPredsrShiftId(Integer.parseInt(preSftId));
						
						String[] arStTime = arPrnlDet[2].split("-");
						if (BuildConfig.DMODE)
							System.out.println("arStTime :"+arStTime[0]);
						objShiftDetails.setStartTime(arStTime[0].trim());
						if (BuildConfig.DMODE)
							System.out.println("arStTime[1] :"+arStTime[1]);
						objShiftDetails.setStartTimeDay(arStTime[1].trim());
						
						String[] arEdTime = arPrnlDet[3].split("-");
						if (BuildConfig.DMODE)
							System.out.println("arStTime :"+arEdTime[0]);
						objShiftDetails.setEndTime(arEdTime[0].trim());
						if (BuildConfig.DMODE)
							System.out.println("arEdTime[1] :"+arEdTime[1]);
						objShiftDetails.setEndTimeDay(arEdTime[1].trim());
						
						vec_SftDet.add(objShiftDetails);
					}
					objBaseCalDet.setVec_ShiftDetails(vec_SftDet);
					
					boolean added = baseCalDefObj.addBaseCalendar(objBaseCalDet);
					
					if (added == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Base Calendar",frm.getBaseCalendarName()));
						
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
				catch(WorkCalendarException e)
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
		}

		if (BuildConfig.DMODE)
			System.out.println("BaseCalendar Add: "+ action);
		return mapping.findForward(action);
	}
}
/***
$Log: BaseCalendarAddAction.java,v $
Revision 1.7  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.6  2005/03/19 14:36:45  vkrishnamoorthy
Modified as per StartTimeDay and EndTimeDay.

Revision 1.5  2005/03/19 04:22:51  vkrishnamoorthy
Unused fields removed.

Revision 1.4  2005/03/19 04:09:13  vkrishnamoorthy
Modified for sending StartTime and EndTime.

Revision 1.3  2005/03/18 12:11:03  vkrishnamoorthy
Modified as per time and day rules.

Revision 1.2  2004/12/07 07:46:59  sponnusamy
BaseCalendar Models are Completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/