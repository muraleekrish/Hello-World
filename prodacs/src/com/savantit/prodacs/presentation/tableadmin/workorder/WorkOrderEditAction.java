/*
 * Created on Nov 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workorder;

import java.io.InputStream;
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

import com.savantit.prodacs.businessimplementation.workorder.WOJobDetails;
import com.savantit.prodacs.businessimplementation.workorder.WOJobOpnDetails;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderDetails;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderException;
import com.savantit.prodacs.facade.SessionWorkOrderDetailsManager;
import com.savantit.prodacs.facade.SessionWorkOrderDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.presentation.tableadmin.jobmaster.StandardHours;
import com.savantit.prodacs.util.CastorXML;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class WorkOrderEditAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/* Create an instanceof WorkOrderEditForm.java*/
		if (form instanceof WorkOrderEditForm)
		{ 
			WorkOrderEditForm frm = (WorkOrderEditForm)form;
		    InputStream is = getClass().getClassLoader().getResourceAsStream("jobconfig.xml");
		    StandardHours objStdHours = new StandardHours();
		    objStdHours = (StandardHours)CastorXML.fromXML(is,objStdHours.getClass());
			
			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			ActionErrors errors = new ActionErrors();
			try 
			{
				/* 	Setting the JNDI name and Environment 	*/
				obj.setJndiName("SessionWorkOrderDetailsManagerBean");
				obj.setEnvironment();
				
				/* 	Creating the Home and Remote Objects 	*/
				SessionWorkOrderDetailsManagerHome woHomeObj = (SessionWorkOrderDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkOrderDetailsManagerHome.class);
				SessionWorkOrderDetailsManager workOrderObj = (SessionWorkOrderDetailsManager)PortableRemoteObject.narrow(woHomeObj.create(),SessionWorkOrderDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
				}
				else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					/* Object to WorkOrder Details */
					WorkOrderDetails objWorkOrderDetails = new WorkOrderDetails();
					
					/*try
					 {
					 if (BuildConfig.DMODE)
					 {
					 System.out.println("Mod Decision Starts.");
					 }
					 workOrderObj.isWoModify(frm.getId());
					 if (BuildConfig.DMODE)
					 {
					 System.out.println("frm.getId() :"+frm.getId());
					 }
					 }catch (WorkOrderException e)
					 {
					 errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
					 if(!errors.isEmpty())
					 saveErrors(request,errors);
					 if (BuildConfig.DMODE)
					 {
					 System.out.println("Modification Failure.");
					 }
					 action = "success";
					 }*/
					
					objWorkOrderDetails = workOrderObj.getWorkOrderDetails(frm.getId());
					frm.setWoDate(objWorkOrderDetails.getWoCreatedDate().toString().substring(0,10));
					frm.setWoEstCompletion(objWorkOrderDetails.getWoEstmdCompleteDate().toString().substring(0,10));
					frm.setWoCustomerType(objWorkOrderDetails.getCustTypeId()+"");
					frm.setWoCustomerName(objWorkOrderDetails.getCustId()+"");
					frm.setWoHash(objWorkOrderDetails.getWorkOrderNo());
					frm.setWoContactName(objWorkOrderDetails.getContactPerson());
					Vector vec_woJobDet = objWorkOrderDetails.getWOJobDetails();
					
					String woJobDet = "";
					for (int i = 0; i < vec_woJobDet.size(); i++)
					{
						/* Object to JobDetails */
						WOJobDetails objJobDetails = new WOJobDetails();
						objJobDetails = (WOJobDetails) vec_woJobDet.get(i);
						if (BuildConfig.DMODE)
						{
							System.out.println("DC No :"+objJobDetails.getWoDCNo());
							System.out.println("DC Date :"+objJobDetails.getWoDCDate());
						}
						if (i != 0)
						{
							woJobDet = woJobDet + "^";
						}
						woJobDet = woJobDet + "" + objJobDetails.getJobId() + "~" + objJobDetails.getGeneralName() + "~" + objJobDetails.getJobName() + "~" + 
						objJobDetails.getJobDrwgNo() + "~" + objJobDetails.getJobRvsnNo() + "~" + objJobDetails.getJobMatlType() + "~" + 
						objJobDetails.getJobQtyStartSno() + "~" + objJobDetails.getJobQty() + "~" + objJobDetails.getGeneralId() + "~" + objJobDetails.getWoDCNo() + "~" + 
						objJobDetails.getWoDCDate().toString().substring(0,10) + "~" + objJobDetails.isProdEntered();
						
						Vector vec_woJobOpnDetails = objJobDetails.getWOJobOpnDetails();
						for (int j = 0; j < vec_woJobOpnDetails.size(); j++)
						{
							/* Object to WorkOrderJobOPerationDetails */
							WOJobOpnDetails objJobOpnDet = new WOJobOpnDetails();
							
							objJobOpnDet = (WOJobOpnDetails) vec_woJobOpnDetails.get(j);
							
							woJobDet = woJobDet + "~" + objJobOpnDet.getOpnGpId() + "~" + objJobOpnDet.getOpnSerialNo() + "~" + objJobOpnDet.getOpnGpCode() + "~" + objJobOpnDet.getOpnName() + "~" + objJobOpnDet.getOpnStdHrs() + "~" + objJobOpnDet.isOpnIncentive();
						}
					}
					
					frm.setHidModifyRecDet(woJobDet);
					if (BuildConfig.DMODE)
					{
						System.out.println("ModFields: "+ frm.getHidModifyRecDet());
						System.out.println("Modified.");
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("update"))
				{					
						if (BuildConfig.DMODE)
						{
							System.out.println("Updation.");
						}
	
					/* Work Order Details Object */
					WorkOrderDetails objWorkorderDetails = new WorkOrderDetails();
	
					String woJobList[] = null;
					String arWoJobList[] = null;
					String arWoJobOperList[] = null;
					String arWoJobOperRecList[] = null;
					String arWoJobOperDet[] = null;					
	
					objWorkorderDetails.setWorkOrderId(frm.getId());
					if (BuildConfig.DMODE)
		            {
						System.out.println("WoId: "+objWorkorderDetails.getWorkOrderId());
		            }
					objWorkorderDetails.setWorkOrderNo(frm.getWoHash());
	
					/* Date Conversion for WorkOrder Date */
					StringTokenizer st = new StringTokenizer(frm.getWoDate(),"-");
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
							
					objWorkorderDetails.setWoCreatedDate(ge.getTime());
	
					/* Date Conversion for WorkOrder Completion Date */
					st = new StringTokenizer(frm.getWoEstCompletion(),"-");
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
							
					objWorkorderDetails.setWoEstmdCompleteDate(ge.getTime());
					objWorkorderDetails.setContactPerson(frm.getWoContactName());
					objWorkorderDetails.setCustId(Integer.parseInt(frm.getWoCustomerName()));
					
					Vector vec_woJobDet = new Vector();
					if (BuildConfig.DMODE)
		            {
						System.out.println("Job Details: "+ frm.getFinalWoJobDet());
						System.out.println("Operation Details: "+ frm.getFinalWoOperDet());
		            }
	
					/* Tokenize the Operation Details */
					StringTokenizer stWoOperList = new StringTokenizer(frm.getFinalWoOperDet(),"$");
					arWoJobOperList = new String[stWoOperList.countTokens()];
					int opCount = 0;
					while (stWoOperList.hasMoreTokens())
					{
						arWoJobOperList[opCount] = stWoOperList.nextToken();
						opCount++;
					}
	
					/* Tokenize the WorkOrder Details */
					StringTokenizer stWoList = new StringTokenizer(frm.getFinalWoJobDet(),"^");
					woJobList = new String[stWoList.countTokens()];
					int count = 0;
					while (stWoList.hasMoreTokens())
					{
						/* Details object for WorkOrderJobDetails */
						WOJobDetails objWOJobDetails = new WOJobDetails();
						woJobList[count] = stWoList.nextToken();
	
						StringTokenizer stWoListRec = new StringTokenizer(woJobList[count],"~");
						arWoJobList = new String[stWoListRec.countTokens()];
						int recCount = 0;
						while (stWoListRec.hasMoreTokens())
						{
							arWoJobList[recCount] = stWoListRec.nextToken();
							recCount++;
							if(BuildConfig.DMODE)
							System.out.println("arWoJobList["+recCount+"]");
						}
						objWOJobDetails.setJobId(Integer.parseInt(arWoJobList[0])); // Job Id
						objWOJobDetails.setJobName(arWoJobList[1]); // Job Name
						objWOJobDetails.setJobSerialNo(count+1); // Seriel No
						objWOJobDetails.setJobDrwgNo(arWoJobList[2]); // Drawing No
						objWOJobDetails.setJobRvsnNo(arWoJobList[3]); // Revision No
						objWOJobDetails.setJobMatlType(arWoJobList[4]); // Material Type
						objWOJobDetails.setJobQtyStartSno(Integer.parseInt(arWoJobList[5])); // Start Sno
						objWOJobDetails.setJobQty(Integer.parseInt(arWoJobList[6])); // Job Qty
						objWOJobDetails.setWoDCNo(arWoJobList[7]); // DC No.
						int y = 0;
						int m = 0;
						int d = 0;
						if (BuildConfig.DMODE)
			            {
							System.out.println("-->"+arWoJobList[8]);
			            }
						StringTokenizer stDate = new StringTokenizer(arWoJobList[8],"-");
	
						if (stDate.hasMoreTokens())
						{
							y = Integer.parseInt(stDate.nextToken().trim());
						}
						if (stDate.hasMoreTokens())
						{
							m = Integer.parseInt(stDate.nextToken().trim());
						}
						if (stDate.hasMoreTokens())
						{
							d = Integer.parseInt(stDate.nextToken().trim());
						}
						GregorianCalendar dcDate = new GregorianCalendar(y,m-1,d);
						if (BuildConfig.DMODE)
			            {
							System.out.println("--->"+dcDate.getTime());
			            }
						objWOJobDetails.setWoDCDate(dcDate.getTime());
						objWOJobDetails.setProdEntered(arWoJobList[9].trim().equalsIgnoreCase("true")?true:false);
						Vector vec_opnDet = new Vector();
						StringTokenizer stWoJobOperDet = new StringTokenizer(arWoJobOperList[count],"^");
						arWoJobOperDet = new String[stWoJobOperDet.countTokens()];
						int opnCount = 0;
						while (stWoJobOperDet.hasMoreTokens())
						{
							arWoJobOperDet[opnCount] = stWoJobOperDet.nextToken();
							
							StringTokenizer stWoJobOpnDet = new StringTokenizer(arWoJobOperDet[opnCount],"~");
							arWoJobOperRecList = new String[stWoJobOpnDet.countTokens()];
							int opnRecCount = 0;
							while (stWoJobOpnDet.hasMoreTokens())
							{
								arWoJobOperRecList[opnRecCount] = stWoJobOpnDet.nextToken();
								if (BuildConfig.DMODE)
					            {
									System.out.println("arWoJobOperRecList["+opnRecCount+"]: "+arWoJobOperRecList[opnRecCount]);
					            }
								opnRecCount++;
							}
	
							/* Object to WorkOrder Job Operation Details */
							WOJobOpnDetails objJobOpnDetails = new WOJobOpnDetails();
	
							objJobOpnDetails.setOpnGpId(Integer.parseInt(arWoJobOperRecList[0]));
							objJobOpnDetails.setOpnSerialNo(Integer.parseInt(arWoJobOperRecList[1]));
							//objJobOpnDetails.setOpnGpCode(arWoJobOperRecList[i+2]);
							objJobOpnDetails.setOpnName(arWoJobOperRecList[3]);
							if((objStdHours.isStdhrs()) && (objStdHours.isOpnlevelstdhrs()))
							{
								objJobOpnDetails.setOpnStdHrs(Float.parseFloat(arWoJobOperRecList[4]));
							}
							if ((objStdHours.isIncentive()) && (objStdHours.isJobopnlevelincentive()))
							{
								objJobOpnDetails.setOpnIncentive(arWoJobOperRecList[5].equalsIgnoreCase("true")?true:false);
							}
							
							vec_opnDet.add(objJobOpnDetails);
	
							opnCount++;
						}
						objWOJobDetails.setWOJobOpnDetails(vec_opnDet);
						vec_woJobDet.add(objWOJobDetails);
						count++;
					}
					
	
					objWorkorderDetails.setWOJobDetails(vec_woJobDet);
					
					boolean up = workOrderObj.updateWorkOrder(objWorkorderDetails);
					
					if (up == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.updated","Work Order",frm.getWoHash()));
						
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
				else if (frm.getFormAction().equalsIgnoreCase("makeValid"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("MakeValid");
					}
					
					String ids[] = frm.getIds();
					Vector tot = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						tot.add(new Integer(ids[i]));
					}
					HashMap hm = new HashMap();
					try
					{
						hm = workOrderObj.makeWorkOrderValid(tot);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("Problem while MakeValid in WorkOrderEdit Action");
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"WorkOrder"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"WorkOrders"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"WorkOrder"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"WorkOrders"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"WorkOrder"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"WorkOrders"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"WorkOrder"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"WorkOrders"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"WorkOrder"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"WorkOrders"));
					}
					if(hm.size() < tot.size())
					{
						int diff = tot.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"WorkOrder"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"WorkOrders"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
				else if (frm.getFormAction().equalsIgnoreCase("makeInvalid"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("MakeInValid");
					}
					
					String ids[] = frm.getIds();
					Vector tot = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						tot.add(new Integer(ids[i]));
					}
					HashMap hm = new HashMap();
					try
					{
						hm = workOrderObj.makeWorkOrderInValid(tot);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("Problem while MakeInValid in WorkOrderEdit Action");
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"WorkOrder"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"WorkOrders"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"WorkOrder"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"WorkOrders"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"WorkOrder"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"WorkOrders"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"WorkOrder"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"WorkOrders"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"WorkOrder"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"WorkOrders"));
					}
					if(hm.size() < tot.size())
					{
						int diff = tot.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"WorkOrder"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"WorkOrders"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
			}
			catch (WorkOrderException e)
			{
				if (BuildConfig.DMODE)
				{
					System.out.println("WrkOrd Exception");
				}
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action="failure";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action="failure";				  			
			}
		}
		if (BuildConfig.DMODE)
		{
			System.out.println("WorkOrder Edit: "+action);
		}
		return mapping.findForward(action);
	}
	
}
/***
$Log: WorkOrderEditAction.java,v $
Revision 1.21  2006/02/20 12:11:44  kduraisamy
buildConfig.DMODE included.

Revision 1.20  2005/08/23 12:54:06  vkrishnamoorthy
isProdEntered flag checked for modification.

Revision 1.19  2005/08/04 05:31:35  vkrishnamoorthy
Modified as per Incentive flag settings and StringTokenizer's removed.

Revision 1.18  2005/08/02 14:39:42  vkrishnamoorthy
Modified as per displaying Incentive, if exists.

Revision 1.17  2005/08/02 05:17:56  vkrishnamoorthy
Production Entered WO's validated for modification.

Revision 1.16  2005/07/28 14:46:48  vkrishnamoorthy
Modified as per DC Date and DC No.

Revision 1.15  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.14  2005/07/25 10:18:00  vkrishnamoorthy
WO DC Date added.

Revision 1.13  2005/07/24 08:57:26  vkrishnamoorthy
DC No included.

Revision 1.12  2005/06/10 13:54:23  vkrishnamoorthy
Script modified with change of delimiters while modifying and updating.

Revision 1.11  2005/03/12 09:04:20  sponnusamy
Date Problems are Corrected.

Revision 1.10  2005/03/02 05:21:19  sponnusamy
WorkOrderEdit completed.

Revision 1.9  2005/02/26 04:57:34  vkrishnamoorthy
WorkOrderEditAction modified.

Revision 1.8  2005/02/24 11:49:00  vkrishnamoorthy
Partially modified.

Revision 1.7  2005/02/11 11:21:49  sponnusamy
Warnings removed

Revision 1.6  2005/02/07 12:11:27  sponnusamy
DummyWorkOrder is completed

Revision 1.5  2005/01/20 08:19:33  sponnusamy
Exception Messages thrown

Revision 1.4  2005/01/19 05:36:11  sponnusamy
IsModify condition Added.

Revision 1.3  2004/12/16 09:20:34  sponnusamy
WorkOrder Controller is fully completed.

Revision 1.2  2004/12/15 16:28:07  sponnusamy
WorkOrder controlling part is partially completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/