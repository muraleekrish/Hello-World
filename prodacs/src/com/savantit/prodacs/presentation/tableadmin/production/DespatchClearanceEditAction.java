/*
 * Created on Jul 22, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.production;

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

import com.savantit.prodacs.businessimplementation.production.DespatchClearanceDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.facade.SessionDespatchClearanceDetailsManager;
import com.savantit.prodacs.facade.SessionDespatchClearanceDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DespatchClearanceEditAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		if (form instanceof DespatchClearanceEditForm)
		{
			DespatchClearanceEditForm frm = (DespatchClearanceEditForm) form;
			if (BuildConfig.DMODE)
			{
				System.out.println("Despatch Clearance Edit Action Starts...");
			}
			EJBLocator obj = new EJBLocator();
			ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();
			try
			{
				/* 	Setting the JNDI name and Environment 	*/
			   	obj.setJndiName("SessionDespatchClearanceDetailsManager");
		   		obj.setEnvironment();
	
				/* 	Creating the Home and Remote Objects 	*/
				SessionDespatchClearanceDetailsManagerHome objDespatchClearance = (SessionDespatchClearanceDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionDespatchClearanceDetailsManagerHome.class);
				SessionDespatchClearanceDetailsManager objDesClear = (SessionDespatchClearanceDetailsManager)PortableRemoteObject.narrow(objDespatchClearance.create(),SessionDespatchClearanceDetailsManager.class);
				
				/* View */
				if (frm.getFormAction().equalsIgnoreCase("despatchView"))
				{
					action = "despatchView";
					if (BuildConfig.DMODE)
					{
						System.out.println("-->"+action);
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("searchDespatch"))
				{
					action = "searchDespatch";
					if (BuildConfig.DMODE)
					{
						System.out.println("-->"+action);
					}
				}
				else if (frm.getFormAction().trim().equalsIgnoreCase("view"))
				{
					action = "view";
				}
				else if (frm.getFormAction().trim().equalsIgnoreCase("modify")) // Modify Operation
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Modification Starts & Id: "+ frm.getId());
					}
					DespatchClearanceDetails objDespatchClearanceDetails = new DespatchClearanceDetails();
					
					objDespatchClearanceDetails = objDesClear.getDespatchClearanceDetails(frm.getId());
					frm.setProDate(objDespatchClearanceDetails.getDespatchCrntDate().toString().substring(0,10));
					frm.setProShift(objDespatchClearanceDetails.getShiftId()+"");
					frm.setProWorkOrderHash(objDespatchClearanceDetails.getWoId()+"");
					frm.setHidWrkOrdId(objDespatchClearanceDetails.getWoId()+"");
					frm.setHidWrkOrdJobId(objDespatchClearanceDetails.getWoJbId()+"");
					frm.setShowCount("5");
					String modDetails = "";
					modDetails = "1#"+objDespatchClearanceDetails.getDespatchCrntDate().toString().substring(0,10)
								+"#"+objDespatchClearanceDetails.getShiftName()
								+"#"+objDespatchClearanceDetails.getWoNo()
								+"#"+objDespatchClearanceDetails.getObjProductionJobDetails().getJobName()
								+"#"+objDespatchClearanceDetails.getDespatchQtySnos()
								+"#"+objDespatchClearanceDetails.getShiftId()
								+"#"+objDespatchClearanceDetails.getWoId()
								+"#"+objDespatchClearanceDetails.getWoJbId()
								+"#"+objDespatchClearanceDetails.getDespatchStartOpn();
					request.setAttribute("hidModDet",modDetails);
				}
				else if (frm.getFormAction().equalsIgnoreCase("update"))// For Update
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Despatch Clearance Update Starts...");
					}
					DespatchClearanceDetails objDespatchClearanceDetails = new DespatchClearanceDetails();

					StringTokenizer stRowDetails = new StringTokenizer(frm.getHidProdList(),"$");
					while (stRowDetails.hasMoreTokens())
					{
						StringTokenizer stColDetails = new StringTokenizer(stRowDetails.nextToken(),"#");
						String[] arColDetails = new String[stColDetails.countTokens()];
						int count = 0;
						while (stColDetails.hasMoreTokens())
						{
							arColDetails[count] = stColDetails.nextToken();
							if (BuildConfig.DMODE)
							{
								System.out.println((count)+". "+arColDetails[count]);
							}
							count++;
						}
						
						StringTokenizer stDate = new StringTokenizer(arColDetails[1].trim(),"-");
						int year = 0;
						int mon = 0;
						int date = 0;
						if(stDate.hasMoreTokens())
							year = Integer.parseInt(stDate.nextToken().trim());
						if(stDate.hasMoreTokens())
							mon = Integer.parseInt(stDate.nextToken().trim());
						if(stDate.hasMoreTokens())
							date = Integer.parseInt(stDate.nextToken().trim());
						GregorianCalendar gc = new GregorianCalendar(year,mon-1,date);

						objDespatchClearanceDetails.setDespatchId(frm.getId());
						objDespatchClearanceDetails.setDespatchCrntDate(gc.getTime());
						objDespatchClearanceDetails.setShiftId(Integer.parseInt(arColDetails[6]));
						objDespatchClearanceDetails.setWoJbId(Integer.parseInt(arColDetails[8]));
						objDespatchClearanceDetails.setDespatchQtySnos(arColDetails[5]);
						StringTokenizer stQtySnos = new StringTokenizer(arColDetails[5],",");
						objDespatchClearanceDetails.setDespatchTotQty(stQtySnos.countTokens());
						objDespatchClearanceDetails.setDespatchStartOpn(Integer.parseInt(arColDetails[9]));
						objDespatchClearanceDetails.setDespatchEndOpn(Integer.parseInt(arColDetails[9]));
						objDespatchClearanceDetails.setModifiedBy(frm.getHidUserId());
					}
					try
					{
						boolean result = objDesClear.updateDespatchClearanceDetails(objDespatchClearanceDetails);
						if (result)
						{
							messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("prodacs.common.message.update","Despatch Clearance Entry"));
							if (!messages.isEmpty())
								saveMessages(request,messages);
							action = "success";
						}
					}
					catch (Exception ex)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.production.error",ex.getMessage()));
						if (!errors.isEmpty())
							saveErrors(request,errors);
						action = "failure";
					}
					
				}
				else if (frm.getFormAction().equalsIgnoreCase("makeInvalid") || frm.getFormAction().equalsIgnoreCase("makeValid"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Make InValid & Valid");
					}
					
					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
						v.add(new Integer(ids[i]));
					HashMap hm = new HashMap();
					try
					{
						if (frm.getFormAction().equalsIgnoreCase("makeValid"))
							hm = objDesClear.makeDespatchValid(v);
						else
							hm = objDesClear.makeDespatchInValid(v);
					}catch (ProductionException e)
					{
						e.printStackTrace();
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
							success++;
						else if(value == 1)
							notFound++;
						else if(value == 2)
							inUse++;
						else if(value == 3)
							locked++;
						else if(value == 4)
							general++;
					}
					if (frm.getFormAction().equalsIgnoreCase("makeInvalid"))
					{
						if(success<=1)
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"Despatch Clearance Entry"));
						else if(success>1)					
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"Despatch Clearance Entries"));
					}
					else
					{
						if(success<=1)
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"Despatch Clearance Entry"));
						else if(success>1)					
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"Despatch Clearance Entries"));
					}
					if(notFound == 1)
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Despatch Clearance Entry"));						
					else if(notFound > 1)
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Despatch Clearance Entries"));
					
					if(inUse == 1)
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Despatch Clearance Entry"));						
					else if(inUse > 1)
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Despatch Clearance Entries"));
					if(locked == 1)
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Despatch Clearance Entry"));						
					else if(locked > 1)
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Despatch Clearance Entries"));
					if(general == 1)
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Despatch Clearance Entry"));						
					else if(general > 1)
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Despatch Clearance Entries"));
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Despatch Clearance Entry"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Despatch Clearance Entries"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
				
			}catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if (BuildConfig.DMODE)
		{
			System.out.println("Despatch Edit Final Action: "+ action);
		}
		return mapping.findForward(action);
	}
}

/***
$Log: DespatchClearanceEditAction.java,v $
Revision 1.4  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.3  2005/07/25 06:21:05  vkrishnamoorthy
Modified as per Despatch and Shipment View from PostProduction.

Revision 1.2  2005/07/23 10:02:38  sponnusamy
Despatch Clearance Edit page was fully completed.

Revision 1.1  2005/07/22 13:39:06  sponnusamy
Despatch Edit Action was created and the view page codings are completed.

***/
