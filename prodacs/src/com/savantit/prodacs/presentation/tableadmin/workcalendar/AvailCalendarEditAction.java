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
public class AvailCalendarEditAction extends Action
{
	public ActionForward execute(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/* creating instance of AvailCalendarEditForm.java */
		if (form instanceof AvailCalendarEditForm)
		{
			AvailCalendarEditForm frm = (AvailCalendarEditForm)form;
			/* EJBLocator */
			EJBLocator obj = new EJBLocator();
			
			/* Object creation for BaseCalendar, Avblty & Non-Avblty */
			BaseCalendarDetails objBaseCalDet = new BaseCalendarDetails();
			AvailabilityDetails objAvailabilityDetails = new AvailabilityDetails();
			
			CustomNonAvbltyDetails objCustomNonAvbltyDetails = new CustomNonAvbltyDetails();
			
			Vector vec_sftDetails = new Vector();
			Vector vec_customAvlDets = new Vector();
			Vector vec_customNonAvlDets = new Vector();
			ActionErrors errors = new ActionErrors();
			
			try
			{
				/* 	Setting the JNDI name and Environment 	*/
				obj.setJndiName("SessionWorkCalendarDetailsManagerBean");
			   	obj.setEnvironment();

			   	/* 	Creating the Home and Remote Objects 	*/
				SessionWorkCalendarDetailsManagerHome availCalHomeObj = (SessionWorkCalendarDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkCalendarDetailsManagerHome.class);
				SessionWorkCalendarDetailsManager availCalDefObj = (SessionWorkCalendarDetailsManager)PortableRemoteObject.narrow(availCalHomeObj.create(),SessionWorkCalendarDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("frm.getFormAction :"+frm.getFormAction());
						System.out.println("Availability Calendar View Starts....");
					}
					
					/* Loading the Availability Details*/
					if (BuildConfig.DMODE)
						System.out.println("Id :"+frm.getId());
					objAvailabilityDetails = availCalDefObj.getAvailabilityDetails(frm.getId());
					objBaseCalDet = availCalDefObj.getBaseCalendarDetails(objAvailabilityDetails.getBaseCalId());
					vec_sftDetails = objBaseCalDet.getVec_ShiftDetails();
					
					frm.setAvailCalendarName(objAvailabilityDetails.getAvailabilityName());
					frm.setFromDate(objAvailabilityDetails.getFromDate().toString().substring(0,10));
					frm.setToDate(objAvailabilityDetails.getToDate().toString().substring(0,10));
					frm.setBaseCalendarName(Integer.toString(objAvailabilityDetails.getBaseCalId()));
					frm.setBcStarnEndDay(objBaseCalDet.getBcStartDay()+"-"+objBaseCalDet.getBcEndDay());
					String shiftDets = "";
					
					for (int i = 0; i < vec_sftDetails.size(); i++)
					{
						ShiftDetails objShiftDetails = new ShiftDetails();
						objShiftDetails = (ShiftDetails) vec_sftDetails.get(i);
						if (i != 0)
						{
							shiftDets = shiftDets + "^";
						}
						if (i == 0)
						{
							shiftDets = objShiftDetails.getDay()+"";
							shiftDets = shiftDets + "-" + objShiftDetails.getShiftId()+"";
							shiftDets = shiftDets+ "-" + objShiftDetails.getShiftName();
							shiftDets = shiftDets + "-" + objShiftDetails.getStartTime();
							shiftDets = shiftDets + "-" + objShiftDetails.getStartTimeDay();
							shiftDets = shiftDets + "-" + objShiftDetails.getEndTime();
							shiftDets = shiftDets + "-" + objShiftDetails.getEndTimeDay();
							shiftDets = shiftDets + "-" + objShiftDetails.getPredsrShiftId()+"";
							shiftDets = shiftDets + "-" + objShiftDetails.getPredsrShiftName();
						}
						else
						{
							shiftDets = shiftDets + "" + objShiftDetails.getDay();
							shiftDets = shiftDets + "-" + objShiftDetails.getShiftId();
							shiftDets = shiftDets + "-" + objShiftDetails.getShiftName();
							shiftDets = shiftDets + "-" + objShiftDetails.getStartTime();
							shiftDets = shiftDets + "-" + objShiftDetails.getStartTimeDay();
							shiftDets = shiftDets + "-" + objShiftDetails.getEndTime();
							shiftDets = shiftDets + "-" + objShiftDetails.getEndTimeDay();
							shiftDets = shiftDets + "-" + objShiftDetails.getPredsrShiftId();
							shiftDets = shiftDets + "-" + objShiftDetails.getPredsrShiftName();
						}
						frm.setSftDetails(shiftDets);
					}
					
					vec_customAvlDets = objAvailabilityDetails.getCustomAvbltyDetails();
					String custAvlDets = "";
					for (int i = 0; i < vec_customAvlDets.size(); i++)
					{
						if (i != 0)
							custAvlDets = custAvlDets + "~";
						
						CustomAvbltyDetails objCustomAvbltyDet = new CustomAvbltyDetails();
						objCustomAvbltyDet = (CustomAvbltyDetails) vec_customAvlDets.get(i);
						
						Vector vec_CstmAvbltyDets = new Vector();
						vec_CstmAvbltyDets = objCustomAvbltyDet.getVec_ShiftDet();
						if (BuildConfig.DMODE)
							System.out.println("Vec_Size: "+ vec_CstmAvbltyDets.size());
						
						String sftDetails = "";
						for (int j = 0; j < vec_CstmAvbltyDets.size(); j++)
						{
							ShiftDetails objShiftDetails = new ShiftDetails();
							objShiftDetails = (ShiftDetails)vec_CstmAvbltyDets.get(j);
							if (j != 0)
							{
								sftDetails = sftDetails + "^";
							}
							sftDetails = sftDetails + objShiftDetails.getShiftId()+"$"+
											objShiftDetails.getShiftName()+"$"+
											objShiftDetails.getStartTime()+"$"+
											objShiftDetails.getStartTimeDay()+"$"+
											objShiftDetails.getEndTime()+"$"+
											objShiftDetails.getEndTimeDay()+"$"+
											objShiftDetails.getPredsrShiftId();
						}
						custAvlDets = custAvlDets + objCustomAvbltyDet.getForDate().toString().substring(0,10) + "|" + sftDetails;
					}
					frm.setCustomAvlDetails(custAvlDets);
					if (BuildConfig.DMODE)
						System.out.println("custAvlDets :"+custAvlDets);
					
					vec_customNonAvlDets = objAvailabilityDetails.getNonAvbltyDetails();
					String custNonAvlDets = "";
					for (int k = 0; k < vec_customNonAvlDets.size(); k++)
					{
						objCustomNonAvbltyDetails = (CustomNonAvbltyDetails)vec_customNonAvlDets.get(k);
						if (k != 0)
						{
							custNonAvlDets = custNonAvlDets + "^";
						}
						custNonAvlDets = custNonAvlDets + objCustomNonAvbltyDetails.getForDate().toString().substring(0,10)+ "$" +objCustomNonAvbltyDetails.getNonAvbltyReason();
					}
					frm.setCustomNonAvlDetails(custNonAvlDets);
					if (BuildConfig.DMODE)
					{
						System.out.println("custAvlDets :"+custAvlDets);
						System.out.println("custNonAvlDets :"+custNonAvlDets);
						System.out.println("Availability Calendar View Ends...");
					}					
					action = "view";
				}
				else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("frm.getFormAction :"+frm.getFormAction());
						System.out.println("Modification Starts....");
					}
					
					/* Loading the Availability Details*/
					if (BuildConfig.DMODE)
						System.out.println("Id :"+frm.getId());
					objAvailabilityDetails = availCalDefObj.getAvailabilityDetails(frm.getId());
					objBaseCalDet = availCalDefObj.getBaseCalendarDetails(objAvailabilityDetails.getBaseCalId());
					vec_sftDetails = objBaseCalDet.getVec_ShiftDetails();
					
					frm.setAvailCalendarName(objAvailabilityDetails.getAvailabilityName());
					frm.setFromDate(objAvailabilityDetails.getFromDate().toString().substring(0,10));
					frm.setToDate(objAvailabilityDetails.getToDate().toString().substring(0,10));
					frm.setBaseCalendarName(Integer.toString(objAvailabilityDetails.getBaseCalId()));
					frm.setBcStarnEndDay(objBaseCalDet.getBcStartDay()+"-"+objBaseCalDet.getBcEndDay());
					String shiftDets = "";
					
					for (int i = 0; i < vec_sftDetails.size(); i++)
					{
						ShiftDetails objShiftDetails = new ShiftDetails();
						objShiftDetails = (ShiftDetails) vec_sftDetails.get(i);
						if (i != 0)
						{
							shiftDets = shiftDets + "^";
						}
						if (i == 0)
						{
							shiftDets = objShiftDetails.getDay()+"";
							shiftDets = shiftDets + "-" + objShiftDetails.getShiftId()+"";
							shiftDets = shiftDets+ "-" + objShiftDetails.getShiftName();
							shiftDets = shiftDets + "-" + objShiftDetails.getStartTime();
							shiftDets = shiftDets + "-" + objShiftDetails.getStartTimeDay();
							shiftDets = shiftDets + "-" + objShiftDetails.getEndTime();
							shiftDets = shiftDets + "-" + objShiftDetails.getEndTimeDay();
							shiftDets = shiftDets + "-" + objShiftDetails.getPredsrShiftId()+"";
							shiftDets = shiftDets + "-" + objShiftDetails.getPredsrShiftName();
						}
						else
						{
							shiftDets = shiftDets + "" + objShiftDetails.getDay();
							shiftDets = shiftDets + "-" + objShiftDetails.getShiftId();
							shiftDets = shiftDets + "-" + objShiftDetails.getShiftName();
							shiftDets = shiftDets + "-" + objShiftDetails.getStartTime();
							shiftDets = shiftDets + "-" + objShiftDetails.getStartTimeDay();
							shiftDets = shiftDets + "-" + objShiftDetails.getEndTime();
							shiftDets = shiftDets + "-" + objShiftDetails.getEndTimeDay();
							shiftDets = shiftDets + "-" + objShiftDetails.getPredsrShiftId();
							shiftDets = shiftDets + "-" + objShiftDetails.getPredsrShiftName();
						}
						frm.setSftDetails(shiftDets);
					}
					
					vec_customAvlDets = objAvailabilityDetails.getCustomAvbltyDetails();
					String custAvlDets = "";
					for (int i = 0; i < vec_customAvlDets.size(); i++)
					{
						if (i != 0)
							custAvlDets = custAvlDets + "~";
						
						CustomAvbltyDetails objCustomAvbltyDet = new CustomAvbltyDetails();
						objCustomAvbltyDet = (CustomAvbltyDetails) vec_customAvlDets.get(i);
						
						Vector vec_CstmAvbltyDets = new Vector();
						vec_CstmAvbltyDets = objCustomAvbltyDet.getVec_ShiftDet();
						if (BuildConfig.DMODE)
							System.out.println("Vec_Size: "+ vec_CstmAvbltyDets.size());
						
						String sftDetails = "";
						for (int j = 0; j < vec_CstmAvbltyDets.size(); j++)
						{
							ShiftDetails objShiftDetails = new ShiftDetails();
							objShiftDetails = (ShiftDetails)vec_CstmAvbltyDets.get(j);
							if (j != 0)
							{
								sftDetails = sftDetails + "^";
							}
							sftDetails = sftDetails + objShiftDetails.getShiftId()+"$"+
											objShiftDetails.getShiftName()+"$"+
											objShiftDetails.getStartTime()+"$"+
											objShiftDetails.getStartTimeDay()+"$"+
											objShiftDetails.getEndTime()+"$"+
											objShiftDetails.getEndTimeDay()+"$"+
											objShiftDetails.getPredsrShiftId();
						}
						custAvlDets = custAvlDets + objCustomAvbltyDet.getForDate().toString().substring(0,10) + "|" + sftDetails;
					}
					frm.setCustomAvlDetails(custAvlDets);
					if (BuildConfig.DMODE)
						System.out.println("custAvlDets :"+custAvlDets);
					
					vec_customNonAvlDets = objAvailabilityDetails.getNonAvbltyDetails();
					String custNonAvlDets = "";
					for (int k = 0; k < vec_customNonAvlDets.size(); k++)
					{
						objCustomNonAvbltyDetails = (CustomNonAvbltyDetails)vec_customNonAvlDets.get(k);
						if (k != 0)
						{
							custNonAvlDets = custNonAvlDets + "^";
						}
						custNonAvlDets = custNonAvlDets + objCustomNonAvbltyDetails.getForDate().toString().substring(0,10)+ "$" +objCustomNonAvbltyDetails.getNonAvbltyReason();
					}
					frm.setCustomNonAvlDetails(custNonAvlDets);
					if (BuildConfig.DMODE)
					{
						System.out.println("custAvlDets :"+custAvlDets);
						System.out.println("custNonAvlDets :"+custNonAvlDets);
						System.out.println("Modification Ends...");
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("update"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Availability Updation Starts...");
					AvailabilityDetails objAvailDetails = new AvailabilityDetails();
					
						objAvailDetails.setAvailabilityName(frm.getAvailCalendarName());
						objAvailDetails.setBaseCalId(Integer.parseInt(frm.getBaseCalendarName().trim()));
						objAvailDetails.setAvailabilityId(frm.getId());
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
							System.out.println("Shift Details :"+frm.getAvlDetails());
						StringTokenizer stAvlDet = new StringTokenizer(frm.getAvlDetails(), "~");
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
									if (BuildConfig.DMODE)
										System.out.println("arPrnlAvlDet["+count+"]. "+arPrnlAvlDet[count]);
									count++;
								}
							}
							if (arPrnlAvlDet[1].trim().equalsIgnoreCase("Availability"))
							{
								
								/* Object for Custom Availability */
								CustomAvbltyDetails objCustomDetails = new CustomAvbltyDetails();

								StringTokenizer stSftDetails = new StringTokenizer(arPrnlAvlDet[3],"|");
								String[] arSftDetails = new String[stSftDetails.countTokens()];
								int cntr = 0;
								for  (int k = 0; k < arSftDetails.length; k++)
								{
									while (stSftDetails.hasMoreTokens())
									{
										arSftDetails[cntr] = stSftDetails.nextToken();
										if (BuildConfig.DMODE)
											System.out.println("arSftDetails["+cntr+"]. "+arSftDetails[cntr]);
										cntr++;
									}
								}
								StringTokenizer stShiftDets = new StringTokenizer(arSftDetails[1],"^");
								String[] arShiftDets = new String[stShiftDets.countTokens()];
								int c = 0;
								for (int y = 0; y < arShiftDets.length; y++)
								{
									while (stShiftDets.hasMoreTokens())
									{
										arShiftDets[c] = stShiftDets.nextToken();
										c++;
									}
								}
								/* Date Conversion for For Date*/
								StringTokenizer stForDate = new StringTokenizer(arPrnlAvlDet[0],"-");
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
								for (int l = 0; l < arShiftDets.length; l++)
								{
									ShiftDetails objShiftDetails = new ShiftDetails();
									
									StringTokenizer stSfts = new StringTokenizer(arShiftDets[l],"$");
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
									String sftId = (arSfts[0].trim().length() == 0)?(0+""):(arSfts[0].trim());
									objShiftDetails.setShiftId(Integer.parseInt(sftId.trim()));
									objShiftDetails.setStartTime(arSfts[2].trim());
									objShiftDetails.setEndTime(arSfts[4].trim());
									objShiftDetails.setStartTimeDay(arSfts[3].trim());
									objShiftDetails.setEndTimeDay(arSfts[5].trim());
									String preSftId = (arSfts[6].trim().length() == 0)?(0+""):(arSfts[6].trim());
									objShiftDetails.setPredsrShiftId(Integer.parseInt(preSftId.trim()));
									vec_ShiftDets.add(objShiftDetails);	/* Adding the Shift Details to Shift Details Vector */
								}
								objCustomDetails.setVec_ShiftDet(vec_ShiftDets);
								vec_CustomAvlDets.add(objCustomDetails);
							}
							else if (arPrnlAvlDet[1].trim().equalsIgnoreCase("Non-Availability"))
							{
								/* Object for Custom Non-Availability */
								CustomNonAvbltyDetails objCustomNonAvlDetails = new CustomNonAvbltyDetails();

								/* Date Conversion for For Date */
								StringTokenizer stForDate = new StringTokenizer(arPrnlAvlDet[0],"-");
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
								objCustomNonAvlDetails.setNonAvbltyReason(arPrnlAvlDet[2]);
								vec_NonAvlDets.add(objCustomNonAvlDetails);
							}
						}
						objAvailDetails.setCustomAvbltyDetails(vec_CustomAvlDets);
						objAvailDetails.setNonAvbltyDetails(vec_NonAvlDets);
						if (BuildConfig.DMODE)
						{
							System.out.println("Avl Vec Size :"+vec_CustomAvlDets.size());
							System.out.println("Non-Avl Vec Size :"+vec_NonAvlDets.size());
						}
						boolean updated = availCalDefObj.updateAvailabilityCalendar(objAvailDetails);
						if (updated == true)
						{
							ActionMessages message = new ActionMessages();
							message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Availability Calendar",frm.getAvailCalendarName()));
							
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
			catch (Exception e)
			{
				if (BuildConfig.DMODE)
					e.printStackTrace();
			}
		}
		else
		{
			action = "failure";
		}
		if (BuildConfig.DMODE)
			System.out.println("AvailCalendar Edit: "+ action);
		return mapping.findForward(action);
	}
}
/***
$Log: AvailCalendarEditAction.java,v $
Revision 1.5  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.4  2005/05/03 13:50:35  vkrishnamoorthy
Modified as per AvailCalendarView.

Revision 1.3  2005/05/03 07:23:31  vkrishnamoorthy
AvailCalEdit Completed.

Revision 1.2  2005/02/11 11:21:49  sponnusamy
Warnings removed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/