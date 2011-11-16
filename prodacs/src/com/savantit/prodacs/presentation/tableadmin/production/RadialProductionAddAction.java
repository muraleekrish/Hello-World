/*
 * Created on Dec 31, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.production;

import java.util.GregorianCalendar;
import java.util.HashMap;
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

import com.savantit.prodacs.businessimplementation.job.OperationDetails;
import com.savantit.prodacs.businessimplementation.production.EmployeeDtyOtDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingDateDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.production.ProductionJobDetails;
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
public class RadialProductionAddAction extends Action 
{
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		String action="failure";
		if (form instanceof RadialProductionAddForm)
		{
			/* Creating a instance of the Radial Production Add Form */
			RadialProductionAddForm frm=(RadialProductionAddForm) form;
			
			EJBLocator obj = new EJBLocator();
			ActionErrors errors = new ActionErrors();
			String[] arEmpDetails;
			String[] arEmpPrsnlDet;
			
			try
			{
				/* Setting the JNDI name and Environment */
				obj.setJndiName("SessionRadlProductionDetailsManagerBean");
				obj.setEnvironment();
				
				/* Creation of Home and Remote Object */
				SessionRadlProductionDetailsManagerHome objRdlHome = (SessionRadlProductionDetailsManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionRadlProductionDetailsManagerHome.class);
				SessionRadlProductionDetailsManager objRdlPro = (SessionRadlProductionDetailsManager) PortableRemoteObject.narrow(objRdlHome.create(),SessionRadlProductionDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("add"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Radial Production Add Starts.");
						System.out.println("Details: "+ frm.getHidProdList());
					}
					
					/* All the Production Entries are Tokenized by $ */
					StringTokenizer stTotProdList = new StringTokenizer(frm.getHidProdList(),"$");
					String[] arTotProdList = new String[stTotProdList.countTokens()];
					int count = 0;
					while (stTotProdList.hasMoreTokens())
					{
						arTotProdList[count] = stTotProdList.nextToken();
						if (BuildConfig.DMODE)
						{
							System.out.println(count+". "+arTotProdList[count]);
						}
						count++;
					}
					if (BuildConfig.DMODE)
					{
						System.out.println("Length: "+arTotProdList.length);
					}

					/* Details object to ProductionDetails */
					RadialProductionDetails[] objRdlProductionDetailsList = null;
					Vector vec_RdlDetails = new Vector();
					for (int i = 0; i < arTotProdList.length; i++)
					{
						/* Production Entries are Listed */
						StringTokenizer stProdList = new StringTokenizer(arTotProdList[i],"#");
						String[] arProdList = new String[stProdList.countTokens()];
						count = 0;
						while (stProdList.hasMoreTokens())
						{
							arProdList[count] = stProdList.nextToken();
							if (BuildConfig.DMODE)
							{
								System.out.println(count+". "+ arProdList[count]);
							}
							count++;
						}
						/* Details object to RadialProductionDetails */ 
						RadialProductionDetails objRdlProDetails = new RadialProductionDetails();
						ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
						
						/* Radial Production Current Date */
						StringTokenizer st = new StringTokenizer(arProdList[1].trim(),"-");
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
						objRdlProDetails.setRadlCrntDate(ge.getTime());
						objRdlProDetails.setShiftName(arProdList[2].trim());
						objRdlProDetails.setMcCode(arProdList[3].trim());
						objRdlProDetails.setWoNo(arProdList[4]);
						
						objProductionJobDetails.setJobName(arProdList[5]);
						objRdlProDetails.setObjProductionJobDetails(objProductionJobDetails);
						
						objRdlProDetails.setRadlWorkType((arProdList[6].trim().equalsIgnoreCase("Normal"))?"N":"R");
						objRdlProDetails.setRadlStartOpn(Integer.parseInt(arProdList[7].trim()));
						objRdlProDetails.setRadlEndOpn(Integer.parseInt(arProdList[8].trim()));
						objRdlProDetails.setRadlTotHrs(Float.parseFloat(arProdList[9].trim()));
						objRdlProDetails.setRadlMatlTypeName(arProdList[10].trim());
						objRdlProDetails.setRadlDiameter(Double.parseDouble(arProdList[11].trim()));
						objRdlProDetails.setRadlLength(Double.parseDouble(arProdList[12].trim()));
						objRdlProDetails.setRadlNoOfHoles(Integer.parseInt(arProdList[13].trim()));
						objRdlProDetails.setRadlPreDiameter(Double.parseDouble(arProdList[14].trim()));
						objRdlProDetails.setRadlCompletionFlg((arProdList[15].trim().equalsIgnoreCase("Yes")?true:false));
						
						objRdlProDetails.setShiftId(Integer.parseInt(arProdList[16].trim()));
						objRdlProDetails.setWoId(Integer.parseInt(arProdList[17].trim()));
						objRdlProDetails.setWoJbId(Integer.parseInt(arProdList[18].trim()));
						objRdlProDetails.setRadlQtySnos(arProdList[19].trim());

						/* Calculate no. of Qty's */
						StringTokenizer stTotQty = new StringTokenizer(arProdList[19].trim(),",");
						objRdlProDetails.setRadlTotQty(stTotQty.countTokens());
						objRdlProDetails.setRadlMatlTypeId(Integer.parseInt(arProdList[20].trim()));

						/* Splitting the Details of Employees */ 
						StringTokenizer stEmpDet = new StringTokenizer(arProdList[21].trim(),"^");
						arEmpDetails = new String[stEmpDet.countTokens()];
						int recCount = 0;
						while (stEmpDet.hasMoreTokens())
						{
							arEmpDetails[recCount] = stEmpDet.nextToken();
							recCount++;
						}

						Vector vec_empDutyOtDet = new Vector();
						for (int j = 0; j < arEmpDetails.length; j++)
						{
							/* Details Object to EmployeeDtyOtDetails */
							EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
							
							StringTokenizer stEmpPrsnlDet = new StringTokenizer(arEmpDetails[j],"-");
							arEmpPrsnlDet = new String[stEmpPrsnlDet.countTokens()];
							recCount = 0;
							while (stEmpPrsnlDet.hasMoreTokens())
							{
								arEmpPrsnlDet[recCount] = stEmpPrsnlDet.nextToken();
								recCount++;
							}
							objEmployeeDtyOtDetails.setEmpType(arEmpPrsnlDet[0]); // Emp Typ Name
							objEmployeeDtyOtDetails.setEmpName(arEmpPrsnlDet[1]); // Emp Name
							objEmployeeDtyOtDetails.setDutyHrs(Float.parseFloat(arEmpPrsnlDet[2])); // Duty Hrs
							objEmployeeDtyOtDetails.setOtHrs(Float.parseFloat(arEmpPrsnlDet[3])); // OT Hrs
							objEmployeeDtyOtDetails.setEmpTypdId(Integer.parseInt(arEmpPrsnlDet[4].trim())); // OT Hrs
							objEmployeeDtyOtDetails.setEmpId(Integer.parseInt(arEmpPrsnlDet[5].trim())); // Emp Id
		
							vec_empDutyOtDet.add(objEmployeeDtyOtDetails);
						}
						objRdlProDetails.setCreatedBy(frm.getHidUserId());
						objRdlProDetails.setRadlEmpHrsDetails(vec_empDutyOtDet);

						vec_RdlDetails.add(objRdlProDetails);
					}
					if (BuildConfig.DMODE)
					{
						System.out.println("Vec_Size: "+ vec_RdlDetails.size());
					}
					
					objRdlProductionDetailsList = new RadialProductionDetails[vec_RdlDetails.size()];
					vec_RdlDetails.copyInto(objRdlProductionDetailsList);
					
					HashMap rdlProdResult = objRdlPro.addNewRadialProductionDetails(objRdlProductionDetailsList);
					request.setAttribute("rdlProdResult",rdlProdResult);
					if (BuildConfig.DMODE)
					{
						System.out.println("Completed...");
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("post"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Posting Decision Starts..");
					}

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
					
					/* Adopting the Posting Rule */
					try
					{
						ProductionAccountingDateDetails prodAccntngDet = (ProductionAccountingDateDetails)objRdlPro.isPosted(frm.getProMachine(),ge.getTime(),Integer.parseInt(frm.getProShift()));
						Vector prodAccntngDets = prodAccntngDet.getVecProductionAccountingMachineDetails();
						String date = prodAccntngDet.getProdDate();
						int postRsnSize = prodAccntngDets.size();
						if (postRsnSize == 0)
						{
							frm.setShowCount("1");
						}
						else
						{
							request.setAttribute("date",date);
							request.setAttribute("prodAccntngDets",prodAccntngDets);
							action = "rdlAccountings";
							request.setAttribute("formAction",action);
							frm.setFormAction("rdlAccountings");
						}
						if (BuildConfig.DMODE)
						{
							System.out.println("Posting Decision: "+postRsnSize);
						}
					}
					catch (ProductionException e)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						frm.setShowCount("");
						action = "failure";
					}
					catch(Exception e)
					{
						e.printStackTrace();
						action = "failure";
					}
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
								{
									System.out.println("WoStatIds["+count+"]: "+ arWoStatIds[count]);
								}
								count++;
							}
								boolean isReworkOpn = objRdlPro.isReworkOperations(arWoStatIds,Integer.parseInt(frm.getProStartOperation()),Integer.parseInt(frm.getProEndOperation()));
								if (BuildConfig.DMODE)
								{
									System.out.println("IsReworkOperation: "+ isReworkOpn);
								}
						}

						/* This is for Production Operation operation or Not */
						boolean isRadlOpn = objRdlPro.isRadlOperation(Integer.parseInt(frm.getHidIsRadl()),Integer.parseInt(frm.getProStartOperation()), Integer.parseInt(frm.getProEndOperation()), 0);
						if (BuildConfig.DMODE)
						{
							System.out.println("Is Production Operation: "+ isRadlOpn);
						}
						frm.setShowCount("3");
						if (BuildConfig.DMODE)
						{
							System.out.println("Count: "+ frm.getShowCount());
						}
						action = "posting";
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
						action = "posting";
					}
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
					objOpnDets = objRdlPro.viewOperations(Integer.parseInt(frm.getHidWoJbId()));
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
			}
			catch (ProductionException e)
			{
				if (BuildConfig.DMODE)
				{
					System.out.println("Radial Exception: "+ e.getExceptionMessage());
				}
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
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
					System.out.println("Problem in Radial Production add Action.");
				}
			}
		}
		if (BuildConfig.DMODE)
		{
			System.out.println("Radl Add Action: "+ action);
		}
		return mapping.findForward(action);
	}
}

