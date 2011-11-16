/*
 * Created on Jan 3, 2005
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
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.production.ProductionJobDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionJobQtyDetails;
import com.savantit.prodacs.businessimplementation.production.RadialProductionDetails;
import com.savantit.prodacs.facade.SessionRadlProductionDetailsManager;
import com.savantit.prodacs.facade.SessionRadlProductionDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RadialProductionEditAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		String action="failure";
		ActionErrors errors = new ActionErrors();

		if (form instanceof RadialProductionEditForm)
		{
			RadialProductionEditForm frm = (RadialProductionEditForm) form;
			boolean posted = false; 

			try
			{
				EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
				
				/* 	Setting the JNDI name and Environment 	*/
			   	obj.setJndiName("SessionRadlProductionDetailsManagerBean");
		   		obj.setEnvironment();

				/* 	Creating the Home and Remote Objects 	*/
				SessionRadlProductionDetailsManagerHome rdlProHomeObj = (SessionRadlProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionRadlProductionDetailsManagerHome.class);
				SessionRadlProductionDetailsManager rdlProductionObj = (SessionRadlProductionDetailsManager)PortableRemoteObject.narrow(rdlProHomeObj.create(),SessionRadlProductionDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
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
					objOpnDets = rdlProductionObj.viewOperations(Integer.parseInt(frm.getHidWoJbId()));
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
				else if (frm.getFormAction().equalsIgnoreCase("radlview"))
				{
					action = "radlview";
					if (BuildConfig.DMODE)
					{
						System.out.println("-->"+action);
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("searchRdl"))
				{
					action = "searchRdl";
					if (BuildConfig.DMODE)
						System.out.println("-->"+action);
				}
				else if (frm.getFormAction().equalsIgnoreCase("posting"))
				{
					ActionMessages messages = new ActionMessages();

					if (BuildConfig.DMODE)
						System.out.println("Prod Id :"+frm.getId());
					Vector vecRadl = new Vector();
					vecRadl.add(new Integer(frm.getId()));
					HashMap postProdDet = new HashMap();
					try
					{
						postProdDet = rdlProductionObj.postRadlProductionDetails(vecRadl);	
					}catch (ProductionException e)
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("Problem in Radial Posting.");
						}
					}
					
					int radlPosted = 0;
					int notPosted = 0;
					int problm = 0;
					for (Iterator it=postProdDet.entrySet().iterator(); it.hasNext(); ) 
					{ 
						Map.Entry entry = (Map.Entry)it.next(); 
						int value = ((Integer)entry.getValue()).intValue();
						if(value == 0)
						{
							radlPosted++;
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
					if(radlPosted <= 1)
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(radlPosted),"Radial Production Entry"));
					else if(radlPosted > 1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.posted", Integer.toString(radlPosted),"Radial Production Entries"));					
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
					if(postProdDet.size() < vecRadl.size())
					{
						int diff = vecRadl.size() - postProdDet.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		

					action = "searching";
				}
				else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					/* Wheather Posted or Not */
					if (BuildConfig.DMODE)
						System.out.println("Posting Decision Starts.");
					posted = rdlProductionObj.isModifyForRadl(frm.getId());
					if (BuildConfig.DMODE)
					{
						System.out.println("Posting Succeed.");
						System.out.println("Modification Starts.");
					}

					/* Object to Production Detals */
					RadialProductionDetails objRadlProductionDetails = new RadialProductionDetails();
					objRadlProductionDetails = rdlProductionObj.getRadlProductionDetails(frm.getId());
					
					frm.setProDate(objRadlProductionDetails.getRadlCrntDate().toString().substring(0,10));
					frm.setProShift(objRadlProductionDetails.getShiftId()+"");
					frm.setProMachine(objRadlProductionDetails.getMcCode());
					if (BuildConfig.DMODE)
						System.out.println("WoNo: "+objRadlProductionDetails.getWoId());
					frm.setProWorkOrderHash(objRadlProductionDetails.getWoId()+"");
					frm.setHidWrkOrdId(objRadlProductionDetails.getWoId()+""); /* This is for Hidden varable */
					frm.setProTotQtySnos(objRadlProductionDetails.getRadlQtySnos());
					if (BuildConfig.DMODE)
					{
						System.out.println("Qty Snos: "+frm.getProTotQtySnos());
						System.out.println("Incentive: "+frm.getProIncentive());
					}
					
					frm.setMaterialType(objRadlProductionDetails.getRadlMatlTypeId()+"");
					frm.setRdlDiameter(objRadlProductionDetails.getRadlDiameter()+"");
					frm.setRdlLength(objRadlProductionDetails.getRadlLength()+"");
					frm.setRdlHoles(objRadlProductionDetails.getRadlNoOfHoles()+"");
					frm.setRdlPreDiameter(objRadlProductionDetails.getRadlPreDiameter()+"");
					frm.setRdlCompleteStatus((objRadlProductionDetails.isRadlCompletionFlg() == true)?"1":"2");
					if (BuildConfig.DMODE)
						System.out.println("Complete Status: "+frm.getRdlCompleteStatus());
					
					/* Find Out the values of Job Details */
					Vector vec_jobDet = rdlProductionObj.getProdnJobDetailsForUpdateByWorkOrder(objRadlProductionDetails.getWoId(),0,frm.getId(),0,0,0);
					if (BuildConfig.DMODE)
						System.out.println("Vec Size: "+vec_jobDet.size());
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
						System.out.println("Job ArrayFinal: "+ str_jobDet);
					
					/* Finding the JobOperation Details by WoJbId */
					Vector vec_operDet = rdlProductionObj.getProdnJobOperationDetailsForUpdate(objRadlProductionDetails.getWoJbId(),0,frm.getId());
					frm.setHidWrkOrdJobId(objRadlProductionDetails.getWoJbId()+""); /* This is for Hidden Job Id */
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
					
					frm.setProStartOperation(objRadlProductionDetails.getRadlStartOpn()+"");
					frm.setProEndOperation(objRadlProductionDetails.getRadlEndOpn()+"");
					frm.setProTotalHours(objRadlProductionDetails.getRadlTotHrs()+"");
					if (BuildConfig.DMODE)
					{
						System.out.println("Hid-WoID: "+frm.getHidWrkOrdId());
						System.out.println("Hid-Wo JbId: "+frm.getHidWrkOrdJobId());
						System.out.println("Modified.");
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("update"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Updation Starts.");
					String[] arEmpDetails;
					String[] arEmpPrsnlDet;
					
					/* Object to Production Detals */
					RadialProductionDetails objRadlProductionDetails = new RadialProductionDetails();
					
					objRadlProductionDetails.setRadlId(frm.getId());
					if (BuildConfig.DMODE)
						System.out.println("RadlProduction Id: "+objRadlProductionDetails.getRadlId());
					objRadlProductionDetails.setMcCode(frm.getProMachine());
					
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
					objRadlProductionDetails.setRadlCrntDate(ge.getTime());
					objRadlProductionDetails.setShiftId(Integer.parseInt(frm.getProShift()));
					objRadlProductionDetails.setRadlWorkType((frm.getProNmlorRwk().equals("normal"))?"N":"R");
					//objRadlProductionDetails.setRadlIncntvFlg((frm.getProIncentive().equals(""))?false:true);
					objRadlProductionDetails.setWoJbId(Integer.parseInt(frm.getHidWrkOrdJobId()));
					objRadlProductionDetails.setRadlQtySnos(frm.getProTotQtySnos());
					if (BuildConfig.DMODE)
						System.out.println("Qty Sno: "+ objRadlProductionDetails.getRadlQtySnos());

					/* Calculate no. of Qty's */
					StringTokenizer stTotQty = new StringTokenizer(frm.getProTotQtySnos().trim(),",");
					objRadlProductionDetails.setRadlTotQty(stTotQty.countTokens());
					if (BuildConfig.DMODE)
						System.out.println("Tot Qty: "+ stTotQty.countTokens());
					objRadlProductionDetails.setRadlStartOpn(Integer.parseInt(frm.getProStartOperation()));
					objRadlProductionDetails.setRadlEndOpn(Integer.parseInt(frm.getProEndOperation()));
					objRadlProductionDetails.setRadlTotHrs(Float.parseFloat(frm.getProTotalHours()));
					
					objRadlProductionDetails.setRadlMatlTypeId(Integer.parseInt(frm.getMaterialType()));
					objRadlProductionDetails.setRadlDiameter(Double.parseDouble(frm.getRdlDiameter()));
					objRadlProductionDetails.setRadlLength(Double.parseDouble(frm.getRdlLength()));
					objRadlProductionDetails.setRadlNoOfHoles(Integer.parseInt(frm.getRdlHoles()));
					objRadlProductionDetails.setRadlPreDiameter(Double.parseDouble(frm.getRdlPreDiameter()));
					objRadlProductionDetails.setRadlCompletionFlg((frm.getRdlCompleteStatus()).equals("1")?true:false);

					
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
							recCount++;
						}

						objEmployeeDtyOtDetails.setEmpType(arEmpPrsnlDet[0]);
						objEmployeeDtyOtDetails.setEmpTypdId(Integer.parseInt(arEmpPrsnlDet[1]));
						objEmployeeDtyOtDetails.setEmpId(Integer.parseInt(arEmpPrsnlDet[3]));
						objEmployeeDtyOtDetails.setDutyHrs(Float.parseFloat(arEmpPrsnlDet[4]));
						objEmployeeDtyOtDetails.setOtHrs(Float.parseFloat(arEmpPrsnlDet[5]));
						if (BuildConfig.DMODE)
							System.out.println("--> "+arEmpPrsnlDet[0]+"&"+arEmpPrsnlDet[1]+"&"+arEmpPrsnlDet[3]+"&"+arEmpPrsnlDet[4]+"&"+arEmpPrsnlDet[5]);						
	
						vec_empDutyOtDet.add(objEmployeeDtyOtDetails);	
					}
					objRadlProductionDetails.setModifiedBy(frm.getHidUserId());
					objRadlProductionDetails.setRadlEmpHrsDetails(vec_empDutyOtDet);
					
					boolean update = rdlProductionObj.updateRadlProductionDetails(objRadlProductionDetails);
					
					if (update == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.update","Radial Production Entry ",""));
						
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
						hm = rdlProductionObj.makeRadlProductionInValid(v);
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
					ActionMessages messages = new ActionMessages();
					if(success<=1)
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"Radial Production Entry"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"Radial Production Entries"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Radial Production Entry"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Radial Production Entries"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Radial Production Entry"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Radial Production Entries"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Radial Production Entry"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Radial Production Entries"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Radial Production Entry"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Radial Production Entries"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Radial Production Entry"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Radial Production Entries"));
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
						hm = rdlProductionObj.makeRadlProductionValid(v);
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
					ActionMessages messages = new ActionMessages();
					if(success<=1)
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"Radial Production Entry"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"Radial Production Entries"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Radial Production Entry"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Radial Production Entries"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Radial Production Entry"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Radial Production Entries"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Radial Production Entry"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Radial Production Entries"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Radial Production Entry"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Radial Production Entries"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Radial Production Entry"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Radial Production Entries"));
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
						System.out.println("Is Radl Operation Starts....");
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
									System.out.println("WoStatIds["+count+"]: "+ arWoStatIds[count]);
								count++;
							}
								boolean isReworkOpn = rdlProductionObj.isReworkOperations(arWoStatIds,Integer.parseInt(frm.getProStartOperation()),Integer.parseInt(frm.getProEndOperation()));
								if (BuildConfig.DMODE)
									System.out.println("IsReworkOperation: "+ isReworkOpn);
						}

						/* This is for Production Operation operation or Not */
						boolean isRadlOpn = rdlProductionObj.isRadlOperation(Integer.parseInt(frm.getHidIsRadl()),Integer.parseInt(frm.getProStartOperation()), Integer.parseInt(frm.getProEndOperation()), 0);
						if (BuildConfig.DMODE)
							System.out.println("Is Production Operation: "+ isRadlOpn);
						frm.setShowCount("3");
						//System.out.println("Count: "+ frm.getShowCount());
					}
					catch (ProductionException e)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
						if (BuildConfig.DMODE)
							System.out.println("ProdException");
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
					System.out.println("Posting Decision: "+ posted);
				if (posted == false)
				{
					action = "success";
				}
				else
				{
					action = "failure";
				}
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
					System.out.println("Problem in Radial Production Edit Action.");
				}
			}
		}
		if (BuildConfig.DMODE)
			System.out.println("RdlEdit Action: "+action);
		return mapping.findForward(action);
	}
}

