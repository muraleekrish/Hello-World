/*
 * Created on Dec 30, 2004
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

import com.savantit.prodacs.businessimplementation.production.EmployeeDtyOtDetails;
import com.savantit.prodacs.businessimplementation.production.PopDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingDateDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.facade.SessionPopDetailsManager;
import com.savantit.prodacs.facade.SessionPopDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PayOutProductionAddAction extends Action
{
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		String action="failure";

		if(form instanceof PayOutProductionAddForm)
		{
			/* Creating a instance to ActionForm */
			PayOutProductionAddForm frm = (PayOutProductionAddForm) form;

			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			ActionErrors errors = new ActionErrors();
			String[] arEmpDetails;
			String[] arEmpPrsnlDet;
			String[] arPopDet;
			String[] arPopPrnlDet;
			
			try
			{
				if (BuildConfig.DMODE)
					System.out.println("POPAddAction Starts.");
				
				/* 	Setting the JNDI name and Environment 	*/
			   	obj.setJndiName("SessionPopDetailsManagerBean");
		   		obj.setEnvironment();

				/* 	Creating the Home and Remote Objects 	*/
				SessionPopDetailsManagerHome popHomeObj = (SessionPopDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionPopDetailsManagerHome.class);
				SessionPopDetailsManager popObj = (SessionPopDetailsManager)PortableRemoteObject.narrow(popHomeObj.create(),SessionPopDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("add"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("POP Add Starts...");
						System.out.println("Details: "+ frm.getHidPopDet());
					}

					Vector vec_PopDet = new Vector();
					PopDetails[] objPopDetailsList = null;
					StringTokenizer stNonProdDet = new StringTokenizer(frm.getHidPopDet(),"$");
					arPopDet = new String[stNonProdDet.countTokens()];
					int count = 0;
					while (stNonProdDet.hasMoreTokens())
					{
						arPopDet[count] = stNonProdDet.nextToken();
						if (BuildConfig.DMODE)
							System.out.println((count+1)+". "+ arPopDet[count]);
						count++;
					}
					
					for (int i = 0; i < arPopDet.length; i++)
					{
						StringTokenizer stNonProdPrnlDet = new StringTokenizer(arPopDet[i],"#");
						arPopPrnlDet = new String[stNonProdPrnlDet.countTokens()];
						count = 0;
						while (stNonProdPrnlDet.hasMoreTokens())
						{
							arPopPrnlDet[count] = stNonProdPrnlDet.nextToken();
							if (BuildConfig.DMODE)
								System.out.println(count+". "+ arPopPrnlDet[count]);
							count++;
						}
						
						/* Object to POPDetails */
						PopDetails objPopDetails = new PopDetails();
						
						/* Date Conversion for POP Current Date */ 
						StringTokenizer st = new StringTokenizer(arPopPrnlDet[1],"-");
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
						objPopDetails.setPopCrntDate(ge.getTime());
						objPopDetails.setShiftId(Integer.parseInt(arPopPrnlDet[4].trim()));
						objPopDetails.setShiftName(arPopPrnlDet[3].trim());
						objPopDetails.setPopRsn(arPopPrnlDet[3].trim());
						
						/* Employee Details */
						StringTokenizer stEmpDetails = new StringTokenizer(arPopPrnlDet[5],"^");
						arEmpDetails = new String[stEmpDetails.countTokens()];
						count = 0;
						while (stEmpDetails.hasMoreTokens())
						{
							arEmpDetails[count] = stEmpDetails.nextToken();
							if (BuildConfig.DMODE)
								System.out.println(count+". "+arEmpDetails[count]);
							count++;
						}

						Vector vec_empDutyOtDet = new Vector();
						for (int j = 0; j < arEmpDetails.length; j++)
						{
							StringTokenizer stEmpPrsnlDet = new StringTokenizer(arEmpDetails[j],"-");
							arEmpPrsnlDet = new String[stEmpPrsnlDet.countTokens()];
							count = 0;
							while (stEmpPrsnlDet.hasMoreTokens())
							{
								arEmpPrsnlDet[count] = stEmpPrsnlDet.nextToken();
								if (BuildConfig.DMODE)
									System.out.println(count+". "+arEmpPrsnlDet[count]);
								count++;
							}
							/*Object to Employee Duty Ot Details*/
							EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
							
							objEmployeeDtyOtDetails.setEmpType(arEmpPrsnlDet[0]);
							objEmployeeDtyOtDetails.setEmpName(arEmpPrsnlDet[1]);
							objEmployeeDtyOtDetails.setDutyHrs(Float.parseFloat(arEmpPrsnlDet[2].trim()));
							objEmployeeDtyOtDetails.setOtHrs(Float.parseFloat(arEmpPrsnlDet[3].trim()));
							objEmployeeDtyOtDetails.setEmpTypdId(Integer.parseInt(arEmpPrsnlDet[4].trim()));
							objEmployeeDtyOtDetails.setEmpId(Integer.parseInt(arEmpPrsnlDet[5].trim()));

							vec_empDutyOtDet.add(objEmployeeDtyOtDetails);	
						}
						objPopDetails.setCreatedBy(frm.getHidUserId());
						objPopDetails.setPopEmpHrsDetails(vec_empDutyOtDet);
						
						vec_PopDet.add(objPopDetails);
					}
					objPopDetailsList = new PopDetails[vec_PopDet.size()];
					vec_PopDet.copyInto(objPopDetailsList);
					
					HashMap popResult = popObj.addNewPopDetails(objPopDetailsList);
					request.setAttribute("popResult",popResult);
					
					if (BuildConfig.DMODE)
						System.out.println("Pop Completed..");
				}
				else if (frm.getFormAction().equalsIgnoreCase("post"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Posting Starts...");
					StringTokenizer st = new StringTokenizer(frm.getPopFromDate(),"-");
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
						ProductionAccountingDateDetails prodAccntngDet = (ProductionAccountingDateDetails)popObj.isPosted(null,ge.getTime(),Integer.parseInt(frm.getMachineShift()));
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
							action = "popAccountings";
							request.setAttribute("formAction",action);
							frm.setFormAction("popAccountings");
						}
						if (BuildConfig.DMODE)
							System.out.println("Posting Decision: "+postRsnSize);
					}
					catch (ProductionException e)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
						if (BuildConfig.DMODE)
							System.out.println("ProdException");
						if(!errors.isEmpty())
							saveErrors(request,errors);
						frm.setShowCount("");
						action = "failure";
					}
					catch (Exception e)
					{
						if (BuildConfig.DMODE)
							e.printStackTrace();
						action = "failure";
					}
				}
			}
			catch(ProductionException pe)
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",pe.getExceptionMessage()));
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
					System.out.println("Problem in POP Add Action.");
				}
			}
		}
		if (BuildConfig.DMODE)
			System.out.println("POP Add Action :"+action);
		return mapping.findForward(action);
	}
}

/***
$Log: PayOutProductionAddAction.java,v $
Revision 1.13  2005/12/28 11:14:45  vkrishnamoorthy
Error message caught and thrown appropriately for Employee and Duty failures.

Revision 1.12  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.11  2005/06/16 06:19:15  vkrishnamoorthy
Machine Code removed.

Revision 1.10  2005/05/31 14:07:52  vkrishnamoorthy
Modified as per Production Accounting Details.

Revision 1.9  2005/05/30 13:23:26  vkrishnamoorthy
Modified as per log entries.

Revision 1.8  2005/05/20 09:35:14  vkrishnamoorthy
Modified as per Production Accountings.

Revision 1.7  2005/03/11 13:11:45  sponnusamy
Pop Production Completed.

Revision 1.6  2005/03/10 12:09:57  sponnusamy
For Build The methods removed.

Revision 1.5  2005/02/03 05:09:15  vkrishnamoorthy
Log added.

***/