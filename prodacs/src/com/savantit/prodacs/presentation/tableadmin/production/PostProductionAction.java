/*
 * Created on Jan 7, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.production;

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

import com.savantit.prodacs.facade.SessionPostingDetails;
import com.savantit.prodacs.facade.SessionPostingDetailsHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PostProductionAction extends Action 
{
	public ActionForward execute (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action="failure";
		EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		
		/* Instance to PostProduction */
		if (form instanceof PostProductionForm)
		{
			PostProductionForm frm = (PostProductionForm) form;
			String[] arProd;
			String[] arNonProd;
			String[] arPop;
			String[] arRdl;
			String[] arFinal;
			String[] arDespatch;
			String[] arShipment;
			
			try
			{
				if (BuildConfig.DMODE)
				{				
					System.out.println("Posting Starts.");
				}
				
				/* 	Setting the JNDI name and Environment 	*/
			   	obj.setJndiName("SessionPostingDetailsBean");
		   		obj.setEnvironment();
		
				/* 	Creating the Home and Remote Objects 	*/
		   		SessionPostingDetailsHome postHomeObj = (SessionPostingDetailsHome)PortableRemoteObject.narrow(obj.getHome(),SessionPostingDetailsHome.class);
				SessionPostingDetails postObj = (SessionPostingDetails)PortableRemoteObject.narrow(postHomeObj.create(),SessionPostingDetails.class);

				if (frm.getFormAction().equalsIgnoreCase("posting"))
				{
					/* For Posting Production Details */
					StringTokenizer stProd = new StringTokenizer(frm.getHidProd(),"-");
					if (stProd.countTokens() > 0)
					{
						arProd = new String[stProd.countTokens()];
						int count = 0;
						while (stProd.hasMoreTokens())
						{
							arProd[count] = stProd.nextToken();
							count++;
						}
						Vector vecProd = new Vector();
						for (int i = 0; i < arProd.length; i++)
						{
							vecProd.add(new Integer(arProd[i]));
						}
						HashMap postProdDet = new HashMap();
						try
						{
							postProdDet = postObj.postProductionDetails(vecProd);	
						}catch (Exception e)
						{
							if (BuildConfig.DMODE)
							{
								System.out.println("Problem in Production Posting.");
							}
						}
						
						int posted = 0;
						int notPosted = 0;
						int problm = 0;
						for (Iterator it=postProdDet.entrySet().iterator(); it.hasNext(); ) 
						{ 
							Map.Entry entry = (Map.Entry)it.next(); 
							int value = ((Integer)entry.getValue()).intValue();
							if(value == 0)
							{
								posted++;
							}
							else if(value == 1)
							{
								notPosted++;
							}
							else if(value == 2)
							{
								problm++;
							}
						}
						if(posted <= 1)
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Production Entry"));
						else if(posted > 1)					
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Production Entries"));					
						if(notPosted == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notPosted),"Production Entry"));						
						}
						else if(notPosted > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notPosted),"Production Entries"));
						}
						
						if(problm == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(problm),"Production Entry"));						
						}
						else if(problm > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(problm),"Production Entries"));
						}
						if(postProdDet.size() < vecProd.size())
						{
							int diff = vecProd.size() - postProdDet.size();
							if(diff==1)
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general"));
							else
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general"));
						}									
						if(!errors.isEmpty())
							saveErrors(request,errors);					
						saveMessages(request,messages);		
						action = "success";
					}
					else
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("No Records Selected in Production.");
						}
					}

					/* For NonProduction Details */
					StringTokenizer stNonProd = new StringTokenizer(frm.getHidNonProd(),"-");
					if (stNonProd.countTokens() > 0)
					{
						arNonProd = new String[stNonProd.countTokens()];
						int count = 0;
						while (stNonProd.hasMoreTokens())
						{
							int te = Integer.parseInt(stNonProd.nextToken());
							arNonProd[count] = new Integer(te).toString();
							count++;
						}
						Vector vecNonProd = new Vector();
						for (int i = 0; i < arNonProd.length; i++)
						{
							vecNonProd.add(new Integer(arNonProd[i]));
						}
		
						HashMap postNonProdDet = new HashMap();
						try
						{
							postNonProdDet = postObj.postNonProductionDetails(vecNonProd);
						}catch (Exception e)
						{
							if (BuildConfig.DMODE)
							{
								System.out.println("Problem in Non-Production Posting.");
							}
						}
						
						int posted = 0;
						int notPosted = 0;
						int problm = 0;
						for (Iterator it=postNonProdDet.entrySet().iterator(); it.hasNext(); ) 
						{ 
							Map.Entry entry = (Map.Entry)it.next(); 
							int value = ((Integer)entry.getValue()).intValue();
							if(value == 0)
							{
								posted++;
							}
							else if(value == 1)
							{
								notPosted++;
							}
							else if(value == 2)
							{
								problm++;
							}
						}
						//ActionMessages messages = new ActionMessages();
						if(posted <= 1)
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Non-Production Entry"));
						else if(posted > 1)					
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Non-Production Entries"));					
						if(notPosted == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notPosted),"Non-Production Entry"));						
						}
						else if(notPosted > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notPosted),"Non-Production Entries"));
						}
						
						if(problm == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(problm),"Non-Production Entry"));						
						}
						else if(problm > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(problm),"Non-Production Entries"));
						}
						if(postNonProdDet.size() < vecNonProd.size())
						{
							int diff = vecNonProd.size() - postNonProdDet.size();
							if(diff==1)
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general"));
							else
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general"));
						}									
						if(!errors.isEmpty())
							saveErrors(request,errors);					
						saveMessages(request,messages);		
						action = "success";
					}
					else
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("No Records selected in Non-Production.");
						}
					}
				
					/* For Posting Pop Details */
					StringTokenizer stPop = new StringTokenizer(frm.getHidPop(),"-");
					if (stPop.countTokens() > 0)
					{
						arPop = new String[stPop.countTokens()];
						int count = 0;
						while (stPop.hasMoreTokens())
						{
							arPop[count] = stPop.nextToken();
							count++;
						}

						Vector vecPop = new Vector();
						for (int i = 0; i < arPop.length; i++)
						{
							vecPop.add(new Integer(arPop[i]));
						}
					
						HashMap postPopDet = new HashMap();
						try
						{
							postPopDet = postObj.postPopDetails(vecPop);
						}catch (Exception e)
						{
							if (BuildConfig.DMODE)
							{
								System.out.println("Problem in Pop Posting.");
							}
						}
						
						int posted = 0;
						int notPosted = 0;
						int problm = 0;
						for (Iterator it=postPopDet.entrySet().iterator(); it.hasNext(); ) 
						{ 
							Map.Entry entry = (Map.Entry)it.next(); 
							int value = ((Integer)entry.getValue()).intValue();
							if(value == 0)
							{
								posted++;
							}
							else if(value == 1)
							{
								notPosted++;
							}
							else if(value == 2)
							{
								problm++;
							}
						}
						//ActionMessages messages = new ActionMessages();
						if(posted <= 1)
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Pop Entry"));
						else if(posted > 1)					
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Pop Entries"));					
						if(notPosted == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notPosted),"Pop Entry"));						
						}
						else if(notPosted > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notPosted),"Pop Entries"));
						}
						
						if(problm == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(problm),"Pop Entry"));						
						}
						else if(problm > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(problm),"Pop Entries"));
						}
						if(postPopDet.size() < vecPop.size())
						{
							int diff = vecPop.size() - postPopDet.size();
							if(diff==1)
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general"));
							else
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general"));
						}									
						if(!errors.isEmpty())
							saveErrors(request,errors);					
						saveMessages(request,messages);		
						action = "success";
					}
					else
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("No Records Selected in Pop Details.");
						}
					}
				
					/* For Posting Radial Details */
					StringTokenizer stRdl = new StringTokenizer(frm.getHidRdl(),"-");
					if (stRdl.countTokens() > 0)
					{
						arRdl = new String[stRdl.countTokens()];
						int count = 0;
						while (stRdl.hasMoreTokens())
						{
							arRdl[count] = stRdl.nextToken();
							count++;
						}

						Vector vecRdl = new Vector();
						for (int i = 0; i < arRdl.length; i++)
						{
							vecRdl.add(new Integer(arRdl[i]));
						}
		
						HashMap postRadlDet = new HashMap();
						try
						{
							postRadlDet = postObj.postRadlProductionDetails(vecRdl);
						}catch (Exception e)
						{
							if (BuildConfig.DMODE)
							{
								System.out.println("Problem in Radl Posting.");
							}
						}
						
						int posted = 0;
						int notPosted = 0;
						int problm = 0;
						for (Iterator it=postRadlDet.entrySet().iterator(); it.hasNext(); ) 
						{ 
							Map.Entry entry = (Map.Entry)it.next(); 
							int value = ((Integer)entry.getValue()).intValue();
							if(value == 0)
							{
								posted++;
							}
							else if(value == 1)
							{
								notPosted++;
							}
							else if(value == 2)
							{
								problm++;
							}
						}
						//ActionMessages messages = new ActionMessages();
						if(posted <= 1)
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Radial Production Entry"));
						else if(posted > 1)					
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Radial Production Entries"));					
						if(notPosted == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notPosted),"Radial Production Entry"));						
						}
						else if(notPosted > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notPosted),"Radial Production Entries"));
						}
						
						if(problm == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(problm),"Radial Production Entry"));						
						}
						else if(problm > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(problm),"Radial Production Entries"));
						}
						if(postRadlDet.size() < vecRdl.size())
						{
							int diff = vecRdl.size() - postRadlDet.size();
							if(diff==1)
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general"));
							else
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general"));
						}									
						if(!errors.isEmpty())
							saveErrors(request,errors);					
						saveMessages(request,messages);		
						action = "success";
					}
					else
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("No Records selected in Radial Production.");
						}
					}
					/* For Posting Production Final Details */
					StringTokenizer stFinal = new StringTokenizer(frm.getHidProdFinal(),"-");
					if (stFinal.countTokens() > 0)
					{
						arFinal = new String[stFinal.countTokens()];
						int count = 0;
						while (stFinal.hasMoreTokens())
						{
							arFinal[count] = stFinal.nextToken();
							count++;
						}

						Vector vecFinal = new Vector();
						for (int i = 0; i < arFinal.length; i++)
						{
							vecFinal.add(new Integer(arFinal[i]));
						}
		
						HashMap postFinalDet = new HashMap();
						try
						{
							postFinalDet = postObj.postFinalProductionDetails(vecFinal);
						}catch (Exception e)
						{
							if (BuildConfig.DMODE)
							{
								System.out.println("Problem in Radl Posting.");
							}
						}
						
						int posted = 0;
						int notPosted = 0;
						int problm = 0;
						for (Iterator it=postFinalDet.entrySet().iterator(); it.hasNext(); ) 
						{ 
							Map.Entry entry = (Map.Entry)it.next(); 
							int value = ((Integer)entry.getValue()).intValue();
							if(value == 0)
							{
								posted++;
							}
							else if(value == 1)
							{
								notPosted++;
							}
							else if(value == 2)
							{
								problm++;
							}
						}
						//ActionMessages messages = new ActionMessages();
						if(posted <= 1)
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Production Final Entry"));
						else if(posted > 1)					
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Production Final Entries"));					
						if(notPosted == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notPosted),"Production Final Entry"));						
						}
						else if(notPosted > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notPosted),"Production Final Entries"));
						}
						
						if(problm == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(problm),"Production Final Entry"));						
						}
						else if(problm > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(problm),"Production Final Entries"));
						}
						if(postFinalDet.size() < vecFinal.size())
						{
							int diff = vecFinal.size() - postFinalDet.size();
							if(diff==1)
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general"));
							else
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general"));
						}									
						if(!errors.isEmpty())
							saveErrors(request,errors);					
						saveMessages(request,messages);		
						action = "success";
					}
					else
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("No Records selected in Production Final.");
						}
					}
					/* For Posting Shipment */
					StringTokenizer stShipment = new StringTokenizer(frm.getHidShipment(),"-");
					if (stShipment.countTokens() > 0)
					{
						arShipment = new String[stShipment.countTokens()];
						int count = 0;
						while (stShipment.hasMoreTokens())
						{
							arShipment[count] = stShipment.nextToken();
							count++;
						}

						Vector vecShipment = new Vector();
						for (int i = 0; i < arShipment.length; i++)
						{
							vecShipment.add(new Integer(arShipment[i]));
						}
		
						HashMap postShipmentDet = new HashMap();
						try
						{
							postShipmentDet = postObj.postShipmentDetails(vecShipment);
						}catch (Exception e)
						{
							if (BuildConfig.DMODE)
							{
								System.out.println("Problem in Posting Shipments.");
							}
						}
						
						int posted = 0;
						int notPosted = 0;
						int problm = 0;
						for (Iterator it=postShipmentDet.entrySet().iterator(); it.hasNext(); ) 
						{ 
							Map.Entry entry = (Map.Entry)it.next(); 
							int value = ((Integer)entry.getValue()).intValue();
							if(value == 0)
							{
								posted++;
							}
							else if(value == 1)
							{
								notPosted++;
							}
							else if(value == 2)
							{
								problm++;
							}
						}
						//ActionMessages messages = new ActionMessages();
						if(posted <= 1)
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Shipment Entry"));
						else if(posted > 1)					
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Shipment Entries"));					
						if(notPosted == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notPosted),"Shipment Entry"));						
						}
						else if(notPosted > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notPosted),"Shipment Entries"));
						}
						
						if(problm == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(problm),"Shipment Entry"));						
						}
						else if(problm > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(problm),"Shipment Entries"));
						}
						if(postShipmentDet.size() < vecShipment.size())
						{
							int diff = vecShipment.size() - postShipmentDet.size();
							if(diff==1)
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
							else
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general"));
						}									
						if(!errors.isEmpty())
							saveErrors(request,errors);					
						saveMessages(request,messages);		
						action = "success";
					}
					else
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("No Records selected in Shipment.");
						}
					}
					/* For Posting Despatch Clearance  */
					StringTokenizer stDespatch = new StringTokenizer(frm.getHidDespatch(),"-");
					if (stDespatch.countTokens() > 0)
					{
						arDespatch = new String[stDespatch.countTokens()];
						int count = 0;
						while (stDespatch.hasMoreTokens())
						{
							arDespatch[count] = stDespatch.nextToken();
							if (BuildConfig.DMODE)
							{
								System.out.println("Final: "+ arDespatch[count]);
							}
							count++;
						}

						Vector vecDespatch = new Vector();
						for (int i = 0; i < arDespatch.length; i++)
						{
							vecDespatch.add(new Integer(arDespatch[i]));
						}
		
						HashMap postDespatchDet = new HashMap();
						try
						{
							postDespatchDet = postObj.postDespatchDetails(vecDespatch);
						}catch (Exception e)
						{
							if (BuildConfig.DMODE)
							{
								System.out.println("Problem in Despatch Posting.");
							}
						}
						
						int posted = 0;
						int notPosted = 0;
						int problm = 0;
						for (Iterator it=postDespatchDet.entrySet().iterator(); it.hasNext(); ) 
						{ 
							Map.Entry entry = (Map.Entry)it.next(); 
							int value = ((Integer)entry.getValue()).intValue();
							if(value == 0)
							{
								posted++;
							}
							else if(value == 1)
							{
								notPosted++;
							}
							else if(value == 2)
							{
								problm++;
							}
						}
						//ActionMessages messages = new ActionMessages();
						if(posted <= 1)
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Despatch Clearance Entry"));
						else if(posted > 1)					
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(posted),"Despatch Clearance Entries"));					
						if(notPosted == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notPosted),"Despatch Clearance Entry"));						
						}
						else if(notPosted > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notPosted),"Despatch Clearance Entries"));
						}
						
						if(problm == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(problm),"Despatch Clearance Entry"));						
						}
						else if(problm > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(problm),"Despatch Clearance Entries"));
						}
						if(postDespatchDet.size() < vecDespatch.size())
						{
							int diff = vecDespatch.size() - postDespatchDet.size();
							if(diff==1)
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
							else
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general"));
						}									
						if(!errors.isEmpty())
							saveErrors(request,errors);					
						saveMessages(request,messages);		
						action = "success";
					}
					else
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("No Records selected in Despatch Clearance.");
						}
					}
				}
			}
			catch (Exception e)
			{
				if (BuildConfig.DMODE)
				{
					e.printStackTrace();
					System.out.println("Problem in Production Post Action.");
				}
			}
		}
		if (BuildConfig.DMODE)
		{
			System.out.println("Post Production Action :"+action);
		}
		return mapping.findForward(action);
	}
}

/***
$Log: PostProductionAction.java,v $
Revision 1.14  2005/07/27 07:30:06  vkrishnamoorthy
Error message corrected.

Revision 1.13  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.12  2005/07/25 04:47:38  vkrishnamoorthy
Modified as per Despatch and Shipment entries posting.

Revision 1.11  2005/07/24 09:17:31  vkrishnamoorthy
Despatch and Shipment included.

Revision 1.10  2005/07/18 12:47:18  vkrishnamoorthy
Unwanted print Statements removed.

Revision 1.9  2005/07/18 11:50:09  vkrishnamoorthy
Post Production modified as per Production Final.

Revision 1.8  2005/03/21 06:19:42  sponnusamy
Messges throwed correctly.

Revision 1.7  2005/03/09 07:28:47  vkrishnamoorthy
Modified as per PostProduction entry viewing.

Revision 1.6  2005/02/11 11:14:17  sponnusamy
Warnings removed

Revision 1.5  2005/02/03 05:09:15  vkrishnamoorthy
Log added.

***/