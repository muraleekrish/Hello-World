/*
 * Created on Dec 31, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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


import com.savantit.prodacs.businessimplementation.job.OperationDetails;
import com.savantit.prodacs.businessimplementation.production.EmployeeDtyOtDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.production.ProductionJobDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionJobQtyDetails;
import com.savantit.prodacs.facade.SessionProductionDetailsManager;
import com.savantit.prodacs.facade.SessionProductionDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ProductionEditAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/*Creating an instance of ProductionEditForm.java*/
		if (form instanceof ProductionEditForm)
		{
			ProductionEditForm frm = (ProductionEditForm) form;
			
			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			ActionErrors errors = new ActionErrors();
			try
			{
				/* 	Setting the JNDI name and Environment 	*/
			   	obj.setJndiName("SessionProductionDetailsManagerBean");
		   		obj.setEnvironment();
		
				/* 	Creating the Home and Remote Objects 	*/
				SessionProductionDetailsManagerHome proHomeObj = (SessionProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionProductionDetailsManagerHome.class);
				SessionProductionDetailsManager productionObj = (SessionProductionDetailsManager)PortableRemoteObject.narrow(proHomeObj.create(),SessionProductionDetailsManager.class);
				if (frm.getFormAction().equalsIgnoreCase("prodnview"))
				{
					action = "prodnview";
					if (BuildConfig.DMODE)
					{
						System.out.println("-->"+action);
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("searchProd"))
				{
					action = "searchProd";
					if (BuildConfig.DMODE)
					{
						System.out.println("-->"+action);
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
				}
				else if (frm.getFormAction().equalsIgnoreCase("posting"))
				{
					ActionMessages messages = new ActionMessages();

					if (BuildConfig.DMODE)
						System.out.println("Prod Id :"+frm.getId());
					Vector vecProd = new Vector();
					vecProd.add(new Integer(frm.getId()));
					HashMap postProdDet = new HashMap();
					try
					{
						postProdDet = productionObj.postProductionDetails(vecProd);	
					}catch (ProductionException e)
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

					action = "searchProd";
				}
				else if (frm.getFormAction().equalsIgnoreCase("viewOpns"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("View Operation Starts...");
					}
					String opnSno = "";
					String opnName = "";
					String opnStdHrs = "";
					String opnGpId = "";
					String opnGpCde = "";
					OperationDetails[] objOpnDets = null;
					objOpnDets = productionObj.viewOperations(Integer.parseInt(frm.getHidWoJbId()));
					for (int i = 0; i < objOpnDets.length; i++)
					{
						if (i == 0)
						{
							opnSno = objOpnDets[i].getOpnSerialNo()+"";
							opnName = objOpnDets[i].getOpnName()+"";
							opnStdHrs = objOpnDets[i].getOpnStdHrs()+"";
							opnGpId = objOpnDets[i].getOpnGpId()+"";
							opnGpCde = objOpnDets[i].getOpnGpCode()+"";
						}
						else
						{
							opnSno = opnSno + "~" + objOpnDets[i].getOpnSerialNo()+"";
							opnName = opnName + "~" + objOpnDets[i].getOpnName()+"";
							opnStdHrs = opnStdHrs + "~" + objOpnDets[i].getOpnStdHrs()+"";
							opnGpId = opnGpId + "~" + objOpnDets[i].getOpnGpId()+"";
							opnGpCde = opnGpCde + "~" + objOpnDets[i].getOpnGpCode()+"";
						}
					}
					frm.setSno(opnSno);
					frm.setName(opnName);
					frm.setStdHrs(opnStdHrs);
					frm.setGrpId(opnGpId);
					frm.setGpCde(opnGpCde);
					action = "failure";
					if (BuildConfig.DMODE)
					{
						System.out.println("View Operation Ends.");
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					/* Wheather Posted or Not */
					if (BuildConfig.DMODE)
					{
						System.out.println("Posting Decision Starts.");
					}
					try
					{
						productionObj.isModify(frm.getId());
						if (BuildConfig.DMODE)
						{
							System.out.println("Posting Succeed.");
						}
					}
					catch (ProductionException e)
					{
						e.printStackTrace();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						frm.setShowCount("");
						if (BuildConfig.DMODE)
						{
							System.out.println("Posting Failure.");
						}
						action = "success";
					}
					if (BuildConfig.DMODE)
					{
						System.out.println("Modification Starts.");
					}

					/* Object to Production Detals */
					ProductionDetails objProductionDetails = new ProductionDetails();
					objProductionDetails = productionObj.getProductionDetails(frm.getId());
					
					frm.setProDate(objProductionDetails.getProdCrntDate().toString().substring(0,10));
					frm.setProShift(objProductionDetails.getShiftId()+"");
					frm.setProMachine(objProductionDetails.getMcCode());
					if (BuildConfig.DMODE)
					{
						System.out.println("objProductionDetails.getWoNo: "+objProductionDetails.getWoId());
					}
					frm.setProWorkOrderHash(objProductionDetails.getWoId()+"");
					frm.setHidWrkOrdId(objProductionDetails.getWoId()+""); /* This is for Hidden varable */
					frm.setProTotQtySnos(objProductionDetails.getProdQtySnos());
					
					/* Find Out the values of Job Details */
					Vector vec_jobDet = productionObj.getProdnJobDetailsForUpdateByWorkOrder(objProductionDetails.getWoId(),frm.getId(),0,0,0,0);
					if (BuildConfig.DMODE)
					{
						System.out.println("Vec Size: "+vec_jobDet.size());
					}
					String str_jobDet = "";
					for (int i = 0; i < vec_jobDet.size(); i++)
					{
						/* Object to ProductionJobDetails */
						ProductionJobDetails objProdJobDet = new ProductionJobDetails();
						objProdJobDet = (ProductionJobDetails) vec_jobDet.get(i);
						
						if (i != 0)
						{
							str_jobDet = str_jobDet + "^";
						}
						if (i == 0)
						{
							str_jobDet = objProdJobDet.getWoJbId()+"";
							str_jobDet = str_jobDet + "-" + objProdJobDet.getJobName();
							str_jobDet = str_jobDet + "-" + objProdJobDet.getTotQty();
							str_jobDet = str_jobDet + "-" + ((objProdJobDet.getPendingQtySnos().equalsIgnoreCase(""))?"empty":objProdJobDet.getPendingQtySnos());
							str_jobDet = str_jobDet + "-" + ((objProdJobDet.getPostedQtySnos().equalsIgnoreCase(""))?"empty":objProdJobDet.getPostedQtySnos());
							str_jobDet = str_jobDet + "-" + ((objProdJobDet.getUnPostedQtySnos().equalsIgnoreCase(""))?"empty":objProdJobDet.getUnPostedQtySnos());
							str_jobDet = str_jobDet + "-" + ((objProdJobDet.getLastProdnDate() == null)?"empty":objProdJobDet.getLastProdnDate().toString().substring(0,10));
						}
						else
						{
							str_jobDet = str_jobDet + "" +objProdJobDet.getWoJbId();
							str_jobDet = str_jobDet + "-" + objProdJobDet.getJobName();
							str_jobDet = str_jobDet + "-" + objProdJobDet.getTotQty();
							str_jobDet = str_jobDet + "-" + ((objProdJobDet.getPendingQtySnos().equalsIgnoreCase(""))?"empty":objProdJobDet.getPendingQtySnos());
							str_jobDet = str_jobDet + "-" + ((objProdJobDet.getPostedQtySnos().equalsIgnoreCase(""))?"empty":objProdJobDet.getPostedQtySnos());
							str_jobDet = str_jobDet + "-" + ((objProdJobDet.getUnPostedQtySnos().equalsIgnoreCase(""))?"empty":objProdJobDet.getUnPostedQtySnos());
							str_jobDet = str_jobDet + "-" + ((objProdJobDet.getLastProdnDate() == null)?"empty":objProdJobDet.getLastProdnDate().toString().substring(0,10));
						}
					}
					frm.setModJobDet(str_jobDet);
					if (BuildConfig.DMODE)
					{
						System.out.println("Job ArrayFinal: "+ str_jobDet);
					}
					
					/* Finding the JobOperation Details by WoJbId */
					Vector vec_operDet = productionObj.getProdnJobOperationDetailsForUpdate(objProductionDetails.getWoJbId(),frm.getId(),0);
					frm.setHidWrkOrdJobId(objProductionDetails.getWoJbId()+""); /* This is for Hidden Job Id */
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
					{
						System.out.println("Oper Array: "+ str_operDet);
					}
					
					frm.setProStartOperation(objProductionDetails.getProdStartOpn()+"");
					frm.setProEndOperation(objProductionDetails.getProdEndOpn()+"");
					frm.setProTotalHours(objProductionDetails.getProdTotHrs()+"");
					if (BuildConfig.DMODE)
					{
						System.out.println("Hid-WoID: "+frm.getHidWrkOrdId());
						System.out.println("Hid-Wo JbId: "+frm.getHidWrkOrdJobId());
						System.out.println("Modified.");
					}
				}
				if (frm.getFormAction().equalsIgnoreCase("update"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Updation Starts.");
					}
					String[] arEmpDetails;
					String[] arEmpPrsnlDet;
					
					/* Details object to ProductionDetails */
					ProductionDetails objProductionDetails = new ProductionDetails();
					
					objProductionDetails.setProdId(frm.getId());
					if (BuildConfig.DMODE)
					{
						System.out.println("Production Id: "+objProductionDetails.getProdId());
					}
					objProductionDetails.setMcCode(frm.getProMachine());
					
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
					objProductionDetails.setProdCrntDate(ge.getTime());
					objProductionDetails.setShiftId(Integer.parseInt(frm.getProShift()));
					objProductionDetails.setProdWorkType((frm.getProNmlorRwk().equals("normal"))?"N":"R");
					objProductionDetails.setWoJbId(Integer.parseInt(frm.getHidWrkOrdJobId()));
					objProductionDetails.setProdQtySnos(frm.getProTotQtySnos());
					if (BuildConfig.DMODE)
					{
						System.out.println("Qty Sno: "+ objProductionDetails.getProdQtySnos());
					}
					/* Calculate no. of Qty's */
					StringTokenizer stTotQty = new StringTokenizer(frm.getProTotQtySnos().trim(),",");
					objProductionDetails.setProdTotQty(stTotQty.countTokens());
					if (BuildConfig.DMODE)
					{
						System.out.println("Tot Qty: "+ stTotQty.countTokens());
					}
					objProductionDetails.setProdStartOpn(Integer.parseInt(frm.getProStartOperation()));
					objProductionDetails.setProdEndOpn(Integer.parseInt(frm.getProEndOperation()));
					objProductionDetails.setProdTotHrs(Float.parseFloat(frm.getProTotalHours()));
					if (BuildConfig.DMODE)
					{
						System.out.println("EmpDetails: "+ frm.getHidAllEmpDet());
					}
					
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
						while (stEmpPrsnlDet.hasMoreTokens())
						{
							arEmpPrsnlDet[recCount] = stEmpPrsnlDet.nextToken();
							if (BuildConfig.DMODE)
								System.out.println("Emp Details :"+arEmpPrsnlDet[recCount]);
							recCount++;
						}
						objEmployeeDtyOtDetails.setEmpType(arEmpPrsnlDet[0]);
						objEmployeeDtyOtDetails.setEmpTypdId(Integer.parseInt(arEmpPrsnlDet[1]));
						objEmployeeDtyOtDetails.setEmpName(arEmpPrsnlDet[2]);
						objEmployeeDtyOtDetails.setEmpId(Integer.parseInt(arEmpPrsnlDet[3]));
						objEmployeeDtyOtDetails.setDutyHrs(Float.parseFloat(arEmpPrsnlDet[4]));
						objEmployeeDtyOtDetails.setOtHrs(Float.parseFloat(arEmpPrsnlDet[5]));
						if (BuildConfig.DMODE)
						{
							System.out.println("--> "+arEmpPrsnlDet[0]+"&"+arEmpPrsnlDet[1]+"&"+arEmpPrsnlDet[3]+"&"+arEmpPrsnlDet[4]+"&"+arEmpPrsnlDet[5]);
						}
	
						vec_empDutyOtDet.add(objEmployeeDtyOtDetails);	
					}
					
					objProductionDetails.setProdnEmpHrsDetails(vec_empDutyOtDet);
					objProductionDetails.setModifiedBy(frm.getHidUserId());
					boolean update = productionObj.updateProductionDetails(objProductionDetails);
					
					if (update == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.update","Production Entry",""));
						
						saveMessages(request,message);
						action="success";
					}
					else
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("Else case: "+update);
						}
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						action="failure";				  			
					}
				}
				if (frm.getFormAction().equalsIgnoreCase("makeInvalid"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Make InValid");
					}
					
					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(new Integer(ids[i]));
						if (BuildConfig.DMODE)
						{
							System.out.println("Invalid Ids: "+ ids[i]);
						}
					}
					HashMap hm = new HashMap();
					try
					{
						hm = productionObj.makeProductionInValid(v);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
						{
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"Production Entry"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"Production Entries"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Production Entry"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Production Entries"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Production Entry"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Production Entries"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Production Entry"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Production Entries"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Production Entry"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Production Entries"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Production Entry"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Production Entries"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
				if (frm.getFormAction().equalsIgnoreCase("makeValid"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Make Valid");
					}
					
					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(new Integer(ids[i]));
						if (BuildConfig.DMODE)
						{
							System.out.println("Valid Ids: "+ ids[i]);
						}
					}
					HashMap hm = new HashMap();
					try
					{
						hm = productionObj.makeProductionValid(v);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
						{
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"Production Entry"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"Production Entries"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Production Entry"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Production Entries"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Production Entry"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Production Entries"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Production Entry"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Production Entries"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Production Entry"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Production Entries"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Production Entry"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Production Entries"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
				else if (frm.getFormAction().equalsIgnoreCase("isRadl"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Is Production Operation Starts....");
						System.out.println("StatIds: "+ frm.getHidWrkOrdJbStIds());
					}
					try
					{
						if (!frm.getHidWrkOrdJbStIds().equalsIgnoreCase(""))
						{
							StringTokenizer stWoStatIds = new StringTokenizer(frm.getHidWrkOrdJbStIds(),"-");
							int[] arWoStatIds = new int[stWoStatIds.countTokens()];
							int count = 0;
							while (stWoStatIds.hasMoreTokens())
							{
								arWoStatIds[count] = Integer.parseInt(stWoStatIds.nextToken().trim());
								if (BuildConfig.DMODE)
								{
									System.out.println("WoStatIds["+count+"]: "+ arWoStatIds[count]);
								}
								count++;
							}
							boolean isReworkOpn = productionObj.isReworkOperations(arWoStatIds,Integer.parseInt(frm.getProStartOperation()),Integer.parseInt(frm.getProEndOperation()));
							if (BuildConfig.DMODE)
							{
								System.out.println("IsReworkOperation: "+ isReworkOpn);
							}
						}

						/* This is for Production operation or not */
						boolean isRadlOpn = productionObj.isRadlOperation(Integer.parseInt(frm.getHidIsRadl()),Integer.parseInt(frm.getProStartOperation()), Integer.parseInt(frm.getProEndOperation()), 1);
						if (BuildConfig.DMODE)
						{
							System.out.println("Is Production Operation: "+ isRadlOpn);
						}
						frm.setShowCount("4");
						if (BuildConfig.DMODE)
						{
							System.out.println("Count: "+ frm.getShowCount());
						}
					}
					catch (ProductionException e)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
						if (BuildConfig.DMODE)
						{
							System.out.println("ProdException");
						}
						if(!errors.isEmpty())
							saveErrors(request,errors);
						frm.setShowCount("2");
						action = "failure";
					}
				}
			}
			catch (ProductionException e)
			{
				if (BuildConfig.DMODE)
				{
					e.printStackTrace();
					System.out.println("Error: "+e.getExceptionMessage());
				}
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				if (BuildConfig.DMODE)
				{
					System.out.println("Error Size: "+errors.size());
				}
				action="failure";
			}
			catch (Exception e)
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action="failure";
				if (BuildConfig.DMODE)
				{
					e.printStackTrace();
					System.out.println("Problem in Production Edit Action.");
				}
			}

		}
		if (BuildConfig.DMODE)
		{
			System.out.println("Action: "+action);
		}
		return mapping.findForward(action);
	}

}

