/*
 * Created on Jul 23, 2005
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

import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.production.ProductionJobDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionJobQtyDetails;
import com.savantit.prodacs.businessimplementation.production.ShipmentDetails;
import com.savantit.prodacs.facade.SessionShipmentDetailsManager;
import com.savantit.prodacs.facade.SessionShipmentDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ShipmentReferenceEditAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		ActionMessages messages = new ActionMessages();
		
		/* Creating an instance of ShipmentReferenceEditForm.java*/
		if (form instanceof ShipmentReferenceEditForm)
		{
			ShipmentReferenceEditForm frm = (ShipmentReferenceEditForm) form;
			
			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			ActionErrors errors = new ActionErrors();
			try
			{
				/* 	Setting the JNDI name and Environment 	*/
			   	obj.setJndiName("SessionShipmentDetailsManager");
		   		obj.setEnvironment();
				
				/* 	Setting the JNDI name and Environment 	*/
				SessionShipmentDetailsManagerHome objShipmentHome = (SessionShipmentDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionShipmentDetailsManagerHome.class);
				SessionShipmentDetailsManager objShipment = (SessionShipmentDetailsManager)PortableRemoteObject.narrow(objShipmentHome.create(),SessionShipmentDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("shipmentView"))
				{
					action = "shipmentView";
					if (BuildConfig.DMODE)
						System.out.println("-->"+action);
				}
				else if (frm.getFormAction().equalsIgnoreCase("searchShipment"))
				{
					action = "searchShipment";
					if (BuildConfig.DMODE)
						System.out.println("-->"+action);
				}
				else if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
				}
				else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					/* Whether Posted or Not */
					if (BuildConfig.DMODE)
						System.out.println("Posting Decision Starts.");
					try
					{
						objShipment.isModifyForDespatch(frm.getId());
					}
					catch(ProductionException e)
					{
						e.printStackTrace();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						frm.setShowCount("");
						if (BuildConfig.DMODE)
							System.out.println("Posting Failure.");
						action = "success";
					}
					if (BuildConfig.DMODE)
						System.out.println("Modification Starts...");
					try
					{
						ShipmentDetails objShipmentDetails = new ShipmentDetails();
						objShipmentDetails = objShipment.getShipmentDetails(frm.getId());
						
						frm.setProDate(objShipmentDetails.getShipmentCrntDate().toString().substring(0,10));
						frm.setProShift(objShipmentDetails.getShiftId()+"");
						frm.setProWorkOrderHash(objShipmentDetails.getWoId()+"");
						frm.setHidWrkOrdId(objShipmentDetails.getWoId()+"");
						frm.setProTotQtySnos(objShipmentDetails.getShipmentQtySnos());
						frm.setProDCNo(objShipmentDetails.getDeliveryChallanNo());
						
						/* Getting the Job Details by passing WO Id*/
						Vector vec_jobDet = objShipment.getProdnJobDetailsForUpdateByWorkOrder(objShipmentDetails.getWoId(),0,0,0,0,frm.getId());
						if (BuildConfig.DMODE)
							System.out.println("Job Vector Size :"+vec_jobDet.size());
	
						String str_jobDet = "";
						for (int i = 0; i < vec_jobDet.size(); i++)
						{
							/* Object for Production Final Job Details */
							ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
							objProductionJobDetails = (ProductionJobDetails) vec_jobDet.get(i);
							
							if (i != 0)
							{
								str_jobDet = str_jobDet + "^";
							}
							if (i == 0)
							{
								str_jobDet = objProductionJobDetails.getWoJbId()+"";
								str_jobDet = str_jobDet + "-" + objProductionJobDetails.getJobName();
								str_jobDet = str_jobDet + "-" + objProductionJobDetails.getTotQty();
								str_jobDet = str_jobDet + "-" + ((objProductionJobDetails.getPendingQtySnos().equalsIgnoreCase(""))?"empty":objProductionJobDetails.getPendingQtySnos());
								str_jobDet = str_jobDet + "-" + ((objProductionJobDetails.getPostedQtySnos().equalsIgnoreCase(""))?"empty":objProductionJobDetails.getPostedQtySnos());
								str_jobDet = str_jobDet + "-" + ((objProductionJobDetails.getUnPostedQtySnos().equalsIgnoreCase(""))?"empty":objProductionJobDetails.getUnPostedQtySnos());
								str_jobDet = str_jobDet + "-" + ((objProductionJobDetails.getLastProdnDate() == null)?"empty":objProductionJobDetails.getLastProdnDate().toString().substring(0,10));
							}
							else
							{
								str_jobDet = str_jobDet + "" +objProductionJobDetails.getWoJbId();
								str_jobDet = str_jobDet + "-" + objProductionJobDetails.getJobName();
								str_jobDet = str_jobDet + "-" + objProductionJobDetails.getTotQty();
								str_jobDet = str_jobDet + "-" + ((objProductionJobDetails.getPendingQtySnos().equalsIgnoreCase(""))?"empty":objProductionJobDetails.getPendingQtySnos());
								str_jobDet = str_jobDet + "-" + ((objProductionJobDetails.getPostedQtySnos().equalsIgnoreCase(""))?"empty":objProductionJobDetails.getPostedQtySnos());
								str_jobDet = str_jobDet + "-" + ((objProductionJobDetails.getUnPostedQtySnos().equalsIgnoreCase(""))?"empty":objProductionJobDetails.getUnPostedQtySnos());
								str_jobDet = str_jobDet + "-" + ((objProductionJobDetails.getLastProdnDate() == null)?"empty":objProductionJobDetails.getLastProdnDate().toString().substring(0,10));
							}
						}
						frm.setModJobDet(str_jobDet);
						if (BuildConfig.DMODE)
							System.out.println("Job ArrayFinal: "+ str_jobDet);
						
						/* Finding the JobOperation Details by WoJbId */
						Vector vec_operDet = objShipment.getShipmentOperationDetailsForUpdate(objShipmentDetails.getWoJbId(),frm.getId());
						frm.setHidWrkOrdJobId(objShipmentDetails.getWoJbId()+""); /* This is for Hidden Job Id */
						String str_operDet = "";
						for (int i = 0; i < vec_operDet.size(); i++)
						{
							/* Object to ProductionJobDetails */
							ProductionJobQtyDetails objProdJobQtyDet = new ProductionJobQtyDetails();
							objProdJobQtyDet = (ProductionJobQtyDetails) vec_operDet.get(i);
							if (i != 0)
							{
								str_operDet = str_operDet + "^";
							}
							if (i == 0)
							{
								str_operDet = objProdJobQtyDet.getJobStatId()+"";
								str_operDet = str_operDet + "-" + objProdJobQtyDet.getJobQtySno();
							}
							else
							{
								str_operDet = str_operDet + "" + objProdJobQtyDet.getJobStatId();
								str_operDet = str_operDet + "-" + objProdJobQtyDet.getJobQtySno();
							}
						}
						if (BuildConfig.DMODE)
						{
							System.out.println("Oper Array: "+ str_operDet);
							System.out.println("Modification Ends.");
						}
					}
					catch(ProductionException e)
					{
						if (BuildConfig.DMODE)
							e.printStackTrace();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						action="failure";				  			
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("update"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Shipment Reference Update Starts...");
					ShipmentDetails objShipmentDetails = new ShipmentDetails();
					if (BuildConfig.DMODE)
						System.out.println("frm.getHidProdList() :"+frm.getHidProdList());
					StringTokenizer stRowDetails = new StringTokenizer(frm.getHidProdList(),"$");
					int count = 0;
					String[] arColDetails = new String[stRowDetails.countTokens()];
					while (stRowDetails.hasMoreTokens())
					{
						arColDetails[count] = stRowDetails.nextToken();
						if (BuildConfig.DMODE)
							System.out.println("arColDetails["+count+"] :"+arColDetails[count]);
						count++;
					}

					objShipmentDetails.setShipmentId(frm.getId());
					
					/* Date Conversion for Production Current Date */
					StringTokenizer st = new StringTokenizer(arColDetails[0].trim(),"-");
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
					
					objShipmentDetails.setShipmentCrntDate(ge.getTime());
					objShipmentDetails.setShiftId(Integer.parseInt(arColDetails[1].trim()));
					objShipmentDetails.setWoJbId(Integer.parseInt(arColDetails[3].trim()));
					StringTokenizer stQtySnos = new StringTokenizer(arColDetails[4],",");
					objShipmentDetails.setShipmentQtySnos(arColDetails[4]);
					if (BuildConfig.DMODE)
						System.out.println("Qty Sno: "+ objShipmentDetails.getShipmentQtySnos());
					/* Calculate no. of Qty's */
					StringTokenizer stTotQty = new StringTokenizer(arColDetails[5],",");
					objShipmentDetails.setShipmentTotQty(stTotQty.countTokens());
					if (BuildConfig.DMODE)
						System.out.println("Tot Qty: "+ stTotQty.countTokens());
					objShipmentDetails.setShipmentStartOpn(Integer.parseInt(arColDetails[5]));
					objShipmentDetails.setShipmentEndOpn(Integer.parseInt(arColDetails[5]));
					objShipmentDetails.setDeliveryChallanNo(arColDetails[6]);
					objShipmentDetails.setModifiedBy(frm.getHidUserId());
					try
					{
						boolean result = objShipment.updateShipmentDetails(objShipmentDetails);
						if (result)
						{
							messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("prodacs.common.message.update","Shipment Reference Entry"));
							if (!messages.isEmpty())
								saveMessages(request,messages);
							action = "success";
						}
					}
					catch (ProductionException e)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.production.error",e.getExceptionMessage()));
						if (!errors.isEmpty())
							saveErrors(request,errors);
						action = "failure";
					}
					catch (Exception ex)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.production.error",ex.getMessage()));
						if (!errors.isEmpty())
							saveErrors(request,errors);
						action = "failure";
					}
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
						if (BuildConfig.DMODE)
							System.out.println("Invalid Ids: "+ ids[i]);
					}
					HashMap hm = new HashMap();
					try
					{
						hm = objShipment.makeShipmentInValid(v);
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
					if(success<=1)
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"Shipment Reference Entry"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"Shipment Reference Entries"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Shipment Reference Entry"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Shipment Reference Entries"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Shipment Reference Entry"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Shipment Reference Entries"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Shipment Reference Entry"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Shipment Reference Entries"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Shipment Reference Entry"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Shipment Reference Entries"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Shipment Reference Entry"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Shipment Reference Entries"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
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
						if (BuildConfig.DMODE)
							System.out.println("Valid Ids: "+ ids[i]);
					}
					HashMap hm = new HashMap();
					try
					{
						hm = objShipment.makeShipmentValid(v);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
							System.out.println("Problem in MakeValid");
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
					if(success<=1)
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"Shipment Reference Entry"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"Shipment Reference Entries"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Shipment Reference Entry"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Shipment Reference Entries"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Shipment Reference Entry"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Shipment Reference Entries"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Shipment Reference Entry"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Shipment Reference Entries"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Shipment Reference Entry"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Shipment Reference Entries"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Shipment Reference Entry"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Shipment Reference Entries"));
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
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action="failure";				  			
			}
		}
		if (BuildConfig.DMODE)
			System.out.println("Shipment Reference Edit Action :"+action);
		return mapping.findForward(action);
	}
}


/***
$Log: ShipmentReferenceEditAction.java,v $
Revision 1.4  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.3  2005/07/25 06:21:05  vkrishnamoorthy
Modified as per Despatch and Shipment View from PostProduction.

Revision 1.2  2005/07/23 18:56:34  vkrishnamoorthy
Make Valid, Invalid added.

Revision 1.1  2005/07/23 17:38:48  vkrishnamoorthy
Initial commit on ProDACS.

***/