/***
 $Log: RadialProductionAddAction.java,v $
 Revision 1.20  2005/07/26 11:11:56  vkrishnamoorthy
 DMODE added to avid System.out.println()'s.

 Revision 1.19  2005/07/14 09:00:22  vkrishnamoorthy
 Modified to capture exception for incentive checkings.

 Revision 1.18  2005/07/06 09:52:30  vkrishnamoorthy
 Incentive Flag removed.

 Revision 1.17  2005/07/01 13:00:53  vkrishnamoorthy
 Modified as per View Operations.

 Revision 1.16  2005/05/31 14:07:52  vkrishnamoorthy
 Modified as per Production Accounting Details.

 Revision 1.15  2005/05/30 13:15:42  vkrishnamoorthy
 Modified as per log entries.

 Revision 1.14  2005/05/20 09:35:14  vkrishnamoorthy
 Modified as per Production Accountings.

 Revision 1.13  2005/03/11 04:54:32  sponnusamy
 Radial Production New Buffer added

 Revision 1.12  2005/03/10 12:09:57  sponnusamy
 For Build The methods removed.

 Revision 1.11  2005/02/28 11:25:28  sponnusamy
 Exception Message added.

 Revision 1.10  2005/02/15 12:08:35  vkrishnamoorthy
 Page Redirection corrected.

 Revision 1.9  2005/02/11 11:14:17  sponnusamy
 Warnings removed

 Revision 1.8  2005/02/04 07:47:33  sponnusamy
 IsRework Operation Checked.

 Revision 1.7  2005/01/29 10:24:18  sponnusamy
 Edit page Script changed.

 Revision 1.6  2005/01/12 06:04:26  sponnusamy
 Log added.

 ***/
