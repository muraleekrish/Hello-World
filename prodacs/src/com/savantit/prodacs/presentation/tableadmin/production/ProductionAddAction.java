/*
 * Created on Nov 5, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.production;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.struts.action.Action;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.savantit.prodacs.businessimplementation.job.OperationDetails;
import com.savantit.prodacs.businessimplementation.production.EmployeeDtyOtDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingDateDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.production.ProductionJobDetails;
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
public class ProductionAddAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		String arEmpDetails[] = null;
		String arEmpPrsnlDet[] = null;

		/*Creating an instance of ProductionAddForm.java*/
		if (form instanceof ProductionAddForm)
		{
			ProductionAddForm frm = (ProductionAddForm) form;
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

				if (frm.getFormAction().equalsIgnoreCase("add"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Production Add Starts.");
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
					ProductionDetails[] objProductionDetailsList = null;
					Vector vec_ProdDetails = new Vector();
					for (int i = 0; i < arTotProdList.length; i++)
					{
						ProductionDetails objProductionDetails = new ProductionDetails();
						ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();

						/* Production Entries are Listed */
						StringTokenizer stProdList = new StringTokenizer(arTotProdList[i],"#");
						String[] arProdList = new String[stProdList.countTokens()];
						count = 0;
						while (stProdList.hasMoreTokens())
						{
							arProdList[count] = stProdList.nextToken();
							count++;
						}

						/* Production Current Date */
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
						objProductionDetails.setProdCrntDate(ge.getTime());
						objProductionDetails.setShiftName(arProdList[2].trim());
						objProductionDetails.setMcCode(arProdList[3].trim());
						objProductionDetails.setWoNo(arProdList[4]);

						objProductionJobDetails.setJobName(arProdList[5]);
						objProductionDetails.setObjProductionJobDetails(objProductionJobDetails);

						objProductionDetails.setProdWorkType((arProdList[6].trim().equalsIgnoreCase("Normal"))?"N":"R");
						objProductionDetails.setWoJbId(Integer.parseInt(arProdList[12].trim()));
						objProductionDetails.setProdQtySnos(arProdList[13].trim());

						/* Calculate no. of Qty's */
						StringTokenizer stTotQty = new StringTokenizer(arProdList[13].trim(),",");
						objProductionDetails.setProdTotQty(stTotQty.countTokens());
						if (BuildConfig.DMODE)
						System.out.println("Tot Qty :"+objProductionDetails.getProdTotQty()+"--"+stTotQty.countTokens());
						objProductionDetails.setProdStartOpn(Integer.parseInt(arProdList[7].trim()));
						objProductionDetails.setProdEndOpn(Integer.parseInt(arProdList[8].trim()));
						objProductionDetails.setProdTotHrs(Float.parseFloat(arProdList[9].trim()));
						objProductionDetails.setShiftId(Integer.parseInt(arProdList[10].trim()));
						objProductionDetails.setWoId(Integer.parseInt(arProdList[11].trim()));

						/* Splitting all Details of Employees */
						StringTokenizer stEmpDet = new StringTokenizer(arProdList[14],"^");
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
							/* Details object to EmployeeDtyOtDetails */
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
						objProductionDetails.setProdnEmpHrsDetails(vec_empDutyOtDet);
						objProductionDetails.setCreatedBy(frm.getHidUserId());
						vec_ProdDetails.add(objProductionDetails);
						if (BuildConfig.DMODE)
						{
							System.out.println("Date: "+ objProductionDetails.getProdCrntDate());
							System.out.println("McCde: "+ objProductionDetails.getMcCode());
							System.out.println("Shift Id: "+ objProductionDetails.getShiftId());
							System.out.println("WrkTyp: "+ objProductionDetails.getProdWorkType());
							System.out.println("QtySno: "+ objProductionDetails.getProdQtySnos());
							System.out.println("Tot Qty: "+ objProductionDetails.getProdTotQty());
							System.out.println("St.Opn: "+ objProductionDetails.getProdStartOpn());
							System.out.println("Ed.Opn: "+ objProductionDetails.getProdEndOpn());
							System.out.println("TotHrs: "+ objProductionDetails.getProdTotHrs());
	
							System.out.println("ShiftName: "+ objProductionDetails.getShiftName());
							System.out.println("WOId: "+ objProductionDetails.getWoId());
							System.out.println("WoNo: "+ objProductionDetails.getWoNo());
							System.out.println("JobName: "+ objProductionDetails.getObjProductionJobDetails().getJobName());
	
							System.out.println("Emp.Size: "+ vec_empDutyOtDet.size());
							System.out.println();
						}
					}
					objProductionDetailsList = new ProductionDetails[vec_ProdDetails.size()];
					vec_ProdDetails.copyInto(objProductionDetailsList);

					HashMap hm_ProdResult = productionObj.addNewProductionDetails(objProductionDetailsList);
					if (BuildConfig.DMODE)
					{
						System.out.println("hm_ProdResult: "+hm_ProdResult);
					}
					request.setAttribute("prodresult",hm_ProdResult);
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

					try
					{
						ProductionAccountingDateDetails prodAccntngDet = (ProductionAccountingDateDetails)productionObj.isPosted(frm.getProMachine(),ge.getTime(),Integer.parseInt(frm.getProShift()));
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
							action = "viewAccountings";
							request.setAttribute("formAction",action);
							frm.setFormAction("viewAccountings");
						}
						if (BuildConfig.DMODE)
						{
							System.out.println("Posting Decision: "+postRsnSize);
						}
					}
					catch (ProductionException e)
					{
						e.printStackTrace();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
						if (BuildConfig.DMODE)
						{
							System.out.println("ProdException");
						}
						if(!errors.isEmpty())
							saveErrors(request,errors);
						frm.setShowCount("");
						action = "failure";
					}
					catch (Exception e)
					{
						e.printStackTrace();
						action = "failure";
					}
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

						/* This is for Production Operation operation or Not */
						boolean isRadlOpn = productionObj.isRadlOperation(Integer.parseInt(frm.getHidIsRadl()),Integer.parseInt(frm.getProStartOperation()), Integer.parseInt(frm.getProEndOperation()), 1);
						if (BuildConfig.DMODE)
						{
							System.out.println("Is Production Operation: "+ isRadlOpn);
						}
						frm.setShowCount("3");
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
			}
			catch (ProductionException e)
			{
				if (BuildConfig.DMODE)
				{
					e.printStackTrace();
					System.out.println("Production Exception: "+ e.getExceptionMessage());
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
				if (BuildConfig.DMODE)
				{
					System.out.println("Problem in Production add Action.");
				}
			}
		}
		if (BuildConfig.DMODE)
		{
			System.out.println("Production Add Action: "+action);
		}
		return mapping.findForward(action);
	}
}
/***
$Log: ProductionAddAction.java,v $
Revision 1.18  2005/08/23 06:32:43  vkrishnamoorthy
Array index changed for ProdTotQty.

Revision 1.17  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.16  2005/07/13 12:09:28  vkrishnamoorthy
ProductionException caught appropriately.

Revision 1.15  2005/07/06 06:33:30  vkrishnamoorthy
Incentive Flag removed.

Revision 1.14  2005/07/01 13:00:53  vkrishnamoorthy
Modified as per View Operations.

Revision 1.13  2005/05/31 13:48:16  vkrishnamoorthy
Modified as per Production Accounting Details.

Revision 1.12  2005/05/30 07:44:50  vkrishnamoorthy
UserName from session got and passed to create log.

Revision 1.11  2005/05/20 09:35:14  vkrishnamoorthy
Modified as per Production Accountings.

Revision 1.10  2005/03/08 09:24:18  sponnusamy
Production add changed according to
requirement.

Revision 1.9  2005/02/11 11:14:17  sponnusamy
Warnings removed

Revision 1.8  2005/02/04 06:17:18  sponnusamy
For Rework operations checked.

Revision 1.7  2005/01/27 15:11:19  sponnusamy
isRadlOperation condition included

Revision 1.6  2005/01/19 05:04:31  sponnusamy
Exception Message throwed

Revision 1.5  2005/01/08 11:49:49  sponnusamy
Posting Rules are completed

Revision 1.4  2005/01/08 07:43:50  sponnusamy
Error Logs added

Revision 1.3  2004/12/31 11:00:26  sponnusamy
Production View page is completed

Revision 1.2  2004/12/29 15:38:19  sponnusamy
Production add and list are partially completed.

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/