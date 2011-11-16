/*
 * Created on Jul 20, 2005
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

import com.savantit.prodacs.businessimplementation.production.EmployeeDtyOtDetails;
import com.savantit.prodacs.businessimplementation.production.FinalProductionDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.production.ProductionJobDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionJobQtyDetails;
import com.savantit.prodacs.facade.SessionFinalProductionDetailsManager;
import com.savantit.prodacs.facade.SessionFinalProductionDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProductionFinalEditAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/*Creating an instance of ProductionFinalEditForm.java*/
		if (form instanceof ProductionFinalEditForm)
		{
			ProductionFinalEditForm frm = (ProductionFinalEditForm) form;
			
			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			ActionErrors errors = new ActionErrors();
			try
			{
				/* 	Setting the JNDI name and Environment 	*/
			   	obj.setJndiName("SessionFinalProductionDetailsManager");
		   		obj.setEnvironment();

				/* 	Creating the Home and Remote Objects 	*/		   		
		   		SessionFinalProductionDetailsManagerHome objFinalProdHome = (SessionFinalProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionFinalProductionDetailsManagerHome.class);
				SessionFinalProductionDetailsManager objProdFinal = (SessionFinalProductionDetailsManager)PortableRemoteObject.narrow(objFinalProdHome.create(),SessionFinalProductionDetailsManager.class);
				if (frm.getFormAction().equalsIgnoreCase("fprodnview"))
				{
					action = "fprodnview";
					if (BuildConfig.DMODE)
						System.out.println("-->"+action);
				}
				else if (frm.getFormAction().equalsIgnoreCase("searchProd"))
				{
					action = "searchProd";
					if (BuildConfig.DMODE)
						System.out.println("-->"+action);
				}
				else if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
				}
				else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					/* Wheather Posted or Not */
					if (BuildConfig.DMODE)
						System.out.println("Posting Decision Starts.");
					try
					{
						objProdFinal.isModifyForFprod(frm.getId());
					}
					catch(ProductionException e)
					{
						if (BuildConfig.DMODE)
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
						System.out.println("Modification Starts.");
					try
					{
						/* Object for FinalProduction Details */
						FinalProductionDetails objFinalProductionDetails = new FinalProductionDetails();
						if (BuildConfig.DMODE)
							System.out.println(frm.getId());
						objFinalProductionDetails = objProdFinal.getFinalProductionDetails(frm.getId());
						
						frm.setProDate(objFinalProductionDetails.getFinalCrntDate().toString().substring(0,10));
						frm.setProShift(objFinalProductionDetails.getShiftId()+"");
						
						frm.setProWorkOrderHash(objFinalProductionDetails.getWoId()+"");
						frm.setHidWrkOrdId(objFinalProductionDetails.getWoId()+"");
						if (BuildConfig.DMODE)
							System.out.println("objFinalProductionDetails.getFinalProdTotQty() :"+objFinalProductionDetails.getFinalProdTotQty());
						frm.setProTotQtySnos(objFinalProductionDetails.getFinalProdTotQty()+"");
						
						/* This is for getting the Job Details */
						Vector vec_jobDet = objProdFinal.getProdnJobDetailsForUpdateByWorkOrder(objFinalProductionDetails.getWoId(),0,0,frm.getId(),0,0);
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
						Vector vec_operDet = objProdFinal.getFinalProdnJobOperationDetailsForUpdate(objFinalProductionDetails.getWoJbId(),frm.getId());
						frm.setHidWrkOrdJobId(objFinalProductionDetails.getWoJbId()+""); /* This is for Hidden Job Id */
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
								str_operDet = str_operDet + "-" + ((objProdJobQtyDet.getPendingOpnSnos().equalsIgnoreCase(""))?"empty":(objProdJobQtyDet.getPendingOpnSnos()));
								str_operDet = str_operDet + "-" + ((objProdJobQtyDet.getPostedOpnSnos().equalsIgnoreCase(""))?"empty":objProdJobQtyDet.getPostedOpnSnos());
								str_operDet = str_operDet + "-" + ((objProdJobQtyDet.getUnPostedOpnSnos().equalsIgnoreCase(""))?"empty":objProdJobQtyDet.getUnPostedOpnSnos());
								str_operDet = str_operDet + "-" + ((objProdJobQtyDet.getLastProdDate() == null)?"empty":objProdJobQtyDet.getLastProdDate().toString().substring(0,10));
							}
							else
							{
								str_operDet = str_operDet + "" + objProdJobQtyDet.getJobStatId();
								str_operDet = str_operDet + "-" + objProdJobQtyDet.getJobQtySno();
								str_operDet = str_operDet + "-" + ((objProdJobQtyDet.getPendingOpnSnos().equalsIgnoreCase(""))?"empty":(objProdJobQtyDet.getPendingOpnSnos()));
								str_operDet = str_operDet + "-" + ((objProdJobQtyDet.getPostedOpnSnos().equalsIgnoreCase(""))?"empty":objProdJobQtyDet.getPostedOpnSnos());
								str_operDet = str_operDet + "-" + ((objProdJobQtyDet.getUnPostedOpnSnos().equalsIgnoreCase(""))?"empty":objProdJobQtyDet.getUnPostedOpnSnos());
								str_operDet = str_operDet + "-" + ((objProdJobQtyDet.getLastProdDate() == null)?"empty":objProdJobQtyDet.getLastProdDate().toString().substring(0,10));
							}
						}
						if (BuildConfig.DMODE)
							System.out.println("Oper Array: "+ str_operDet);
						
						frm.setProStartOperation(objFinalProductionDetails.getFinalProdStartOpn()+"");
						frm.setProEndOperation(objFinalProductionDetails.getFinalProdEndOpn()+"");
						frm.setProTotalHours(objFinalProductionDetails.getFinalProdTotHrs()+"");
						
						if (BuildConfig.DMODE)
							System.out.println("Modified.");
					}
					catch (ProductionException pe)
					{
						if (BuildConfig.DMODE)
							pe.printStackTrace();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",pe.getExceptionMessage()));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						frm.setShowCount("");
						if (BuildConfig.DMODE)
							System.out.println("Modify Failure.");
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("update"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Updation Starts.");
					String[] arEmpDetails;
					String[] arEmpPrsnlDet;
					
					FinalProductionDetails objFinalProductionDetails = new FinalProductionDetails();
					
					objFinalProductionDetails.setFinalProdId(frm.getId());
					
					/* Date Conversion for Production Current Date */
					StringTokenizer st = new StringTokenizer(frm.getProDate(),"-");
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
					objFinalProductionDetails.setFinalCrntDate(ge.getTime());
					objFinalProductionDetails.setShiftId(Integer.parseInt(frm.getProShift()));
					objFinalProductionDetails.setWoJbId(Integer.parseInt(frm.getHidWrkOrdJobId()));
					objFinalProductionDetails.setFinalProdQtySnos(frm.getProTotQtySnos());
					if (BuildConfig.DMODE)
						System.out.println("Qty Sno: "+ objFinalProductionDetails.getFinalProdQtySnos());
					/* Calculate no. of Qty's */
					StringTokenizer stTotQty = new StringTokenizer(frm.getProTotQtySnos().trim(),",");
					objFinalProductionDetails.setFinalProdTotQty(stTotQty.countTokens());
					if (BuildConfig.DMODE)
						System.out.println("Tot Qty: "+ stTotQty.countTokens());
					objFinalProductionDetails.setFinalProdStartOpn(Integer.parseInt(frm.getProStartOperation()));
					objFinalProductionDetails.setFinalProdEndOpn(Integer.parseInt(frm.getProEndOperation()));
					objFinalProductionDetails.setFinalProdTotHrs(Float.parseFloat(frm.getProTotalHours()));
					
					/* Splitting all Details of Employees */
					StringTokenizer stEmpDet = new StringTokenizer(frm.getHidAllEmpDet(),"^");
					arEmpDetails = new String[stEmpDet.countTokens()];
					int recCount = 0;
					while (stEmpDet.hasMoreTokens())
					{
						arEmpDetails[recCount] = stEmpDet.nextToken();
						recCount++;
					}

					Vector vec_empDutyOtDet = new Vector();
					for (int i = 0; i < arEmpDetails.length; i++)
					{
						EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
						StringTokenizer stEmpPrsnlDet = new StringTokenizer(arEmpDetails[i],"-");
						arEmpPrsnlDet = new String[stEmpPrsnlDet.countTokens()];
						recCount = 0;
						System.out.println("-->"+arEmpDetails[i]);
						while (stEmpPrsnlDet.hasMoreTokens())
						{
							objEmployeeDtyOtDetails.setEmpType(stEmpPrsnlDet.nextToken());
							String empTypId =stEmpPrsnlDet.nextToken();
							String empName = stEmpPrsnlDet.nextToken();
							objEmployeeDtyOtDetails.setEmpId(Integer.parseInt(stEmpPrsnlDet.nextToken()));
	
							vec_empDutyOtDet.add(objEmployeeDtyOtDetails);	
						}
						objFinalProductionDetails.setProdnEmpHrsDetails(vec_empDutyOtDet);
					}
					
					objFinalProductionDetails.setModifiedBy(frm.getHidUserId());
					boolean update = objProdFinal.updateFinalProductionDetails(objFinalProductionDetails);
					
					if (update == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.update","Final Production Entry",""));
						
						saveMessages(request,message);
						action="success";
					}
					else
					{
						if (BuildConfig.DMODE)
							System.out.println("Else case: "+update);
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						action="failure";				  			
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
						hm = objProdFinal.makeFinalProductionInValid(v);
					}catch (ProductionException e)
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("Vec Size :"+v.size()+" & HM Size :"+hm.size());
							e.printStackTrace();
							System.out.println("Problem in MakeInvalid");
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"Final Production Entry"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"Final Production Entries"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Final Production Entry"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Final Production Entries"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Final Production Entry"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Final Production Entries"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Final Production Entry"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Final Production Entries"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Final Production Entry"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Final Production Entries"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Final Production Entry"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Final Production Entries"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
				if (frm.getFormAction().equalsIgnoreCase("makeValid"))
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
						hm = objProdFinal.makeFinalProductionValid(v);
					}catch (ProductionException e)
					{
						if (BuildConfig.DMODE)
						{
							e.printStackTrace();
							System.out.println("Problem in MakeValid");
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"Final Production Entry"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"Final Production Entries"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Final Production Entry"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Final Production Entries"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Final Production Entry"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Final Production Entries"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Final Production Entry"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Final Production Entries"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Final Production Entry"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Final Production Entries"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Final Production Entry"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Final Production Entries"));
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
			System.out.println("ProductionFinalEdit Action :"+action);
		return mapping.findForward(action);
	}
}


/***
$Log: ProductionFinalEditAction.java,v $
Revision 1.7  2005/08/01 09:29:12  vkrishnamoorthy
Total Hours included.

Revision 1.6  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.5  2005/07/23 09:24:36  vkrishnamoorthy
Despatch and Shipment added to getProdnJobDetailsForUpdateByWorkOrder().

Revision 1.4  2005/07/23 08:30:44  vkrishnamoorthy
General Exception caught and thrown appropriately.

Revision 1.3  2005/07/22 06:03:38  vkrishnamoorthy
Ids for Make Valid and Invalid changed from String to Integer.

Revision 1.2  2005/07/22 05:28:09  vkrishnamoorthy
Make Valid, Invalid added.

Revision 1.1  2005/07/21 07:45:18  vkrishnamoorthy
Initial commit on ProDACS.

***/