/***
$Log: ProductionEditAction.java,v $
Revision 1.26  2005/12/28 10:02:07  vkrishnamoorthy
Error message caught and thrown appropriately for Employee and Duty failures.

Revision 1.25  2005/10/06 07:43:05  vkrishnamoorthy
Modified for loading shift and machines if that already selected ones does not exist as per accountings.

Revision 1.24  2005/09/26 14:35:05  vkrishnamoorthy
EmpTypId sent along with employee details.

Revision 1.23  2005/08/29 12:14:43  vkrishnamoorthy
Ids for MakeValid and MakeInValid changed to Integer type.

Revision 1.22  2005/08/25 14:00:38  vkrishnamoorthy
Modified for posting details during view.

Revision 1.21  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.20  2005/07/23 09:24:36  vkrishnamoorthy
Despatch and Shipment added to getProdnJobDetailsForUpdateByWorkOrder().

Revision 1.19  2005/07/16 07:04:14  vkrishnamoorthy
finalProdId set for method getProdnJobDetailsForUpdateByWorkOrder().

Revision 1.18  2005/07/06 06:43:58  vkrishnamoorthy
Incentive Flag removed.

Revision 1.17  2005/07/01 13:00:53  vkrishnamoorthy
Modified as per View Operations.

Revision 1.16  2005/05/30 07:44:50  vkrishnamoorthy
UserName from session got and passed to create log.

Revision 1.15  2005/03/14 09:01:53  sponnusamy
Changes made in Date

Revision 1.14  2005/03/12 06:35:20  sponnusamy
According PostProduction Actions Changed.

Revision 1.13  2005/03/11 14:56:02  sponnusamy
Production Date modified

Revision 1.12  2005/03/09 07:28:47  vkrishnamoorthy
Modified as per PostProduction entry viewing.

Revision 1.11  2005/02/11 11:14:17  sponnusamy
Warnings removed

Revision 1.10  2005/02/07 09:43:07  sponnusamy
getLastProduction Date field  is chanaged instead of getProductionDate.

Revision 1.9  2005/02/04 06:17:18  sponnusamy
For Rework operations checked.

Revision 1.8  2005/02/02 08:53:51  sponnusamy
Method changed for update Production Details

Revision 1.7  2005/01/30 10:13:06  sponnusamy
Exception Messages modified.

Revision 1.6  2005/01/28 10:02:04  sponnusamy
Log added

***/