/***
$Log: RadialProductionEditAction.java,v $
Revision 1.24  2005/09/27 10:18:10  vkrishnamoorthy
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

Revision 1.18  2005/07/06 10:08:17  vkrishnamoorthy
Incentive Flag removed.

Revision 1.17  2005/07/01 13:00:53  vkrishnamoorthy
Modified as per View Operations.

Revision 1.16  2005/05/30 13:15:42  vkrishnamoorthy
Modified as per log entries.

Revision 1.15  2005/03/16 08:46:47  vkrishnamoorthy
Checking for null fields modified.

Revision 1.14  2005/03/14 09:01:53  sponnusamy
Changes made in Date

Revision 1.13  2005/03/12 06:35:20  sponnusamy
According PostProduction Actions Changed.

Revision 1.12  2005/03/12 05:57:46  sponnusamy
Radial Production Date Modified.

Revision 1.11  2005/03/09 07:28:47  vkrishnamoorthy
Modified as per PostProduction entry viewing.

Revision 1.10  2005/02/11 11:14:17  sponnusamy
Warnings removed

Revision 1.9  2005/02/07 09:43:07  sponnusamy
getLastProduction Date field  is chanaged instead of getProductionDate.

Revision 1.8  2005/02/04 07:47:33  sponnusamy
IsRework Operation Checked.

Revision 1.7  2005/02/02 09:46:37  sponnusamy
Method changed for update Radial production Details

Revision 1.6  2005/01/31 09:16:28  sponnusamy
MakeValid and MakeInValid are modified.

Revision 1.5  2005/01/31 07:07:24  sponnusamy
Extra definitions removed

***/
