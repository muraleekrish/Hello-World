/*
 * Created on Nov 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workcalendar;

import java.util.GregorianCalendar;
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

import com.savantit.prodacs.businessimplementation.workcalendar.AvailabilityDetails;
import com.savantit.prodacs.businessimplementation.workcalendar.BaseCalendarDetails;
import com.savantit.prodacs.businessimplementation.workcalendar.CustomAvbltyDetails;
import com.savantit.prodacs.businessimplementation.workcalendar.CustomNonAvbltyDetails;
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
public class AvailCalendarAddAction extends Action 
{
	public ActionForward execute(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/* creating instance of AvailCalendarAddForm.java */
		if (form instanceof AvailCalendarAddForm)
		{
			AvailCalendarAddForm frm = (AvailCalendarAddForm)form;
			
			/* EJBLocator */
			EJBLocator obj = new EJBLocator();
			ActionErrors errors = new ActionErrors();
			
			BaseCalendarDetails objBaseCalendarDetails = new BaseCalendarDetails();
			Vector vec_SftDetails = new Vector();
			try
			{
				/* 	Setting the JNDI name and Environment 	*/
				obj.setJndiName("SessionWorkCalendarDetailsManagerBean");
			   	obj.setEnvironment();

			   	/* 	Creating the Home and Remote Objects 	*/
				SessionWorkCalendarDetailsManagerHome baseCalHomeObj = (SessionWorkCalendarDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkCalendarDetailsManagerHome.class);
				SessionWorkCalendarDetailsManager baseCalDefObj = (SessionWorkCalendarDetailsManager)PortableRemoteObject.narrow(baseCalHomeObj.create(),SessionWorkCalendarDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("loadMandatory"))
				{
					String sftDetails = "";
					objBaseCalendarDetails = baseCalDefObj.getBaseCalendarDetails(Integer.parseInt(frm.getSelectBaseCalendar().trim()));
					vec_SftDetails = objBaseCalendarDetails.getVec_ShiftDetails();
					frm.setBcStarnEndDay(objBaseCalendarDetails.getBcStartDay()+"-"+objBaseCalendarDetails.getBcEndDay());
					for (int i = 0; i < vec_SftDetails.size(); i++)
					{
						ShiftDetails objShiftDetails = new ShiftDetails();
						objShiftDetails = (ShiftDetails)vec_SftDetails.get(i);
						if (i != 0)
						{
							sftDetails = sftDetails + "^";
						}

						sftDetails = sftDetails + objShiftDetails.getDay() +"-"+
							objShiftDetails.getShiftName() +"-"+
							objShiftDetails.getStartTime() +"-"+
							objShiftDetails.getStartTimeDay() +"-"+
							objShiftDetails.getEndTime() +"-"+
							objShiftDetails.getEndTimeDay() +"-"+
							(objShiftDetails.getPredsrShiftName().trim().equalsIgnoreCase("")?" ":objShiftDetails.getPredsrShiftName()) +"-"+
							objShiftDetails.getShiftId() +"-"+
							objShiftDetails.getPredsrShiftId();
					}
					frm.setShiftDetails(sftDetails);
					if (BuildConfig.DMODE)
						System.out.println("sftDetails: "+ frm.getShiftDetails());
					action = "failure";
				}
				else if (frm.getFormAction().equalsIgnoreCase("add"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Availability Add Starts...");
					AvailabilityDetails objAvailDetails = new AvailabilityDetails();
					
					try
					{
						objAvailDetails.setAvailabilityName(frm.getBaseCalendarName());
						objAvailDetails.setBaseCalId(Integer.parseInt(frm.getSelectBaseCalendar().trim()));
						/* Date conversion for From Date*/
						StringTokenizer stDate = new StringTokenizer(frm.getFromDate(),"-");
	
						int yr = 0;
						int month = 0;
						int day = 0;
						if (stDate.hasMoreTokens())
						{
							yr = Integer.parseInt(stDate.nextToken().trim());
						}
						if (stDate.hasMoreTokens())
						{
							month = Integer.parseInt(stDate.nextToken().trim());
						}
						if (stDate.hasMoreTokens())
						{
							day = Integer.parseInt(stDate.nextToken().trim());
						}
						GregorianCalendar ge = new GregorianCalendar(yr,month-1,day);
						objAvailDetails.setFromDate(ge.getTime());
	
						/* Date conversion for To Date*/
						StringTokenizer st = new StringTokenizer(frm.getToDate(),"-");
	
						int year = 0;
						int mon = 0;
						int date = 0;
						if (st.hasMoreTokens())
						{
							year = Integer.parseInt(st.nextToken().trim());
						}
						if (st.hasMoreTokens())
						{
							mon = Integer.parseInt(st.nextToken().trim());
						}
						if (st.hasMoreTokens())
						{
							date = Integer.parseInt(st.nextToken().trim());
						}
						GregorianCalendar toGe = new GregorianCalendar(year,mon-1,date);
						objAvailDetails.setToDate(toGe.getTime());
						if (BuildConfig.DMODE)
							System.out.println("ShiftDets frm JSP :"+frm.getAvlDetails());
						StringTokenizer stAvlDet = new StringTokenizer(frm.getAvlDetails(), "$");
						String[] arAvlDet = new String[stAvlDet.countTokens()];
						int cnt = 0;
						while (stAvlDet.hasMoreTokens())
						{
							arAvlDet[cnt] = stAvlDet.nextToken();
							if (BuildConfig.DMODE)
								System.out.println(cnt+". "+ arAvlDet[cnt]);
							cnt++;
						}

						Vector vec_NonAvlDets = new Vector();
						Vector vec_CustomAvlDets = new Vector();
						for (int i = 0; i < arAvlDet.length; i++)
						{
							StringTokenizer stPrnlAvlDet = new StringTokenizer(arAvlDet[i],"#");
							String[] arPrnlAvlDet = new String[stPrnlAvlDet.countTokens()];
							int count = 0;
							for (int j = 0; j < arPrnlAvlDet.length; j++)
							{
								while (stPrnlAvlDet.hasMoreTokens())
								{
									arPrnlAvlDet[count] = stPrnlAvlDet.nextToken();
									count++;
								}
							}
							if (arPrnlAvlDet[2].trim().equalsIgnoreCase("Availability"))
							{
								
								/* Object for Custom Availability */
								CustomAvbltyDetails objCustomDetails = new CustomAvbltyDetails();

								StringTokenizer stSftDetails = new StringTokenizer(arPrnlAvlDet[3],"^");
								String[] arSftDetails = new String[stSftDetails.countTokens()];
								int cntr = 0;
								for  (int k = 0; k < arSftDetails.length; k++)
								{
									while (stSftDetails.hasMoreTokens())
									{
										arSftDetails[cntr] = stSftDetails.nextToken();
										cntr++;
									}
								}
								/* Date Conversion for For Date*/
								StringTokenizer stForDate = new StringTokenizer(arPrnlAvlDet[1],"-");
								int y = 0;
								int mnth = 0;
								int d = 0;
								if (stForDate.hasMoreTokens())
								{
									y = Integer.parseInt(stForDate.nextToken().trim());
								}
								if (stForDate.hasMoreTokens())
								{
									mnth = Integer.parseInt(stForDate.nextToken().trim());
								}
								if (stForDate.hasMoreTokens())
								{
									d = Integer.parseInt(stForDate.nextToken().trim());
								}
								ge = new GregorianCalendar(y,mnth-1,d);
								objCustomDetails.setForDate(ge.getTime());

								Vector vec_ShiftDets = new Vector();
								for (int l = 0; l < arSftDetails.length; l++)
								{
									ShiftDetails objShiftDetails = new ShiftDetails();
									
									StringTokenizer stSfts = new StringTokenizer(arSftDetails[l],"-");
									String[] arSfts = new String[stSfts.countTokens()];
									int counter = 0;
									for (int m = 0; m < arSfts.length; m++)
									{
										while (stSfts.hasMoreTokens())
										{
											arSfts[counter] = stSfts.nextToken();
											counter++;
										}
										if (BuildConfig.DMODE)
											System.out.println("arSfts["+m+"] :"+arSfts[m]);
									}
									objShiftDetails.setShiftName(arSfts[1].trim());
									String sftId = (arSfts[7].trim().length() == 0)?(0+""):(arSfts[7].trim());
									objShiftDetails.setShiftId(Integer.parseInt(sftId.trim()));
									objShiftDetails.setStartTime(arSfts[2].trim());
									objShiftDetails.setEndTime(arSfts[4].trim());
									objShiftDetails.setStartTimeDay(arSfts[3].trim());
									objShiftDetails.setEndTimeDay(arSfts[5].trim());
									String preSftId = (arSfts[8].trim().length() == 0)?(0+""):(arSfts[8].trim());
									objShiftDetails.setPredsrShiftId(Integer.parseInt(preSftId.trim()));
									vec_ShiftDets.add(objShiftDetails);	/* Adding the Shift Details to Shift Details Vector */
								}
								objCustomDetails.setVec_ShiftDet(vec_ShiftDets);
								vec_CustomAvlDets.add(objCustomDetails);
							}
							else if (arPrnlAvlDet[2].trim().equalsIgnoreCase("Non-Availability"))
							{
								/* Object for Custom Non-Availability */
								CustomNonAvbltyDetails objCustomNonAvlDetails = new CustomNonAvbltyDetails();

								/* Date Conversion for For Date */
								StringTokenizer stForDate = new StringTokenizer(arPrnlAvlDet[1],"-");
								int non_yr = 0;
								int non_mnth = 0;
								int non_day = 0;
								if (stForDate.hasMoreTokens())
								{
									non_yr = Integer.parseInt(stForDate.nextToken().trim());
								}
								if (stForDate.hasMoreTokens())
								{
									non_mnth = Integer.parseInt(stForDate.nextToken().trim());
								}
								if (stForDate.hasMoreTokens())
								{
									non_day = Integer.parseInt(stForDate.nextToken().trim());
								}
								ge = new GregorianCalendar(non_yr,non_mnth-1,non_day);
								objCustomNonAvlDetails.setForDate(ge.getTime());
								if (BuildConfig.DMODE)
									System.out.println("frm.getAvlDetails():"+frm.getAvlDetails());
								objCustomNonAvlDetails.setNonAvbltyReason(arPrnlAvlDet[3].trim());
								vec_NonAvlDets.add(objCustomNonAvlDetails);
							}
						}
						objAvailDetails.setCustomAvbltyDetails(vec_CustomAvlDets);
						objAvailDetails.setNonAvbltyDetails(vec_NonAvlDets);

						boolean added = baseCalDefObj.addAvailabilityCalendar(objAvailDetails);
						if (added == true)
						{
							ActionMessages message = new ActionMessages();
							message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Availability Calendar",frm.getBaseCalendarName()));
							
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
					catch(Exception e)
					{
						if (BuildConfig.DMODE)
							e.printStackTrace();
					}
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
		else
		{
			action = "failure";
		}
		if (BuildConfig.DMODE)
			System.out.println("AvailCalendar add: "+ action);
		return mapping.findForward(action);
	}
}
/***
$Log: AvailCalendarAddAction.java,v $
Revision 1.9  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.8  2005/05/11 04:25:38  vkrishnamoorthy
Script modified for adding availability details.

Revision 1.7  2005/05/03 07:24:18  vkrishnamoorthy
Modified for StTimeDay and EdTimeDay.

Revision 1.6  2005/04/29 07:12:25  vkrishnamoorthy
StartTimeDay and EndTimeDay added.

Revision 1.5  2005/04/21 07:25:16  vkrishnamoorthy
Availability Add Completed.

Revision 1.4  2005/04/20 10:56:05  sponnusamy
Modified according to availability calendar

Revision 1.3  2005/04/07 04:27:11  sponnusamy
Availcalendar add on progress

Revision 1.2  2005/02/11 11:21:49  sponnusamy
Warnings removed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/